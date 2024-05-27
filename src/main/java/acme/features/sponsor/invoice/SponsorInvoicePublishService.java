
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Sponsor;

@Service
public class SponsorInvoicePublishService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int invoiceId;
		Sponsor sponsor;
		Sponsorship sph;
		Invoice invoice;

		invoiceId = super.getRequest().getData("id", int.class);
		sph = this.repository.findOneSponsorshipByInvoiceId(invoiceId);
		sponsor = this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());
		invoice = this.repository.findOneInvoiceById(invoiceId);
		status = sph != null && //
			sph.isDraftMode() && //
			invoice.isDraftMode() && //
			super.getRequest().getPrincipal().getActiveRole() == Sponsor.class //
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
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");

	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;

			existing = this.repository.findOneInvoiceByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "sponsor.invoice.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			Date registrationTime;
			Date dueDate;
			boolean isMinimumDuration;
			boolean dueDateIsAfterRegistrationTime;

			registrationTime = object.getRegistrationTime();
			dueDate = object.getDueDate();
			isMinimumDuration = MomentHelper.isLongEnough(registrationTime, dueDate, 1, ChronoUnit.MONTHS);
			dueDateIsAfterRegistrationTime = MomentHelper.isAfter(dueDate, registrationTime);

			super.state(isMinimumDuration && dueDateIsAfterRegistrationTime, "dueDate", "sponsor.invoice.form.error.due-date-not-valid");
		}

		if (!super.getBuffer().getErrors().hasErrors("registrationTime") && object.getSponsorship() != null)
			super.state(MomentHelper.isBefore(object.getSponsorship().getMoment(), object.getRegistrationTime()), "registrationTime", "sponsor.invoice.form.error.date-before-moment");

		if (!super.getBuffer().getErrors().hasErrors("quantity") && object.getSponsorship() != null) {
			super.state(object.getQuantity().getAmount() > 0, "quantity", "sponsor.invoice.form.error.quantity-must-be-positive");
			List<SystemConfiguration> sc = this.repository.findSystemConfiguration();
			final boolean foundCurrency = Stream.of(sc.get(0).acceptedCurrencies.split(",")).anyMatch(c -> c.equals(object.getQuantity().getCurrency()));

			super.state(foundCurrency, "quantity", "sponsor.invoice.form.error.currency-not-supported");

			super.state(object.getQuantity().getCurrency().equals(object.getSponsorship().getAmount().getCurrency()), "quantity", "sponsor.invoice.form.error.currency");

			int sponsorshipId = object.getSponsorship().getId();

			double sponsorshipAmount = object.getSponsorship().getAmount().getAmount();
			Collection<Invoice> invoicesForSponsorship = this.repository.findAllPublisedInvoicesBySponsorShipsId(sponsorshipId);
			double sumOfInvoicesTotalAmount = 0.0;
			for (Invoice invoice : invoicesForSponsorship)
				sumOfInvoicesTotalAmount += invoice.totalAmount().getAmount();

			sumOfInvoicesTotalAmount += object.totalAmount().getAmount();
			// para no permitir publicar la factura si lo que resta por pagar es menor que lo que se quiere publicar 

			super.state(sponsorshipAmount >= sumOfInvoicesTotalAmount, "quantity", "sponsor.invoice.form.error.sponsorship-amount");
		}
		if (!super.getBuffer().getErrors().hasErrors("tax")) {
			Double tax;

			tax = object.getTax();
			super.state(100 >= tax && tax >= 0, "tax", "sponsor.invoice.form.error.invalid-tax");

		}
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
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
