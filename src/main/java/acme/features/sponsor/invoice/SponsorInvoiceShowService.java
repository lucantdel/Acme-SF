
package acme.features.sponsor.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceShowService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int invoiceId;
		Sponsorship sph;
		Sponsor sponsor;

		invoiceId = super.getRequest().getData("id", int.class);
		sph = this.repository.findOneSponsorshipByInvoiceId(invoiceId);
		sponsor = this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());
		status = sph != null  //
			&& super.getRequest().getPrincipal().getActiveRole() == Sponsor.class //
			&& sph.getSponsor().equals(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice invoice;
		int id;

		id = super.getRequest().getData("id", int.class);
		invoice = this.repository.findOneInvoiceById(id);

		super.getBuffer().addData(invoice);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "draftMode");
		dataset.put("masterId", object.getSponsorship().getId());

		super.getResponse().addData(dataset);

	}
}
