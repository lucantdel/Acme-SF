
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
public class SponsorInvoiceUpdateService extends AbstractService<Sponsor, Invoice> {

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
		Invoice object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneInvoiceById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "project");
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

			int sponsorshipId = object.getSponsorship().getId();

			super.state(this.repository.findAllPublisedInvoicesBySponsorShipsId(sponsorshipId).size() == 0 || object.getQuantity().getCurrency().equals(object.getSponsorship().getAmount().getCurrency()), "quantity", "sponsor.invoice.form.error.currency");
			// La moneda que define la moneda final,  que fijará esta tanto en el sponshorship como en las invoices, será la moneda del primer invoice publicado 
			// ( que debe a su vez estar coincidiendo con el del sponsorship en ese momento de publicación)

			// Luego, si tenemos una invoice ya publicada del mismo sponsorship, la moneda a usar será la del sponsorship en ese momento ya que al publicarla pues se ha fijado esa moneda.
			// Ya que si actualizamos la invoice a una moneda diferente a la fijada jamás podrá ser publicada y por lo tanto será inutil el crearla.
			double sponsorshipAmount = object.getSponsorship().getAmount().getAmount();
			Collection<Invoice> invoicesForSponsorship = this.repository.findAllPublisedInvoicesBySponsorShipsId(sponsorshipId);
			double sumOfInvoicesTotalAmount = 0.0;
			for (Invoice invoice : invoicesForSponsorship)
				sumOfInvoicesTotalAmount += invoice.totalAmount().getAmount();

			sumOfInvoicesTotalAmount += object.totalAmount().getAmount();
			// para no permitir actualizar facturas que sumen más de lo que le resta por pagar del sponsorship

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
		//		assert object != null;

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
