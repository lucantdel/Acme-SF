
package acme.features.manager.projectuserstory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.ProjectUserStory;

@Repository
public interface ManagerProjectUserStoryRepository extends AbstractRepository {

	@Query("select pus from ProjectUserStory pus where pus.id = :id")
	ProjectUserStory findOneProjectUserStoryById(int id);

	@Query("select pus from ProjectUserStory pus where pus.project.manager.id = :id and pus.userStory.manager.id = :id")
	Collection<ProjectUserStory> findProjectUserStoryByManagerId(int id);

}
