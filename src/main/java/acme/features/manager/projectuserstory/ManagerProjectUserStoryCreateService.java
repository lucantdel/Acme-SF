
package acme.features.manager.projectuserstory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
public class ManagerProjectUserStoryCreateService extends AbstractService<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		/*
		 * El rol del usuario logueado debe ser Manager
		 */
		boolean status;

		status = super.getRequest().getPrincipal().getActiveRole() == Manager.class;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProjectUserStory object;

		object = new ProjectUserStory();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProjectUserStory object) {
		assert object != null;

		super.bind(object, "project", "userStory");
	}

	@Override
	public void validate(final ProjectUserStory object) {
		/*
		 * No deben existir relaciones repetidas, es decir, mismo proyecto e historia de usuario
		 */
		assert object != null;
		Project project;
		UserStory userStory;

		project = object.getProject();
		userStory = object.getUserStory();

		if (!super.getBuffer().getErrors().hasErrors("project")) {
			ProjectUserStory existing;

			if (userStory != null) {
				existing = this.repository.findOneProjectUserStoryByProjectIdAndUserStoryId(project.getId(), userStory.getId());
				super.state(existing == null, "*", "manager.project-user-story.form.error.existing-assignment");
			}
			// Necesaria? Si en las choices solo incluyo proyectos en borrador nunca se llamará a este error
			// La dejo por si acaso
			super.state(project.isDraftMode(), "project", "manager.project-user-story.form.error.create-assignment-published-project");
		}
	}

	@Override
	public void perform(final ProjectUserStory object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		int managerId;
		Collection<Project> projects;
		Set<UserStory> userStories;
		Collection<UserStory> publishedUserStories;
		Collection<UserStory> myUserStories;
		SelectChoices projectChoices;
		SelectChoices userStoryChoices;
		Dataset dataset;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();

		projects = this.repository.findDraftModeProjectsByManagerId(managerId);

		publishedUserStories = this.repository.findAllPublishedUserStories();
		myUserStories = this.repository.findUserStoriesByManagerId(managerId);
		userStories = new HashSet<>(publishedUserStories);
		userStories.addAll(myUserStories);

		projectChoices = SelectChoices.from(projects, "title", object.getProject());
		userStoryChoices = SelectChoices.from(userStories, "title", object.getUserStory());

		dataset = super.unbind(object, "project", "userStory");
		dataset.replace("project", projectChoices.getSelected().getKey());
		dataset.put("projectChoices", projectChoices);
		dataset.replace("userStory", userStoryChoices.getSelected().getKey());
		dataset.put("userStoryChoices", userStoryChoices);

		super.getResponse().addData(dataset);
	}

}
