
package acme.features.manager.project;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.projects.UserStory;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select p from Project p where p.manager.id = :id")
	Collection<Project> findProjectsByManagerId(int id);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select p from Project p where p.code = :code")
	Optional<Project> findOneProjectByCode(String code);

	@Query("select m from Manager m where m.id = :managerId")
	Manager findOneManagerById(int managerId);

	@Query("select pus.userStory from ProjectUserStory pus where pus.project.id = :projectId")
	Collection<UserStory> findAllUserStoriesByProjectId(int projectId);

	@Query("select us from UserStory us")
	Collection<UserStory> findAllUserStories();

	@Query("select pus from ProjectUserStory pus where pus.project.id = :projectId")
	Collection<ProjectUserStory> findProjectUserStoryByProjectId(int projectId);

	@Query("select us from UserStory us where us.manager.id =: managerId ")
	Collection<UserStory> findAllUserStoriesByManagerId(int managerId);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findActualSystemConfiguration();

}
