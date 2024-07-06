
package acme.features.auditor.dashboard;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeAudits.AuditRecord;
import acme.entities.codeAudits.CodeAudit;
import acme.roles.Auditor;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select a from Auditor a where a.userAccount.id = :id")
	Auditor findAuditorById(int id);

	@Query("SELECT COUNT(ca) FROM CodeAudit ca WHERE ca.type = acme.entities.codeAudits.CodeAuditsStatus.Dinamic AND ca.auditor.id = :auditorId AND ca.published = true")
	int totalNumberOfCodeAuditsDynamic(int auditorId);

	@Query("SELECT COUNT(ca) FROM CodeAudit ca WHERE ca.type = acme.entities.codeAudits.CodeAuditsStatus.Static AND ca.auditor.id = :auditorId AND ca.published = true")
	int totalNumberOfCodeAuditsStatic(int auditorId);

	@Query("SELECT COUNT(ar) FROM AuditRecord ar where ar.auditor.id = :auditorId AND ar.published = true group by ar.codeAudit ")
	List<Integer> numberOfAuditRecord(int auditorId);

	@Query("select max(a.finishDate - a.startDate) FROM AuditRecord a where a.auditor.id = :auditorId AND a.published = true")
	Long maxPeriod(int auditorId);

	@Query("select min(a.finishDate - a.startDate) FROM AuditRecord a where a.auditor.id = :auditorId AND a.published = true")
	Long minPeriod(int auditorId);
	@Query("select a.finishDate - a.startDate FROM AuditRecord a where a.auditor.id = :auditorId AND a.published = true")
	List<Long> Period(int auditorId);

	@Query("select avg(a.finishDate - a.startDate) FROM AuditRecord a where a.auditor.id = :auditorId AND a.published = true")
	Long avgPeriod(int auditorId);

	@Query("select stddev(a.finishDate - a.startDate) FROM AuditRecord a where a.auditor.id = :auditorId AND a.published = true")
	Long stddevPeriod(int auditorId);

	@Query("select ca from CodeAudit ca where ca.auditor.id = :id AND ca.published = true")
	Collection<CodeAudit> findAllCodeAuditsByAuditorId(int id);

	@Query("select a from AuditRecord a where a.auditor.id = :id AND a.published = true")
	Collection<AuditRecord> findAllAuditRecordsByAuditorId(int id);

}
