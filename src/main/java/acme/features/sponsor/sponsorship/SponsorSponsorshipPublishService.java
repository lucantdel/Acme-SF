
package acme.features.sponsor.sponsorship;

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
import acme.client.views.SelectChoices;
import acme.entities.invoices.Invoice;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipType;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {
		boolean status;
		Sponsorship sph;
		Sponsor sponsor;

		sph = this.repository.findOneSponsorshipById(super.getRequest().getData("id", int.class));

		sponsor = sph == null ? null : this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());
		status = sph != null && super.getRequest().getPrincipal().getActiveRole() == Sponsor.class && sph.getSponsor().equals(sponsor) //
			&& sph.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSponsorshipById(id);

		Date publishMoment;
		publishMoment = MomentHelper.getCurrentMoment();
		object.setMoment(publishMoment);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;
		super.bind(object, "code", "moment", "startDuration", "finalDuration", "type", "amount", "email", "link", "project");

	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;

		//Code 
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship sponsorshipSameCode;
			sponsorshipSameCode = this.repository.findSponsorshipByCode(object.getCode());
			if (sponsorshipSameCode != null) {
				// si ese spSame de la base de datos (que hemos encontrado buscando por el code de la peticion) no es nulo (es decir, no exisíta ya),
				// es porque este encontrado debe ser exactamente el mismo que tomamos en la peticion para publicarlo (y no otro con el mismo code pero siendo diferente)
				// luego sus ids deben ser los mismos.
				// Sino, debe saltar el error ya que tienen el mismo id y son dos diferentes.

				int id = sponsorshipSameCode.getId();
				super.state(id == object.getId(), "code", "sponsor.sponsorship.form.error.duplicate");
				// si no tienen el mismo id es porque tienen el mismo code pero son distintos
				// luego estarían duplicados
			}
		}

		//Fechas
		// vamos a tomar valores de fechas para restringir los valores 
		// dentro de unos rangos viables de tiempo
		String dateString = "2201/01/01 00:00";
		Date futureMostDate = MomentHelper.parse(dateString, "yyyy/MM/dd HH:mm");
		dateString = "2200/12/25 00:00";
		Date latestStartDate = MomentHelper.parse(dateString, "yyyy/MM/dd HH:mm");

		if (object.getStartDuration() != null && object.getFinalDuration() != null) {

			if (!super.getBuffer().getErrors().hasErrors("startDuration"))
				super.state(MomentHelper.isAfter(object.getStartDuration(), object.getMoment()), "startDuration", "sponsor.sponsorship.form.error.startDuration");

			// para rangos
			if (!super.getBuffer().getErrors().hasErrors("startDuration"))
				super.state(MomentHelper.isBefore(object.getStartDuration(), latestStartDate), "startDuration", "sponsor.sponsorship.form.error.startDurationOutOfBounds");
			// sino es es antes, estaria empezando un sp detro de muchisimo tiempo, inviable.

			if (!super.getBuffer().getErrors().hasErrors("finalDuration"))
				super.state(MomentHelper.isAfter(object.getFinalDuration(), object.getMoment()), "finalDuration", "sponsor.sponsorship.form.error.finalDuration");

			if (!super.getBuffer().getErrors().hasErrors("startDuration"))
				super.state(MomentHelper.isBefore(object.getStartDuration(), object.getFinalDuration()), "startDuration", "sponsor.sponsorship.form.error.startDurationBeforeEndDate");
			// para rangos
			if (!super.getBuffer().getErrors().hasErrors("finalDuration"))
				super.state(MomentHelper.isBefore(object.getFinalDuration(), futureMostDate), "finalDuration", "sponsor.sponsorship.form.error.dateOutOfBounds");
			// sino es es antes, estaria empezando un sp detro de muchisimo tiempo, inviable.

			if (!super.getBuffer().getErrors().hasErrors("finalDuration"))
				super.state(MomentHelper.isLongEnough(object.getStartDuration(), object.getFinalDuration(), 1, ChronoUnit.MONTHS), "finalDuration", "sponsor.sponsorship.form.error.period");

		}
		if (object.getStartDuration() == null)
			if (!super.getBuffer().getErrors().hasErrors("finalDuration"))
				super.state(object.getStartDuration() != null, "finalDuration", "sponsor.sponsorhsip.form.error.nullStart");
		if (object.getFinalDuration() == null)
			if (!super.getBuffer().getErrors().hasErrors("startDuration"))
				super.state(object.getFinalDuration() != null, "startDuration", "sponsor.sponsorhsip.form.error.nullFinal");

		//Cantidad
		if (object.getAmount() != null) {

			if (!super.getBuffer().getErrors().hasErrors("amount"))
				super.state(object.getAmount().getAmount() > 0, "amount", "sponsor.sponsorship.form.error.amount-must-be-positive");

			if (!super.getBuffer().getErrors().hasErrors("amount")) {
				List<SystemConfiguration> sc = this.repository.findSystemConfiguration();
				final boolean foundCurrency = Stream.of(sc.get(0).acceptedCurrencies.split(",")).anyMatch(c -> c.equals(object.getAmount().getCurrency()));
				super.state(foundCurrency, "amount", "sponsor.sponsorship.form.error.currency-not-supported");
			}

			if (!super.getBuffer().getErrors().hasErrors("amount"))
				super.state(object.getAmount().getAmount() <= 1000000.00 && object.getAmount().getAmount() >= 0.00, "amount", "sponsor.sponsorship.form.error.amountOutOfBounds");

			//			if (!super.getBuffer().getErrors().hasErrors("amount"))
			//				super.state(this.repository.countPublishedInvoicesBySponsorshipId(object.getId()) == 0 || object.getAmount().getCurrency().equals(this.repository.findOneSponsorshipById(object.getId()).getAmount().getCurrency()), "amount",
			//					"sponsor.sponsorship.form.error.currencyChange");

			if (!super.getBuffer().getErrors().hasErrors("amount")) {
				Double amount = object.getAmount().getAmount();
				Double total = 0.0;
				boolean allPublished = true;
				Collection<Invoice> invoices = this.repository.findAllInvoicesBySponsorshipId(object.getId());
				for (Invoice i : invoices)
					if (!i.isDraftMode())
						total += i.totalAmount().getAmount();
					else
						allPublished = false;

				super.state(amount.equals(total) && allPublished, "amount", "sponsor.sponsorship.form.error.invoices");
			}
		}

		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.isDraftMode(), "code", "sponsor.sponsorship.form.error.published");

	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		SelectChoices types;
		SelectChoices projectsChoices;
		Collection<Project> projects;

		Dataset dataset;
		types = SelectChoices.from(SponsorshipType.class, object.getType());
		projects = this.repository.findAllProjects();
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "moment", "startDuration", "finalDuration", "amount", "type", "email", "link", "draftMode", "project");

		dataset.put("sponsorshipType", types);
		dataset.put("project", projectsChoices.getSelected().getLabel());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);

	}
}
