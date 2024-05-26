
package acme.features.manager.userstory;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryListMineService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		/*
		 * El rol del usuario logueado debe ser Manager
		 * Las historias de usuario que aparecen deben pertenecer al manager logueado
		 */
		boolean status;
		int managerId;
		Collection<UserStory> userStories;
		Manager manager;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		manager = this.repository.findOneManagerById(managerId);

		status = super.getRequest().getPrincipal().hasRole(Manager.class);

		userStories = this.repository.findUserStoriesByManagerId(managerId);
		for (UserStory us : userStories)
			status = status && us.getManager().equals(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<UserStory> objects;
		int masterId;

		masterId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findUserStoriesByManagerId(masterId);

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
