
package acme.features.any.trainingmodule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;

@Service
public class AnyTrainingModuleListService extends AbstractService<Any, TrainingModule> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<TrainingModule> objects;

		objects = this.repository.findAllTrainingModuleNotDraft();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset;
		String payload;

		dataset = super.unbind(object, "code", "creationMoment", "developer", "difficultyLevel");
		dataset.put("project", object.getProject().getCode());
		payload = String.format(//
			"%s; %s; %s; %s; %s", //
			object.getDetails(), //
			object.getUpdateMoment(), //
			object.getLink(), //
			object.getTotalEstimatedTime(), //
			object.getDeveloper());

		dataset.put("payload", payload);

		super.getResponse().addData(dataset);
	}
}
