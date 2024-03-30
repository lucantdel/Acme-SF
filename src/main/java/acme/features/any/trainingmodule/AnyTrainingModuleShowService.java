
package acme.features.any.trainingmodule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;

@Service
public class AnyTrainingModuleShowService extends AbstractService<Any, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		TrainingModule trainingModule;

		id = super.getRequest().getData("id", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(id);
		status = trainingModule != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingModuleById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset;

		Collection<TrainingSession> objectsTS = this.repository.findAllTrainingSessionsWithSameTrainingModuleId(object.getId());
		int totaltime = 0;
		for (TrainingSession ts : objectsTS)
			totaltime += MomentHelper.computeDuration(ts.getStartPeriod(), ts.getEndPeriod()).toHoursPart();

		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "developer");
		dataset.put("totalTime", totaltime);
		dataset.put("project", object.getProject().getCode());

		super.getResponse().addData(dataset);
	}
}
