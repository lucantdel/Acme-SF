
package acme.features.any.codeAudit;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeAudits.CodeAudit;
import acme.entities.projects.Project;

@Repository
public interface AnyCodeAuditRepository extends AbstractRepository {

	@Query("select ac from CodeAudit ac where ac.published = true")
	Collection<CodeAudit> findAllCodeAuditPublished();

	@Query("SELECT a.score FROM AuditRecord a WHERE a.codeAudit = :ca AND a.published = true")
	List<String> getScoreOfAsociatedPublishedAuditRecords(CodeAudit ca);

	@Query("select c from CodeAudit c where c.id = :id")
	CodeAudit findCodeAuditById(int id);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();
}
