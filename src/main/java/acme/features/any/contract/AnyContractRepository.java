
package acme.features.any.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.progress_logs.ProgressLogs;

public interface AnyContractRepository extends AbstractRepository {

	@Query("select c from Contract c where c.draftMode = false")
	Collection<Contract> findPublishedContracts();

	@Query("select c from Contract c where c.id = :id")
	Contract findContractById(int id);

	@Query("select pl from ProgressLogs pl where pl.contract.id = :id")
	Collection<ProgressLogs> findProgressLogsByContract(int id);
}
