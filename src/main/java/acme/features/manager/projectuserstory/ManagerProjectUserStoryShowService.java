
package acme.features.manager.projectuserstory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.ProjectUserStory;
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

		Dataset dataset;

		dataset = this.unbind(object, "project", "userStory");

		dataset.put("projectTitle", object.getProject().getTitle());
		dataset.put("userStoryTitle", object.getUserStory().getTitle());

		super.getResponse().addData(dataset);
	}

}
