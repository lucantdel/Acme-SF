
package acme.features.auditor.codeAudits;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeAudits.AuditRecord;
import acme.entities.codeAudits.CodeAudit;
import acme.entities.projects.Project;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("select c from CodeAudit c where c.id = :id")
	CodeAudit findCodeAuditById(int id);

	@Query("select c from CodeAudit c where c.auditor.id = :id")
	Collection<CodeAudit> findCodeAuditsByAuditorId(int id);

	@Query("select c from CodeAudit c")
	Collection<CodeAudit> findCreatedCodeAudits();

	@Query("select a from Auditor a where a.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("select a from AuditRecord a where a.codeAudit = :ca")
	List<AuditRecord> getAllAsociatedAuditRecords(CodeAudit ca);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select ac.auditor from CodeAudit ac where ac.id = :id")
	Auditor getAuditorbyCodeAuditId(int id);

	@Query("select c from CodeAudit c where c.code = :code")
	CodeAudit findCodeAuditByCode(String code);

	@Query("select ar.startDate from AuditRecord ar where ar.codeAudit = :ca")
	List<Date> getAllStartDates(CodeAudit ca);

}
