
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
	// cargo objeto vacio y le pongo lo necesario que vendria del predeterminado.
	public void load() {
		Sponsorship object;
		Sponsor sponsor;

		Principal principal = super.getRequest().getPrincipal();
		sponsor = this.repository.findOneSponsorById(principal.getActiveRoleId());
		object = new Sponsorship();
		object.setDraftMode(true);
		object.setSponsor(sponsor);
		Date creationMoment;
		creationMoment = MomentHelper.getCurrentMoment();
		object.setMoment(creationMoment);

		super.getBuffer().addData(object);
	}

	@Override
	// cargo o actualizo el objeto con  lo que el principal me mete por el formulario desde sus imputs
	// para validarlos  tenniendo un objeto como tal y poder hacer comprobaciones
	// con el framework y despues con el validate.
	public void bind(final Sponsorship object) {
		assert object != null;

		int projectId;
		Project project;
		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);
		object.setProject(project);

		super.bind(object, "code", "moment", "startDuration", "finalDuration", "amount", "type", "email", "link", "project");

	}

	@Override
	// validamos tras las del framework usando el objeto mandado por el principal
	public void validate(final Sponsorship object) {
		assert object != null;

		//Code 
		// comprobar que no exista un patrocinio con code igual antes
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing;
			existing = this.repository.findSponsorshipByCode(object.getCode());
			super.state(existing == null, "code", "sponsor.sponsorship.form.error.duplicate");
		}

		//Fechas
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
		}

	}

	@Override
	// si no habia errores se hace la accion
	public void perform(final Sponsorship object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	// si ha habido errores, te devuelve el objeto al formulario
	// con los mensajes de error correspondientes para volver a rellenarlo bien.
	public void unbind(final Sponsorship object) {
		assert object != null;
		SelectChoices choices;
		SelectChoices projectsChoices;
		Collection<Project> projects;

		Dataset dataset;

		choices = SelectChoices.from(SponsorshipType.class, object.getType());
		projects = this.repository.findAllProjects();
		// como en el bind hemos actualizado el valor del project
		// pues este que ha sido seleccionado por un DropDown por su code
		// lo pasamos a objeto project, buscando entre los projects por la variable code el valor de code que teniamos guardo.
		// seleccionar dentro los posibles proyectos del projects, el que del desplegable los projectos por su codigo
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "moment", "startDuration", "finalDuration", "amount", "type", "email", "link", "draftMode");

		dataset.put("sponsorshipType", choices);
		dataset.put("project", projectsChoices.getSelected().getLabel());
		dataset.put("projects", projectsChoices);

		super.getResponse().addData(dataset);

	}
}
