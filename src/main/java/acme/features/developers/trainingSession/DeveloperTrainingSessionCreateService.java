
package acme.features.developers.trainingSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionCreateService extends AbstractService<Developer, TrainingSession> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int trainingModuleId;
		TrainingModule trainingModule;

		trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);
		trainingModule = this.repository.findOneTrainingModuleByTmId(trainingModuleId);
		status = trainingModule != null && trainingModule.isDraftMode() && super.getRequest().getPrincipal().hasRole(trainingModule.getDeveloper());

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		TrainingSession object;
		int trainingModuleId;
		Date startPeriod;
		Date endPeriod;
		TrainingModule tMod;

		trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);
		//masterId = super.getRequest().getData("masterId", int.class);
		tMod = this.repository.findOneTrainingModuleByTmId(trainingModuleId);
		startPeriod = MomentHelper.deltaFromMoment(this.repository.findOneTrainingModuleByTmId(trainingModuleId).getCreationMoment(), 7, ChronoUnit.DAYS);
		endPeriod = MomentHelper.deltaFromMoment(startPeriod, 7, ChronoUnit.DAYS);

		object = new TrainingSession();
		object.setStartPeriod(startPeriod);
		object.setEndPeriod(endPeriod);
		object.setTrainingModule(tMod);
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
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;

			existing = this.repository.findOneTrainingSessionByCode(object.getCode());
			super.state(existing == null, "code", "developer.training-session.form.error.duplicated-ts-code");
		}
		if (!super.getBuffer().getErrors().hasErrors("startPeriod")) {
			Date minStartPeriod;
			minStartPeriod = MomentHelper.deltaFromMoment(this.repository.findOneTrainingModuleByTmId(object.getTrainingModule().getId()).getCreationMoment(), 7, ChronoUnit.DAYS);

			super.state(MomentHelper.isAfterOrEqual(object.getStartPeriod(), minStartPeriod), "startPeriod", "developer.training-session.form.error.startPeriod");

		}
		if (!super.getBuffer().getErrors().hasErrors("endPeriod")) {
			Date minEndPeriod;
			minEndPeriod = MomentHelper.deltaFromMoment(object.getStartPeriod(), 7, ChronoUnit.DAYS);

			super.state(MomentHelper.isAfterOrEqual(object.getEndPeriod(), minEndPeriod), "endPeriod", "developer.training-session.form.error.endPeriod");

		}
	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		object.setDraftMode(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "contactEmail", "link", "draftMode");
		dataset.put("trainingModuleId", super.getRequest().getData("trainingModuleId", int.class));

		super.getResponse().addData(dataset);
	}
}
