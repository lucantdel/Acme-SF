
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("select pus.userStory from ProjectUserStory pus where pus.project.id = :id")
	Collection<UserStory> findUserStoriesByProjectId(int id);

	@Query("select us from UserStory us where us.id = :id")
	UserStory findOneUserStoryById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("SELECT m FROM Manager m WHERE m.id = :id")
	Manager findManagerById(int id);

}
