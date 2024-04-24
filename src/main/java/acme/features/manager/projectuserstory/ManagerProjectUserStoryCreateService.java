
package acme.features.manager.projectuserstory;

import java.util.Collection;

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
		// TODO: Mensajes de error por restricciones
		assert object != null;
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
		Collection<UserStory> userStories;
		SelectChoices projectChoices;
		SelectChoices userStoryChoices;
		Dataset dataset;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();

		projects = this.repository.findProjectsByManagerId(managerId);
		userStories = this.repository.findUserStoriesByManagerId(managerId);

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
