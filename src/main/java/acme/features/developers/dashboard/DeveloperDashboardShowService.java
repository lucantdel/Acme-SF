
package acme.features.developers.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Service
public class DeveloperDashboardShowService extends AbstractService<Developer, DeveloperDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;

		id = super.getRequest().getPrincipal().getAccountId();
		Developer developer = this.repository.findDeveloperById(id);
		status = developer != null && super.getRequest().getPrincipal().hasRole(Developer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		DeveloperDashboard dashboard;
		Integer totalNumberOfTrainingModulesWithUpdateMoment;
		Integer totalNumberOfTrainingSessionsWithLink;
		Double averageTimeByTM;
		Double minimumTimeByTM;
		Double maximumTimeByTM;
		Double standardDeviationTimeByTM;
		int id;
		Collection<TrainingModule> modules;
		Collection<TrainingSession> sessions;
		id = super.getRequest().getPrincipal().getAccountId();
		modules = this.repository.findAllPublishedTrainingModuleByDevId(id);
		sessions = this.repository.findAllPublishedTrainingSessionByDevId(id);

		totalNumberOfTrainingModulesWithUpdateMoment = 0;
		totalNumberOfTrainingSessionsWithLink = 0;
		averageTimeByTM = 0.0;
		minimumTimeByTM = 0.0;
		maximumTimeByTM = 0.0;
		standardDeviationTimeByTM = 0.0;

		if (!sessions.isEmpty())
			totalNumberOfTrainingSessionsWithLink = this.repository.totalNumberOfTrainingSessionsWithLink(id);

		if (!modules.isEmpty()) {
			totalNumberOfTrainingModulesWithUpdateMoment = this.repository.totalNumberOfTrainingModulesWithUpdateMoment(id);

			averageTimeByTM = this.repository.averageTimeByTM(id);
			minimumTimeByTM = this.repository.minimumTimeByTM(id);
			maximumTimeByTM = this.repository.maximumTimeByTM(id);
			standardDeviationTimeByTM = this.repository.standardDeviationTimeByTM(id);

		}

		dashboard = new DeveloperDashboard();

		dashboard.setTotalNumberOfTrainingModulesWithUpdateMoment(totalNumberOfTrainingModulesWithUpdateMoment);
		dashboard.setTotalNumberOfTrainingSessionsWithLink(totalNumberOfTrainingSessionsWithLink);
		/*
		 * dashboard.setAverageTimeByTM(0.0);
		 * dashboard.setStandardDeviationTimeByTM(0.0);
		 * dashboard.setMaximumTimeByTM(0.0);
		 * dashboard.setMinimumTimeByTM(0.0);
		 */

		dashboard.setAverageTimeByTM(averageTimeByTM);
		dashboard.setStandardDeviationTimeByTM(standardDeviationTimeByTM);
		dashboard.setMaximumTimeByTM(maximumTimeByTM);
		dashboard.setMinimumTimeByTM(minimumTimeByTM);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalNumberOfTrainingModulesWithUpdateMoment", "totalNumberOfTrainingSessionsWithLink", "averageTimeByTM", "minimumTimeByTM", "maximumTimeByTM", "standardDeviationTimeByTM");

		super.getResponse().addData(dataset);
	}
}
