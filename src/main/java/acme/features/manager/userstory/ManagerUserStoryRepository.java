
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.UserStory;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("select pus.userStory from ProjectUserStory pus where pus.project.id = :id")
	Collection<UserStory> findUserStoriesByProjectId(int id);

	@Query("select us from UserStory us where us.id = :id")
	UserStory findOneUserStoryById(int id);

}
