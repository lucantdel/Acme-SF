
package acme.features.client.progresslog;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.progress_logs.ProgressLogs;

@Repository
public interface ClientProgressLogRepository extends AbstractRepository {

	@Query("select pl from ProgressLogs pl where pl.contract.client.userAccount.id = :id")
	Collection<ProgressLogs> findProgressLogsByClientId(int id);

	@Query("select c from Contract c where c.code = :code")
	Contract findContractByCode(String code);

	@Query("select pl from ProgressLogs pl where pl.contract.id = :id")
	Collection<ProgressLogs> findProgressLogsByContract(int id);

	@Query("select pl from ProgressLogs pl where pl.recordId = :id")
	ProgressLogs findProgressLogsByRecordId(String id);

	@Query("select pl from ProgressLogs pl where pl.id = :id")
	ProgressLogs findProgressLogsById(int id);

	@Query("select c from Contract c where c.id = :id")
	Contract findContractById(int id);

	@Query("select c from Contract c where c.client.id = :id")
	Collection<Contract> findContractsByClient(int id);
}
