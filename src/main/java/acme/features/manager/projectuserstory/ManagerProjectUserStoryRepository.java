
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

	@Query("select pus from ProjectUserStory pus where pus.id = :projectUserStoryId")
	ProjectUserStory findOneProjectUserStoryById(int projectUserStoryId);

	@Query(" select pus from ProjectUserStory pus where pus.project.id = :projectId and pus.userStory.id = :userStoryId")
	ProjectUserStory findOneProjectUserStoryByProjectIdAndUserStoryId(int projectId, int userStoryId);

	@Query("select pus from ProjectUserStory pus where pus.project.manager.id = :managerId")
	Collection<ProjectUserStory> findProjectUserStoryByManagerId(int managerId);

	@Query("select m from Manager m where m.id = :managerId")
	Manager findOneManagerById(int managerId);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select p from Project p where p.manager.id = :managerId")
	Collection<Project> findProjectsByManagerId(int managerId);

	@Query("select p from Project p where p.manager.id = :managerId and p.draftMode = true")
	Collection<Project> findDraftModeProjectsByManagerId(int managerId);

	@Query("select us from UserStory us where us.id = :userStoryId")
	UserStory findOneUserStoryById(int userStoryId);

	@Query("select us from UserStory us")
	Collection<UserStory> findAllUserStories();

}
