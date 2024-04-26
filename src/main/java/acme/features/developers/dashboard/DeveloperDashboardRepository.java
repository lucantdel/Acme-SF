
package acme.features.developers.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperDashboardRepository extends AbstractRepository {

	@Query("select d from Developer d where d.userAccount.id = :id")
	Developer findDeveloperById(int id);

	@Query("select count(tm) from TrainingModule tm  where tm.updateMoment is not null and tm.developer.userAccount.id = :id")
	Integer totalNumberOfTrainingModulesWithUpdateMoment(int id);

	@Query("select count(ts) from TrainingSession ts where ts.link is not null and ts.trainingModule.developer.userAccount.id = :id")
	Integer totalNumberOfTrainingSessionsWithLink(int id);

	@Query("select avg(tm.totalEstimatedTime) from TrainingModule tm where tm.developer.userAccount.id = :id")
	Double averageTimeByTM(int id);

	@Query("select min(tm.totalEstimatedTime) from TrainingModule tm where tm.developer.userAccount.id = :id")
	Double minimumTimeByTM(int id);

	@Query("select max(tm.totalEstimatedTime) from TrainingModule tm where tm.developer.userAccount.id = :id")
	Double maximumTimeByTM(int id);

	@Query("SELECT SQRT((SUM(tm.totalEstimatedTime * tm.totalEstimatedTime) / COUNT(tm.totalEstimatedTime)) - (AVG(tm.totalEstimatedTime) * AVG(tm.totalEstimatedTime))) FROM TrainingModule tm where tm.developer.userAccount.id = :id")
	Double standardDeviationTimeByTM(int id);

	@Query("select tm from TrainingModule tm where tm.developer.userAccount.id = :id")
	Collection<TrainingModule> findAllTrainingModuleByDevId(int id);

	@Query("select ts from TrainingSession ts where ts.trainingModule.developer.userAccount.id = :id")
	Collection<TrainingSession> findAllTrainingSessionByDevId(int id);

}
