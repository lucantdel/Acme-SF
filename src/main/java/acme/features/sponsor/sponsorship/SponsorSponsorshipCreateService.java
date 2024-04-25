
package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipType;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Sponsorship object;
		Sponsor sponsor;

		Principal principal = super.getRequest().getPrincipal();
		sponsor = this.repository.findOneSponsorById(principal.getActiveRoleId());
		object = new Sponsorship();
		object.setDraftMode(true);
		object.setSponsor(sponsor);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "moment", "startDuration", "finalDuration", "amount", "type", "email", "link", "draftMode", "project");

		object.setProject(project);

	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;

		// comprobar que no exista un patrocinio con code igual antes
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing;

			existing = this.repository.findOneSponsorshipByCode(object.getCode());
			super.state(existing == null, "code", "sponsor.sponsorship.form.error.duplicated");
		}

		// mirar si es in the past
		if (!super.getBuffer().getErrors().hasErrors("startDuration"))
			super.state(MomentHelper.isAfter(object.getStartDuration(), object.getMoment()), "startDuration", "sponsor.sponsorship.form.error.duration-start-date-not-valid");

		// mirar que es al menos 1 mes de duracion
		if (!super.getBuffer().getErrors().hasErrors("finalDuration")) {
			Date startDuration;
			Date finalDuration;
			boolean isMinimumDuration;
			boolean finalDurationIsAfterStart;

			startDuration = object.getStartDuration();
			finalDuration = object.getFinalDuration();
			isMinimumDuration = MomentHelper.isLongEnough(startDuration, finalDuration, 1, ChronoUnit.MONTHS);
			finalDurationIsAfterStart = MomentHelper.isAfter(finalDuration, startDuration);

			super.state(isMinimumDuration && finalDurationIsAfterStart, "startDuration", "sponsor.sponsorship.form.error.duration-not-valid");
		}

		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			super.state(object.getAmount().getAmount() > 0, "amount", "sponsor.sponsorship.form.error.amount-must-be-positive");
			List<SystemConfiguration> sc = this.repository.findSystemConfiguration();
			final boolean foundCurrency = Stream.of(sc.get(0).acceptedCurrencies.split(",")).anyMatch(c -> c.equals(object.getAmount().getCurrency()));

			super.state(foundCurrency, "amount", "sponsor.sponsorship.form.error.currency-not-supported");
		}

	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

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
		// seleccionar dentro del desplegable los projectos por su codigo
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "moment", "startDuration", "finalDuration", "amount", "type", "emailContact", "link", "draftMode", "project");

		dataset.put("type", types);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);

		super.getResponse().addData(dataset);

	}
}
