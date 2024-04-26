
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		/*
		 * El rol del usuario logueado debe ser Sponsor
		 * El patrocinio de la relacion que aparecen debe pertenecer al Sponsor logueado
		 */
		boolean status;
		Sponsorship sph;
		Sponsor sponsor;

		sph = this.repository.findOneSponsorshipById(super.getRequest().getData("id", int.class));
		sponsor = this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());

		status = super.getRequest().getPrincipal().getActiveRole() == Sponsor.class //
			&& sph.getSponsor().equals(sponsor);
		//			 meter aqui un Errors para que no de panic con un 500 si me meto no autorizado
		//			 del rollo: 
		//			 y meterlo tmb en el form
		//	
		//			 asegurar que el estado del boton confirmed esta seleccionado
		//					if (!super.getBuffer().getErrors().hasErrors("confirm")) {
		//						final boolean confirm = super.getRequest().getData("confirm", boolean.class);
		//			
		//						// aqui mira el estado
		//					super.state(confirm, "confirm", "any.claim.form.error.not-confirmed");
		//					}

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		// para que tenga desplegable del project que quiere escoger
		SelectChoices choices;
		SelectChoices projectsChoices;
		Collection<Project> projects;

		Dataset dataset;
		choices = SelectChoices.from(SponsorshipType.class, object.getType());
		projects = this.repository.findAllProjects();
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "moment", "startDuration", "finalDuration", "amount", "type", "email", "link", "draftMode", "project");

		dataset.put("sponsorshipType", choices);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);
	}

}
