
package acme.features.manager.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select p.cost.currency, avg(p.cost.amount) from Project p where p.manager.id = :managerId and p.draftMode = false group by p.cost.currency")
	Collection<Object[]> avgProjectCost(int managerId);

	@Query("select p.cost.currency, stddev(p.cost.amount) from Project p where p.manager.id = :managerId and p.draftMode = false group by p.cost.currency")
	Collection<Object[]> devProjectCost(int managerId);

	@Query("select p.cost.currency, min(p.cost.amount) from Project p where p.manager.id = :managerId and p.draftMode = false group by p.cost.currency")
	Collection<Object[]> minProjectCost(int managerId);

	@Query("select p.cost.currency, max(p.cost.amount) from Project p where p.manager.id = :managerId and p.draftMode = false group by p.cost.currency")
	Collection<Object[]> maxProjectCost(int managerId);

	@Query("select count(us) from UserStory us where us.manager.id = :managerId and us.draftMode = false and us.priority = acme.entities.projects.UserStoryPriority.MUST")
	int mustUserStories(int managerId);

	@Query("select count(us) from UserStory us where us.manager.id = :managerId and us.draftMode = false and us.priority = acme.entities.projects.UserStoryPriority.SHOULD")
	int shouldUserStories(int managerId);

	@Query("select count(us) from UserStory us where us.manager.id = :managerId and us.draftMode = false and us.priority = acme.entities.projects.UserStoryPriority.COULD")
	int couldUserStories(int managerId);

	@Query("select count(us) from UserStory us where us.manager.id = :managerId and us.draftMode = false and us.priority = acme.entities.projects.UserStoryPriority.WONT")
	int wontUserStories(int managerId);

	@Query("select avg(us.estimatedCost) from UserStory us where us.manager.id = :managerId and us.draftMode = false")
	double avgUserStoryEstimatedCost(int managerId);

	@Query("select stddev(us.estimatedCost) from UserStory us where us.manager.id = :managerId and us.draftMode = false")
	double devUserStoryEstimatedCost(int managerId);

	@Query("select min(us.estimatedCost) from UserStory us where us.manager.id = :managerId and us.draftMode = false")
	double minUserStoryEstimatedCost(int managerId);

	@Query("select max(us.estimatedCost) from UserStory us where us.manager.id = :managerId and us.draftMode = false")
	double maxUserStoryEstimatedCost(int managerId);

	@Query("select p from Project p where p.manager.id = :managerId and p.draftMode = false")
	Collection<Project> findAllPublishedProjectsByManagerId(int managerId);

	@Query("select us from UserStory us where us.manager.id = :managerId and us.draftMode = false")
	Collection<UserStory> findAllPublishedUserStoriesByManagerId(int managerId);

}
