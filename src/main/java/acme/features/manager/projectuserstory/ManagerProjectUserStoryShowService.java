
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
public class ManagerProjectUserStoryShowService extends AbstractService<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		// TODO
		//		boolean status;
		//		int masterId;
		//		ProjectUserStory pus;
		//
		//		masterId = super.getRequest().getData("id", int.class);
		//		pus = this.repository.findOneProjectUserStoryById(masterId);
		//		status = pus != null;

		super.getResponse().setAuthorised(true);
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
		SelectChoices projectChoices;
		SelectChoices userStoryChoices;
		Dataset dataset;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();

		projects = this.repository.findProjectsByManagerId(managerId);
		userStories = this.repository.findUserStoriesByManagerId(managerId);

		projectChoices = SelectChoices.from(projects, "title", object.getProject());
		userStoryChoices = SelectChoices.from(userStories, "title", object.getUserStory());

		dataset = super.unbind(object, "project", "userStory");
		dataset.put("projectChoices", projectChoices);
		dataset.put("userStoryChoices", userStoryChoices);

		super.getResponse().addData(dataset);
	}

}
