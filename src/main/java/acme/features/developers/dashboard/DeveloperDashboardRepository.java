
package acme.features.developers.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DeveloperDashboardRepository extends AbstractRepository {

	@Query("select count(tm) from TrainingModule tm  where tm.updateMoment is not null")
	Integer totalNumberOfTrainingModulesWithUpdateMoment();

	@Query("select count(ts) from TrainingSession ts where ts.link is not null")
	Integer totalNumberOfTrainingSessionsWithLink();

	//NO PUEDO ACCEDER A ESTOS DATOS EN LA BASE DE DATOS
	/*
	 * @Query("select avg(select count(a) from Application a where exists(select j from Job j where j.employer.id = e.id and a.job.id = j.id)) from Employer e")
	 * Double minimumTimeByTM();
	 * 
	 * @Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.ApplicationStatus.PENDING")
	 * Double maximumTimeByTM();
	 * 
	 * @Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.ApplicationStatus.ACCEPTED")
	 * Double standardDeviationTimeByTM();
	 */
}
