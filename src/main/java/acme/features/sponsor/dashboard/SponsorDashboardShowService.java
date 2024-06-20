
package acme.features.sponsor.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int sponsorId;

		Collection<Sponsorship> sponsorships;
		Collection<Invoice> invoices;

		SponsorDashboard dashboard;

		int totalNumberOfInvoicesWithLowTax;
		int totalNumberOfSponsorshipsWithLink;

		Map<String, Double> avgSponsorshipsAmount;
		Map<String, Double> devSponsorshipsAmount;
		Map<String, Double> minSponsorshipsAmount;
		Map<String, Double> maxSponsorshipsAmount;

		Map<String, Double> avgInvoicesQuantity;
		Map<String, Double> devInvoicesQuantity;
		Map<String, Double> minInvoicesQuantity;
		Map<String, Double> maxInvoicesQuantity;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
		sponsorships = this.repository.findAllPublishedSponsorshipsBySponsorId(sponsorId);
		invoices = this.repository.findAllPublishedInvoicesBySponsorId(sponsorId);

		avgSponsorshipsAmount = new HashMap<>();
		devSponsorshipsAmount = new HashMap<>();
		minSponsorshipsAmount = new HashMap<>();
		maxSponsorshipsAmount = new HashMap<>();

		avgInvoicesQuantity = new HashMap<>();
		devInvoicesQuantity = new HashMap<>();
		minInvoicesQuantity = new HashMap<>();
		maxInvoicesQuantity = new HashMap<>();

		if (!sponsorships.isEmpty()) {
			avgSponsorshipsAmount = this.convertToMap(this.repository.avgSponsorshipAmountGroupingByCurrency(sponsorId));
			devSponsorshipsAmount = this.convertToMap(this.repository.devSponsorshipAmountGroupingByCurrency(sponsorId));
			minSponsorshipsAmount = this.convertToMap(this.repository.minSponsorshipAmountGroupingByCurrency(sponsorId));
			maxSponsorshipsAmount = this.convertToMap(this.repository.maxSponsorshipAmountGroupingByCurrency(sponsorId));
		}

		if (!invoices.isEmpty()) {
			avgInvoicesQuantity = this.convertToMap(this.repository.avgInvoiceQuantityGroupingByCurrency(sponsorId));
			devInvoicesQuantity = this.convertToMap(this.repository.devInvoiceQuantityGroupingByCurrency(sponsorId));
			minInvoicesQuantity = this.convertToMap(this.repository.minInvoiceQuantityGroupingByCurrency(sponsorId));
			maxInvoicesQuantity = this.convertToMap(this.repository.maxInvoiceQuantityGroupingByCurrency(sponsorId));
		}

		totalNumberOfInvoicesWithLowTax = 0;
		totalNumberOfSponsorshipsWithLink = 0;

		totalNumberOfInvoicesWithLowTax = this.repository.findInvoicesTax21(super.getRequest().getPrincipal().getActiveRoleId());
		totalNumberOfSponsorshipsWithLink = this.repository.findSponsorshipsWithLink(super.getRequest().getPrincipal().getActiveRoleId());

		// 		String defaultCurrency = this.repository.findConfiguration().getDefaultCurrency();
		// podria hacer el find en el repositorio para meterla como indice tipo avgSponsorshipsAmount[defaultCurrency]
		// y los atributos del sponsordashboard serían Double simplemente
		dashboard = new SponsorDashboard();
		dashboard.setTotalNumberOfInvoicesWithLowTax(totalNumberOfInvoicesWithLowTax);
		dashboard.setTotalNumberOfSponsorshipsWithLink(totalNumberOfSponsorshipsWithLink);
		dashboard.setAvgSponsorshipsAmount(avgSponsorshipsAmount);
		dashboard.setDevSponsorshipsAmount(devSponsorshipsAmount);
		dashboard.setMinSponsorshipsAmount(minSponsorshipsAmount);
		dashboard.setMaxSponsorshipsAmount(maxSponsorshipsAmount);
		dashboard.setAvgInvoicesQuantity(avgInvoicesQuantity);
		dashboard.setDevInvoicesQuantity(devInvoicesQuantity);
		dashboard.setMinInvoicesQuantity(minInvoicesQuantity);
		dashboard.setMaxInvoicesQuantity(maxInvoicesQuantity);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalNumberOfInvoicesWithLowTax", "totalNumberOfSponsorshipsWithLink",//
			"avgSponsorshipsAmount", "devSponsorshipsAmount", "minSponsorshipsAmount", "maxSponsorshipsAmount",//
			"avgInvoicesQuantity", "devInvoicesQuantity", "minInvoicesQuantity", "maxInvoicesQuantity");

		super.getResponse().addData(dataset);

	}

	// Auxiliary methods

	private Map<String, Double> convertToMap(final Collection<Object[]> objectSet) {
		Map<String, Double> res = new HashMap<>();
		for (Object[] keyValuePair : objectSet) {
			String currency = (String) keyValuePair[0];
			Double statistic = (Double) keyValuePair[1];
			res.put(currency, statistic);
		}
		return res;
	}
}
