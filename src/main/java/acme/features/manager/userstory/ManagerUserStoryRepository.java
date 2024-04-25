
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("select us from UserStory us where us.id = :userStoryId")
	UserStory findOneUserStoryById(int userStoryId);

	@Query("select pus.userStory from ProjectUserStory pus where pus.project.id = :projectId")
	Collection<UserStory> findUserStoriesByProjectId(int projectId);

	@Query("select us from UserStory us where us.manager.id = :managerId")
	Collection<UserStory> findUserStoriesByManagerId(int managerId);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select m from Manager m where m.id = :managerId")
	Manager findOneManagerById(int managerId);

	@Query("select pus from ProjectUserStory pus where pus.userStory.id = :userStoryId")
	Collection<ProjectUserStory> findProjectUserStoryByUserStoryId(int userStoryId);

}
