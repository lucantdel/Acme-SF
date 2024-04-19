
package acme.features.developers.trainingmodule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingModuleRepository extends AbstractRepository {

	@Query("select tm from TrainingModule tm where tm.id = :id")
	TrainingModule findOneTrainingModuleById(int id);

	@Query("select tm from TrainingModule tm")
	Collection<TrainingModule> findAllTrainingModule();

	@Query("select tm from TrainingModule tm where tm.developer.id = :id")
	Collection<TrainingModule> findAllTrainingModuleByDeveloperId(int id);

	@Query("select ts from TrainingSession ts where ts.trainingModule.id = :id")
	Collection<TrainingSession> findAllTrainingSessionsWithSameTrainingModuleId(int id);

	@Query("select d from Developer d where d.id = :id")
	Developer findOneDeveloperById(int id);

	@Query("select tm from TrainingModule tm where tm.code = :code")
	TrainingModule findOneTrainingModuleByReference(String code);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();
}
