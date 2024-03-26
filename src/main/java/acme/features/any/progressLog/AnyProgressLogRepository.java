
package acme.features.any.progressLog;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.progress_logs.ProgressLog;

@Repository
public interface AnyProgressLogRepository extends AbstractRepository {

	@Query("select p from ProgressLog p where p.id = :id")
	ProgressLog findOneProgressLogById(int id);

	@Query("select p from ProgressLog p")
	Collection<ProgressLog> findMany();

}
