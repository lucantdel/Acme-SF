
package acme.features.developers.trainingmodule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleListService extends AbstractService<Developer, TrainingModule> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<TrainingModule> objects;
		int developerId;

		developerId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findAllTrainingModuleByDeveloperId(developerId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset;
		String payload;

		dataset = super.unbind(object, "code", "creationMoment", "developer", "difficultyLevel");
		dataset.put("project", object.getProject().getCode());
		dataset.put("draftMode", object.isDraftMode());
		dataset.put("numberOfTrainingSessions", (int) this.repository.findAllTrainingSessionsWithSameTrainingModuleId(object.getId()).stream().count());
		payload = String.format(//
			"%s; %s; %s; %s", //
			object.getDetails(), //
			object.getUpdateMoment(), //
			object.getLink(), //
			object.getTotalEstimatedTime());
		dataset.put("payload", payload);

		super.getResponse().addData(dataset);
	}
}
