
package acme.features.manager.projectuserstory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerProjectUserStoryRepository extends AbstractRepository {

	@Query("select pus from ProjectUserStory pus where pus.id = :id")
	ProjectUserStory findOneProjectUserStoryById(int id);

	@Query("select pus from ProjectUserStory pus where pus.project.manager.id = :id and pus.userStory.manager.id = :id")
	Collection<ProjectUserStory> findProjectUserStoryByManagerId(int id);

	@Query("select m from Manager m where m.id = :id")
	Manager findOneManagerById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select p from Project p where p.manager.id = :id")
	Collection<Project> findProjectsByManagerId(int id);

	@Query("select us from UserStory us where us.id = :id")
	UserStory findOneUserStoryById(int id);

	@Query("select us from UserStory us where us.manager.id = :id")
	Collection<UserStory> findUserStoriesByManagerId(int id);

}
