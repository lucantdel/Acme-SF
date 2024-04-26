
package acme.features.developers.trainingSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionListService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<TrainingSession> objects;
		int moduleId;

		moduleId = super.getRequest().getData("trainingModuleId", int.class);
		objects = this.repository.findAllTrainingSessionBytrainingModuleId(moduleId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;
		int tmID;
		final boolean showCreate;
		String payload;

		showCreate = object.getTrainingModule().isDraftMode();
		tmID = super.getRequest().getData("trainingModuleId", int.class);

		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "draftMode");
		payload = String.format(//
			"%s; %s; %s; %s", //
			object.getLocation(), //
			object.getInstructor(), //
			object.getContactEmail(), //
			object.getLink());
		dataset.put("payload", payload);

		super.getResponse().addGlobal("showCreate", showCreate);
		super.getResponse().addGlobal("trainingModuleId", tmID);
		super.getResponse().addData(dataset);
	}
}
