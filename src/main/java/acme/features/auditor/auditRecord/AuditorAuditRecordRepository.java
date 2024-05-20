
package acme.features.auditor.auditRecord;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeAudits.AuditRecord;
import acme.entities.codeAudits.CodeAudit;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("select a from AuditRecord a where a.id = :id")
	AuditRecord findAuditRecordById(int id);

	@Query("select a from AuditRecord a where a.auditor.id = :id")
	Collection<AuditRecord> findAuditRecordsByAuditorId(int id);

	@Query("select a from AuditRecord a")
	Collection<AuditRecord> findAllAuditRecords();

	@Query("select a from AuditRecord a where a.codeAudit.id = :id")
	Collection<AuditRecord> findAllAuditRecordsByCodeAuditId(int id);

	@Query("SELECT a.score FROM AuditRecord a WHERE a.codeAudit = :ca AND a.published = true")
	List<String> getScoreOfAsociatedPublishedAuditRecords(CodeAudit ca);

	@Query("select a from AuditRecord a where a.codeAR = :code")
	AuditRecord findOneAuditRecordByCode(String code);
}
