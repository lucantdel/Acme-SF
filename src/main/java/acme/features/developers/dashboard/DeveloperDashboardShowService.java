
package acme.features.developers.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
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
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		DeveloperDashboard dashboard;
		Integer totalNumberOfTrainingModulesWithUpdateMoment;
		Integer totalNumberOfTrainingSessionsWithLink;

		/*
		 * Double minimumTimeByTM;
		 * Double maximumTimeByTM;
		 * Double standardDeviationTimeByTM;
		 */

		totalNumberOfTrainingModulesWithUpdateMoment = this.repository.totalNumberOfTrainingModulesWithUpdateMoment();
		totalNumberOfTrainingSessionsWithLink = this.repository.totalNumberOfTrainingSessionsWithLink();
		/*
		 * minimumTimeByTM = this.repository.minimumTimeByTM();
		 * maximumTimeByTM = this.repository.maximumTimeByTM();
		 * standardDeviationTimeByTM = this.repository.standardDeviationTimeByTM();
		 */

		dashboard = new DeveloperDashboard();
		dashboard.setTotalNumberOfTrainingModulesWithUpdateMoment(totalNumberOfTrainingModulesWithUpdateMoment);
		dashboard.setTotalNumberOfTrainingSessionsWithLink(totalNumberOfTrainingSessionsWithLink);
		/*
		 * dashboard.setMinimumTimeByTM(minimumTimeByTM);
		 * dashboard.setMaximumTimeByTM(maximumTimeByTM);
		 * dashboard.setStandardDeviationTimeByTM(standardDeviationTimeByTM);
		 */

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumberOfTrainingModulesWithUpdateMoment", "totalNumberOfTrainingSessionsWithLink" // 
		/* "minimumTimeByTM", "maximumTimeByTM", "standardDeviationTimeByTM" */);

		super.getResponse().addData(dataset);
	}
}
