
package acme.features.developers.trainingmodule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainingModule.Difficulty;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModulePublishService extends AbstractService<Developer, TrainingModule> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingModule object;
		Developer developer;

		masterId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingModuleById(masterId);
		developer = object == null ? null : object.getDeveloper();
		status = object != null && object.isDraftMode() && super.getRequest().getPrincipal().hasRole(developer);

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
	public void bind(final TrainingModule object) {
		assert object != null;

		super.bind(object, "code", "details", "difficultyLevel", "updateMoment", "link", "project", "totalEstimatedTime");

	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		//Puede que tengas q meter el validate del update moment del feat update
		///////////////////////

		Collection<TrainingSession> sessiones;
		Boolean hayDrafts;

		sessiones = this.repository.findAllTrainingSessionsWithSameTrainingModuleId(object.getId());

		hayDrafts = sessiones.stream().allMatch(ses -> ses.isDraftMode() == false);

		if (hayDrafts == false)
			super.state(hayDrafts, "*", "developer.training-module.form.error.ts-exist-Draft");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule existing;

			existing = this.repository.findOneTrainingModuleByReference(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "developer.training-module.form.error.duplicated-tm-code");
		}
		if (!super.getBuffer().getErrors().hasErrors("numberOfTrainingSessions")) {
			int numTotalTS;

			numTotalTS = (int) this.repository.findAllTrainingSessionsWithSameTrainingModuleId(object.getId()).stream().count();
			super.state(0 < numTotalTS, "*", "developer.training-module.form.error.numberOfTrainingSessions");
		}

		if (object.getUpdateMoment() != null && object.getCreationMoment() != null && !super.getBuffer().getErrors().hasErrors("updateMoment"))
			super.state(MomentHelper.isAfter(object.getUpdateMoment(), object.getCreationMoment()), "updateMoment", "developer.training-module.form.error.updateMoment");

	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset;
		SelectChoices choicesDifficulty;
		SelectChoices projectsChoices;
		Collection<Project> projects;

		choicesDifficulty = SelectChoices.from(Difficulty.class, object.getDifficultyLevel());
		projects = this.repository.findAllProjects();
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "creationMoment", "details", "updateMoment", "link", "draftMode", "developer", "totalEstimatedTime");
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		dataset.put("difficultyLevel", choicesDifficulty);
		dataset.put("numberOfTrainingSessions", (int) this.repository.findAllTrainingSessionsWithSameTrainingModuleId(object.getId()).stream().count());

		super.getResponse().addData(dataset);
	}

}
