
package acme.features.sponsor.dashboard;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	@Autowired
	private SponsorDashboardRepository repository;


	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		int sponsorId = super.getRequest().getPrincipal().getActiveRoleId();

		SponsorDashboard sponsorDashboard;

		sponsorDashboard = new SponsorDashboard();

		// Facturas publicadas
		Collection<Invoice> myPublishedInvoices = this.repository.findManyInvoicesBySponsorId(sponsorId).stream().filter(x -> !x.isDraftMode()).toList();
		Collection<Sponsorship> myPublishedSponsorships = this.repository.findManySponsorshipsBySponsorId(sponsorId).stream().filter(x -> !x.isDraftMode()).toList();

		// Amounts y quantities publicadas
		Collection<Money> myQuantities = this.repository.findManyPublishedQuantitiesBySponsorId(sponsorId);
		Collection<Money> myAmounts = this.repository.findManyPublishedAmountsBySponsorId(sponsorId); //this only considers published contracts.

		// Monedas de las amounts y quatities
		//tendre mapas con clave tipo de currecy y valor cantidades, luego me quedara  por ej: (USD:[600,1000,10,...])
		// amounts para sponsorships
		Map<String, List<Money>> amountsByCurrency = myAmounts.stream().collect(Collectors.groupingBy(Money::getCurrency));
		// y quantities para las invoices
		Map<String, List<Money>> quantitiesByCurrency = myQuantities.stream().collect(Collectors.groupingBy(Money::getCurrency));

		// Cálculos con amounts
		Map<String, Double> mediaDeAmountsPorCurrency = amountsByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularMedia(entry.getValue()).getAmount()));
		Map<String, Double> maximoDeAmountsPorCurrency = amountsByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularMaximo(entry.getValue()).getAmount()));
		Map<String, Double> minimoDeAmountsPorCurrency = amountsByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularMinimo(entry.getValue()).getAmount()));
		Map<String, Double> desviacionDeAmountsPorCurrency = amountsByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularDesviacion(entry.getValue()).getAmount()));

		// Cálculos con quantities
		Map<String, Double> mediaDeQuantitiesPorCurrency = quantitiesByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularMedia(entry.getValue()).getAmount()));
		Map<String, Double> maximoDeQuantitiesPorCurrency = quantitiesByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularMaximo(entry.getValue()).getAmount()));
		Map<String, Double> minimoDeQuantitiesPorCurrency = quantitiesByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularMinimo(entry.getValue()).getAmount()));
		Map<String, Double> desviacionDeQuantitiesPorCurrency = quantitiesByCurrency.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.calcularDesviacion(entry.getValue()).getAmount()));

		List<SystemConfiguration> systemConfiguration = this.repository.findSystemConfiguration();
		String[] supportedCurrencies = systemConfiguration.get(0).acceptedCurrencies.split(",");

		long totalNumInvoicesWithTaxLessOrEqualTo21 = myPublishedInvoices.stream().filter(x -> x.getTax() <= 21.00).count();
		long totalNumSponsorshipsWithLink = myPublishedInvoices.stream().filter(x -> !x.getLink().equals(null)).count();

		sponsorDashboard.setTotalNumberOfInvoicesWithLowTax(totalNumInvoicesWithTaxLessOrEqualTo21);
		sponsorDashboard.setTotalNumberOfSponsorshipsWithLink(totalNumSponsorshipsWithLink);

		sponsorDashboard.setMaxSponsorshipsAmount(maximoDeAmountsPorCurrency);
		sponsorDashboard.setMinSponsorshipsAmount(minimoDeAmountsPorCurrency);
		sponsorDashboard.setAvgSponsorshipsAmount(mediaDeAmountsPorCurrency);
		sponsorDashboard.setDevSponsorshipsAmount(desviacionDeAmountsPorCurrency);

		sponsorDashboard.setMaxInvoicesQuantity(maximoDeQuantitiesPorCurrency);
		sponsorDashboard.setMinInvoicesQuantity(minimoDeQuantitiesPorCurrency);
		sponsorDashboard.setAvgInvoicesQuantity(mediaDeQuantitiesPorCurrency);
		sponsorDashboard.setDevInvoicesQuantity(desviacionDeQuantitiesPorCurrency);

		sponsorDashboard.setSupportedCurrencies(supportedCurrencies);
		super.getBuffer().addData(sponsorDashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {

		Dataset dataset;

		dataset = super.unbind(object, "totalNumberOfInvoicesWithLowTax", "totalNumberOfSponsorshipsWithLink",//
			"avgSponsorshipsAmount", "devSponsorshipsAmount", "minSponsorshipsAmount", "maxSponsorshipsAmount",//
			"avgInvoicesQuantity", "devInvoicesQuantity", "minInvoicesQuantity", "maxInvoicesQuantity",//
			"supportedCurrencies");

		super.getResponse().addData(dataset);

	}

	private Money calcularMedia(final Collection<Money> money) {
		Money moneyFinal = new Money();
		moneyFinal.setCurrency("USD");
		moneyFinal.setAmount(money.stream().map(x -> x.getAmount()).mapToDouble(Double::doubleValue).average().orElse(Double.NaN));

		return moneyFinal;
	}

	private Money calcularMaximo(final Collection<Money> money) {
		Money moneyFinal = new Money();
		moneyFinal.setCurrency("USD");
		moneyFinal.setAmount(money.stream().map(x -> x.getAmount()).mapToDouble(Double::doubleValue).max().orElse(Double.NaN));
		return moneyFinal;
	}

	private Money calcularMinimo(final Collection<Money> money) {
		Money moneyFinal = new Money();
		moneyFinal.setCurrency("USD");
		moneyFinal.setAmount(money.stream().map(x -> x.getAmount()).mapToDouble(Double::doubleValue).min().orElse(Double.NaN));
		return moneyFinal;

	}

	private Money calcularDesviacion(final Collection<Money> money) {
		Money desviacion = new Money();
		desviacion.setCurrency("USD");

		// Calcular la media
		double media = money.stream().mapToDouble(Money::getAmount).average().orElse(Double.NaN);

		// Calcular la suma de las diferencias al cuadrado
		double sumaDiferenciasCuadradas = money.stream().mapToDouble(budget -> Math.pow(budget.getAmount() - media, 2)).sum();

		// Calcular la varianza
		double varianza = sumaDiferenciasCuadradas / money.size();

		// Calcular la desviación estándar como la raíz cuadrada de la varianza
		double desviacionEstandar = Math.sqrt(varianza);

		desviacion.setAmount(desviacionEstandar);

		return desviacion;
	}

}
