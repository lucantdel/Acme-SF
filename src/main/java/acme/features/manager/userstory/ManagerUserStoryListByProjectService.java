
package acme.features.manager.userstory;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryListByProjectService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		/*
		 * Comprobar que el usuario logueado es Manager
		 * El proyecto al que pertenecen las historias de usuario que aparecen deben pertenecer al manager logueado
		 */
		boolean status;
		int managerId;
		int masterId;
		Project project;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();

		masterId = super.getRequest().getData("masterId", int.class);
		project = this.repository.findOneProjectById(masterId);

		status = super.getRequest().getPrincipal().hasRole(Manager.class) && project.getManager().equals(this.repository.findOneManagerById(managerId));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<UserStory> objects;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findUserStoriesByProjectId(masterId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;
		String payload;

		dataset = super.unbind(object, "title", "estimatedCost", "priority");
		payload = String.format(//
			"%s; %s; %s; %s", //
			object.getDescription(), //
			object.getManager().getIdentity().getFullName(), //
			object.getAcceptanceCriteria(), //
			object.getLink());
		dataset.put("payload", payload);

		// Cambiar true o false por si o no
		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		super.getResponse().addData(dataset);
	}

}
