
package acme.features.manager.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		ManagerDashboard dashboard;
		int mustUserStories;
		int shouldUserStories;
		int couldUserStories;
		int wontUserStories;
		double avgUserStoryEstimatedCost;
		double devUserStoryEstimatedCost;
		int minUserStoryEstimatedCost;
		int maxUserStoryEstimatedCost;
		// TODO: HAY QUE CAMBIAR TODOS LOS DE ABAJO A TIPO Money
		double avgProjectCost;
		double devProjectCost;
		double minProjectCost;
		double maxProjectCost;

		mustUserStories = this.repository.mustUserStories();
		shouldUserStories = this.repository.shouldUserStories();
		couldUserStories = this.repository.couldUserStories();
		wontUserStories = this.repository.wontUserStories();
		avgUserStoryEstimatedCost = this.repository.avgUserStoryEstimatedCost();
		devUserStoryEstimatedCost = this.repository.devUserStoryEstimatedCost();
		minUserStoryEstimatedCost = this.repository.minUserStoryEstimatedCost();
		maxUserStoryEstimatedCost = this.repository.maxUserStoryEstimatedCost();
		avgProjectCost = this.repository.avgProjectCost();
		devProjectCost = this.repository.devProjectCost();
		minProjectCost = this.repository.minProjectCost();
		maxProjectCost = this.repository.maxProjectCost();

		dashboard = new ManagerDashboard();
		dashboard.setMustUserStories(mustUserStories);
		dashboard.setShouldUserStories(shouldUserStories);
		dashboard.setCouldUserStories(couldUserStories);
		dashboard.setWontUserStories(wontUserStories);
		dashboard.setAvgUserStoryEstimatedCost(avgUserStoryEstimatedCost);
		dashboard.setDevUserStoryEstimatedCost(devUserStoryEstimatedCost);
		dashboard.setMinUserStoryEstimatedCost(minUserStoryEstimatedCost);
		dashboard.setMaxUserStoryEstimatedCost(maxUserStoryEstimatedCost);
		dashboard.setAvgProjectCost(avgProjectCost);
		dashboard.setDevProjectCost(devProjectCost);
		dashboard.setMinProjectCost(minProjectCost);
		dashboard.setMaxProjectCost(maxProjectCost);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"mustUserStories", "shouldUserStories", "couldUserStories", "wontUserStories",  // 
			"avgUserStoryEstimatedCost", "devUserStoryEstimatedCost", "minUserStoryEstimatedCost", "maxUserStoryEstimatedCost", //
			"avgProjectCost", "devProjectCost", "minProjectCost", "maxProjectCost");

		super.getResponse().addData(dataset);

	}
}
