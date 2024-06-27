
package acme.features.developers.trainingSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionDeleteService extends AbstractService<Developer, TrainingSession> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingSessionRepository repository;

	// AbstractService interface -------------------------------------------{


	@Override
	public void authorise() {
		boolean status;
		int trainingSessionId;
		TrainingSession object;

		trainingSessionId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingSessionById(trainingSessionId);
		status = object.getTrainingModule() != null && object.getTrainingModule().isDraftMode() && super.getRequest().getPrincipal().hasRole(object.getTrainingModule().getDeveloper());

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		int trainingSessionId;
		TrainingSession object;

		trainingSessionId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingSessionById(trainingSessionId);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "contactEmail", "link");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;
		this.repository.delete(object);
	}

	//puede que tengas que cometar/quitar el unbind
	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "contactEmail", "link", "draftMode");

		super.getResponse().addData(dataset);
	}

}
