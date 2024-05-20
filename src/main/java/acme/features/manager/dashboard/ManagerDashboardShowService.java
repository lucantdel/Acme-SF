
package acme.features.manager.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;
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
		int managerId;

		Collection<Project> projects;
		Collection<UserStory> userStories;

		ManagerDashboard dashboard;

		int mustUserStories;
		int shouldUserStories;
		int couldUserStories;
		int wontUserStories;

		double avgUserStoryEstimatedCost;
		double devUserStoryEstimatedCost;
		double minUserStoryEstimatedCost;
		double maxUserStoryEstimatedCost;

		Map<String, Double> avgProjectCost;
		Map<String, Double> devProjectCost;
		Map<String, Double> minProjectCost;
		Map<String, Double> maxProjectCost;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		projects = this.repository.findAllPublishedProjectsByManagerId(managerId);
		userStories = this.repository.findAllPublishedUserStoriesByManagerId(managerId);

		avgProjectCost = new HashMap<>();
		devProjectCost = new HashMap<>();
		minProjectCost = new HashMap<>();
		maxProjectCost = new HashMap<>();

		if (!projects.isEmpty()) {
			avgProjectCost = this.convertToMap(this.repository.avgProjectCost(managerId));
			devProjectCost = this.convertToMap(this.repository.devProjectCost(managerId));
			minProjectCost = this.convertToMap(this.repository.minProjectCost(managerId));
			maxProjectCost = this.convertToMap(this.repository.maxProjectCost(managerId));
		}

		mustUserStories = 0;
		shouldUserStories = 0;
		couldUserStories = 0;
		wontUserStories = 0;

		avgUserStoryEstimatedCost = Double.NaN;
		devUserStoryEstimatedCost = Double.NaN;
		minUserStoryEstimatedCost = Double.NaN;
		maxUserStoryEstimatedCost = Double.NaN;

		if (!userStories.isEmpty()) {
			mustUserStories = this.repository.mustUserStories(managerId);
			shouldUserStories = this.repository.shouldUserStories(managerId);
			couldUserStories = this.repository.couldUserStories(managerId);
			wontUserStories = this.repository.wontUserStories(managerId);
			avgUserStoryEstimatedCost = this.repository.avgUserStoryEstimatedCost(managerId);
			devUserStoryEstimatedCost = this.repository.devUserStoryEstimatedCost(managerId);
			minUserStoryEstimatedCost = this.repository.minUserStoryEstimatedCost(managerId);
			maxUserStoryEstimatedCost = this.repository.maxUserStoryEstimatedCost(managerId);
		}

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

	// Auxiliary methods

	private Map<String, Double> convertToMap(final Collection<Object[]> objectSet) {
		Map<String, Double> res = new HashMap<>();
		for (Object[] keyValuePair : objectSet) {
			String currency = (String) keyValuePair[0];
			Double statistic = (Double) keyValuePair[1];
			res.put(currency, statistic);
		}
		return res;
	}
}
