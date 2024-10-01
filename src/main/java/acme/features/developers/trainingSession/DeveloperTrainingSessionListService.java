
package acme.features.developers.trainingSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
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
		boolean status;
		int trainingModuleId;
		TrainingModule object;

		trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);
		object = this.repository.findOneTrainingModuleByTmId(trainingModuleId);
		status = object != null && (!object.isDraftMode() || super.getRequest().getPrincipal().hasRole(object.getDeveloper()));

		super.getResponse().setAuthorised(status);
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

		final boolean showCreate;
		String payload;

		showCreate = object.getTrainingModule().isDraftMode();

		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "draftMode");
		payload = String.format(//
			"%s; %s; %s; %s", //
			object.getLocation(), //
			object.getInstructor(), //
			object.getContactEmail(), //
			object.getLink());
		dataset.put("payload", payload);

		super.getResponse().addGlobal("showCreate", showCreate);

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<TrainingSession> objects) {
		assert objects != null;

		int trainingModuleId;
		//TrainingModule tMod;
		final boolean showCreate;

		trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);
		//tMod = this.repository.findOneTrainingModuleByTmId(trainingModuleId);
		super.getResponse().addGlobal("trainingModuleId", trainingModuleId);

		showCreate = this.repository.findOneTrainingModuleByTmId(trainingModuleId).isDraftMode();

		super.getResponse().addGlobal("showCreate", showCreate);

	}
}
