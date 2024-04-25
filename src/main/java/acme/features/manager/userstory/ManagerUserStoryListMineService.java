
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

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		status = super.getRequest().getPrincipal().getActiveRole() == Manager.class;

		userStories = this.repository.findUserStoriesByManagerId(managerId);
		for (UserStory us : userStories)
			status = status && us.getManager().equals(this.repository.findOneManagerById(managerId));

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

		dataset = super.unbind(object, "title", "estimatedCost", "priority");

		// Cambiar true o false por si o no
		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		super.getResponse().addData(dataset);
	}

}
