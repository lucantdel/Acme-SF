
package acme.features.any.auditRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeAudits.AuditRecord;
import acme.entities.codeAudits.CodeAudit;

@Repository
public interface AnyAuditRecordRepository extends AbstractRepository {

	@Query("select a from AuditRecord a where a.codeAudit.id = :id and a.published = true")
	Collection<AuditRecord> findAllAuditRecordsByCodeAuditId(int id);

	@Query("select a from AuditRecord a where a.id = :id")
	AuditRecord findAuditRecordById(int id);

	@Query("select c from CodeAudit c where c.id = :id")
	CodeAudit findCodeAuditById(int id);

}
