
package acme.features.auditor.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(ca) FROM CodeAudit ca WHERE ca.type = acme.entities.codeAudits.CodeAuditsStatus.Dinamic")
	int totalNumberOfCodeAuditsDynamic();

	@Query("SELECT COUNT(ca) FROM CodeAudit ca WHERE ca.type = acme.entities.codeAudits.CodeAuditsStatus.Static")
	int totalNumberOfCodeAuditsStatic();

	@Query("SELECT COUNT(ar) FROM AuditRecord ar group by ar.codeAudit")
	List<Integer> numberOfAuditRecord();

	@Query("select max(a.finishDate - a.startDate) FROM AuditRecord a")
	Long maxPeriod();

	@Query("select min(a.finishDate - a.startDate) FROM AuditRecord a")
	Long minPeriod();
	@Query("select a.finishDate - a.startDate FROM AuditRecord a")
	List<Long> Period();

	@Query("select avg(a.finishDate - a.startDate) FROM AuditRecord a")
	Long avgPeriod();

	@Query("select stddev(a.finishDate - a.startDate) FROM AuditRecord a")
	Long stddevPeriod();

}
