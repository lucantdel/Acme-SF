
package acme.features.auditor.auditRecords;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeAudits.AuditRecord;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("select a from AuditRecord a where a.id = :id")
	AuditRecord findAuditRecordById(int id);

	@Query("select a from AuditRecord a where a.draftMode = false")
	Collection<AuditRecord> findPublishedAuditRecords();

}
