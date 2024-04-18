
package acme.features.developers.trainingmodule;

import java.util.Calendar;
import java.util.Collection;
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
public class DeveloperTrainingModuleShowService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

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
		//solo contaremos las horas entre la 8:00-18:00 y no contaremos sabados y domingos.
		for (TrainingSession ts : objectsTS) {
			Date start = ts.getStartPeriod();
			Date end = ts.getEndPeriod();

			Calendar calendarStart = Calendar.getInstance();
			calendarStart.setTime(start);
			//int startHour = calendarStart.get(Calendar.HOUR_OF_DAY);
			int startDayOfWeek = calendarStart.get(Calendar.DAY_OF_WEEK);

			Calendar calendarEnd = Calendar.getInstance();
			calendarEnd.setTime(end);
			//int endHour = calendarEnd.get(Calendar.HOUR_OF_DAY);
			int endDayOfWeek = calendarEnd.get(Calendar.DAY_OF_WEEK);

			if (MomentHelper.isBefore(start, end))
				while (calendarStart.before(calendarEnd)) {
					if (!(startDayOfWeek == Calendar.SATURDAY && endDayOfWeek == Calendar.SATURDAY))
						//ya que la jornada va de 8:00-17:00 ( un total de 9 horas);
						totaltime += 9;
					calendarStart.roll(Calendar.DAY_OF_YEAR, true);
				}
			/*
			 * int i = startHour;
			 * int j = endHour;
			 * while (i < j) {
			 * boolean cond1 = 8 <= i && i < 17;
			 * boolean cond2 = j <= 17;
			 * if (cond1 && cond2)
			 * totaltime++;
			 * else if (!cond1) {
			 * if (i < 8)
			 * i++;
			 * if (i >= 17)
			 * i--;
			 * } else if (!cond2)
			 * j--;
			 * }
			 */

		}

		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "draftMode", "developer");
		dataset.put("totalTime", totaltime);
		dataset.put("project", object.getProject().getCode());

		super.getResponse().addData(dataset);
	}
}
