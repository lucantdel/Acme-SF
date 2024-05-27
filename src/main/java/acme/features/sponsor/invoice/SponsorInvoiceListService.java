
package acme.features.sponsor.invoice;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceListService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Sponsorship sph;
		Sponsor sponsor;

		masterId = super.getRequest().getData("masterId", int.class);
		sph = this.repository.findOneSponsorshipById(masterId);
		sponsor = this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());
		status = sph != null && //
			super.getRequest().getPrincipal().getActiveRole() == Sponsor.class //
			&& sph.getSponsor().equals(sponsor);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Invoice> invoices;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		invoices = this.repository.findAllInvoicesByMasterId(masterId);

		super.getBuffer().addData(invoices);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;
		Dataset dataset;
		Invoice invoice;
		Money totalAmount = new Money();
		double amount;
		String currency;
		String payload;

		invoice = this.repository.findOneInvoiceByCode(object.getCode());
		currency = invoice.getQuantity().getCurrency();
		amount = invoice.totalAmount().getAmount();

		totalAmount.setAmount(amount);
		totalAmount.setCurrency(currency);
		dataset = super.unbind(object, "code", "tax", "registrationTime", "dueDate", "draftMode");
		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");
		dataset.put("totalAmount", totalAmount);

		payload = String.format(//
			"%s; %s", //
			object.getQuantity(), //
			object.getLink());

		dataset.put("payload", payload);
		super.getResponse().addData(dataset);

	}

	@Override
	public void unbind(final Collection<Invoice> objects) {
		assert objects != null;

		int masterId;
		Sponsorship sponsorship;
		final boolean showCreate;

		masterId = super.getRequest().getData("masterId", int.class);
		sponsorship = this.repository.findOneSponsorshipById(masterId);
		showCreate = sponsorship.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor());

		super.getResponse().addGlobal("masterId", masterId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}
}
