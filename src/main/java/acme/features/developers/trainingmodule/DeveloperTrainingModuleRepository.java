
package acme.features.developers.trainingmodule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;

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
}
