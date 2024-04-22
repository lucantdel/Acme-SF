
package acme.features.manager.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select count(us) from UserStory us where us.priority = acme.entities.projects.UserStoryPriority.MUST")
	int mustUserStories();

	@Query("select count(us) from UserStory us where us.priority = acme.entities.projects.UserStoryPriority.SHOULD")
	int shouldUserStories();

	@Query("select count(us) from UserStory us where us.priority = acme.entities.projects.UserStoryPriority.COULD")
	int couldUserStories();

	@Query("select count(us) from UserStory us where us.priority = acme.entities.projects.UserStoryPriority.WONT")
	int wontUserStories();

	@Query("select avg(us.estimatedCost) from UserStory us")
	double avgUserStoryEstimatedCost();

	@Query("select stddev(us.estimatedCost) from UserStory us")
	double devUserStoryEstimatedCost();

	@Query("select min(us.estimatedCost) from UserStory us")
	int minUserStoryEstimatedCost();

	@Query("select max(us.estimatedCost) from UserStory us")
	int maxUserStoryEstimatedCost();

	// TODO: HAY QUE CAMBIAR TODOS LOS DE ABAJO A TIPO Money

	@Query("select avg(p.cost.amount) from Project p")
	double avgProjectCost();

	@Query("select stddev(p.cost.amount) from Project p")
	double devProjectCost();

	@Query("select min(p.cost.amount) from Project p")
	double minProjectCost();

	@Query("select max(p.cost.amount) from Project p")
	double maxProjectCost();

}
