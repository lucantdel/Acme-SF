
package acme.features.manager.projectuserstory;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryShowService extends AbstractService<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		/*
		 * El rol del usuario logueado debe ser Manager
		 * El proyecto de la relacion que aparecen debe pertenecer al manager logueado
		 */
		boolean status;
		ProjectUserStory pus;
		Manager manager;

		pus = this.repository.findOneProjectUserStoryById(super.getRequest().getData("id", int.class));
		manager = this.repository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());

		status = super.getRequest().getPrincipal().getActiveRole() == Manager.class //
			&& pus.getProject().getManager().equals(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProjectUserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProjectUserStoryById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		int managerId;
		Collection<Project> projects;
		Collection<UserStory> userStories;
		Collection<UserStory> publishedUserStories;
		Collection<UserStory> myUserStories;
		SelectChoices projectChoices;
		SelectChoices userStoryChoices;
		Dataset dataset;
		boolean projectDraftMode;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();

		projects = this.repository.findProjectsByManagerId(managerId);

		publishedUserStories = this.repository.findAllPublishedUserStories();
		myUserStories = this.repository.findUserStoriesByManagerId(managerId);
		userStories = new HashSet<>(publishedUserStories);
		userStories.addAll(myUserStories);

		projectChoices = SelectChoices.from(projects, "title", object.getProject());
		userStoryChoices = SelectChoices.from(userStories, "title", object.getUserStory());

		projectDraftMode = object.getProject().isDraftMode();

		dataset = super.unbind(object, "project", "userStory");
		dataset.put("projectChoices", projectChoices);
		dataset.put("userStoryChoices", userStoryChoices);
		dataset.put("projectDraftMode", projectDraftMode);

		super.getResponse().addData(dataset);
	}

}
