
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

	@Query("SELECT COUNT(ar) FROM AuditRecord ar GROUP BY ar.codeAudit")
	List<Integer> minimunNumberOfAuditRecords();

	@Query("SELECT COUNT(ar) FROM AuditRecord ar group by ar.codeAudit")
	List<Integer> numberOfAuditRecord();

	//	@Query("SELECT TIMESTAMPDIFF(ChronoUnit.HOURS, ar.startDate, ar.finishDate) FROM AuditRecord ar")
	//	List<Long> Periodlengthlist();
	//
	//	@Query("SELECT AVG(ar.finishDate - ar.startDate) FROM AuditRecord ar ")
	//	Double avegageTimeOfThePeriodlength();
	//
	//	@Query("SELECT MAX(ar.finishDate - ar.startDate) FROM AuditRecord ar ")
	//	Double minimunTimeOfThePeriodlength();
	//
	//	@Query("SELECT STDDEV(ar.finishDate - ar.startDate) FROM AuditRecord ar ")
	//	Double deviationTimeOfThePeriodlength();

}
