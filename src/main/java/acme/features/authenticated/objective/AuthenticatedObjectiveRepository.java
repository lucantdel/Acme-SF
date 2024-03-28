
package acme.features.authenticated.objective;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.objectives.Objective;

@Repository
public interface AuthenticatedObjectiveRepository extends AbstractRepository {

	@Query("select o from Objective o where o.id = :id")
	Objective findOneObjectiveById(int id);

	@Query("select o from Objective o where o.instantiationMoment >= :deadline")
	Collection<Objective> findRecentObjectives(Date deadline);

	@Query("select o from Objective o")
	Collection<Objective> findAllObjectives();

}
