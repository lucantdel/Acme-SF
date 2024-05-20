
package acme.testing.developer.trainingModule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingModule.TrainingModule;

public interface DeveloperTrainingModuleRepositoryTest extends AbstractRepository {

	@Query("select tm from TrainingModule tm where tm.developer.userAccount.username = :developer")
	Collection<TrainingModule> findAllTrainingModuleByDeveloperuserName(String developer);
}
