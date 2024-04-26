
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
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipType;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipUpdateService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {
		boolean status;
		Sponsorship sponsorship;
		Sponsor sponsor;

		sponsorship = this.repository.findOneSponsorshipById(super.getRequest().getData("id", int.class));
		sponsor = sponsorship == null ? null : this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());

		status = super.getRequest().getPrincipal().getActiveRole() == Sponsor.class //
			&& sponsorship.getSponsor().equals(sponsor) && sponsorship.isDraftMode();
		//		status = sponsorship != null && sponsorship.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	// creo uno nuevo y lo relleno segun el que me han dado
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	// enseño en el formulario el objeto para actualizarlo
	public void bind(final Sponsorship object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "moment", "startDuration", "finalDuration", "amount", "type", "email", "link", "project");
		object.setProject(project);

	}

	@Override
	// valido los datos cambiados
	public void validate(final Sponsorship object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing;

			existing = this.repository.findOneSponsorshipByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "sponsor.sponsorship.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDuration"))
			super.state(MomentHelper.isAfter(object.getStartDuration(), object.getMoment()), "startDuration", "sponsor.sponsorship.form.error.duration-start-date-not-valid");

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
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "moment", "startDuration", "finalDuration", "amount", "type", "email", "link", "draftMode", "project");

		dataset.put("sponsorshipType", types);
		dataset.put("project", projectsChoices.getSelected().getLabel());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);

	}
}
