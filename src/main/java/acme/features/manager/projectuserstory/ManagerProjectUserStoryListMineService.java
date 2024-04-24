
package acme.features.manager.projectuserstory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.ProjectUserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryListMineService extends AbstractService<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		/*
		 * El rol del usuario logueado debe ser Manager
		 * Los proyectos de las relaciones que aparecen deben pertenecer al manager logueado
		 */
		boolean status;
		int managerId;
		Collection<ProjectUserStory> pus;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		status = super.getRequest().getPrincipal().getActiveRole() == Manager.class;

		pus = this.repository.findProjectUserStoriesByManagerId(managerId);
		for (ProjectUserStory p : pus)
			status = status && p.getProject().getManager() == this.repository.findOneManagerById(managerId);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<ProjectUserStory> objects;
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();

		objects = this.repository.findProjectUserStoriesByManagerId(managerId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		Dataset dataset;

		dataset = this.unbind(object, "project", "userStory");

		dataset.put("project", object.getProject().getTitle());
		dataset.put("userStory", object.getUserStory().getTitle());

		super.getResponse().addData(dataset);
	}

}
