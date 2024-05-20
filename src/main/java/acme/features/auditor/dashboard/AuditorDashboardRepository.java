
package acme.features.auditor.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(ca) FROM CodeAudit ca WHERE ca.type = acme.entities.codeAudits.CodeAuditsStatus.Dinamic and ca.auditor.id = :auditorId")
	int totalNumberOfCodeAuditsDynamic(int auditorId);

	@Query("SELECT COUNT(ca) FROM CodeAudit ca WHERE ca.type = acme.entities.codeAudits.CodeAuditsStatus.Static and ca.auditor.id = :auditorId")
	int totalNumberOfCodeAuditsStatic(int auditorId);

	@Query("SELECT COUNT(ar) FROM AuditRecord ar where ar.auditor.id = :auditorId group by ar.codeAudit ")
	List<Integer> numberOfAuditRecord(int auditorId);

	@Query("select max(a.finishDate - a.startDate) FROM AuditRecord a where a.auditor.id = :auditorId")
	Long maxPeriod(int auditorId);

	@Query("select min(a.finishDate - a.startDate) FROM AuditRecord a where a.auditor.id = :auditorId")
	Long minPeriod(int auditorId);
	@Query("select a.finishDate - a.startDate FROM AuditRecord a where a.auditor.id = :auditorId")
	List<Long> Period(int auditorId);

	@Query("select avg(a.finishDate - a.startDate) FROM AuditRecord a where a.auditor.id = :auditorId")
	Long avgPeriod(int auditorId);

	@Query("select stddev(a.finishDate - a.startDate) FROM AuditRecord a where a.auditor.id = :auditorId")
	Long stddevPeriod(int auditorId);

}
