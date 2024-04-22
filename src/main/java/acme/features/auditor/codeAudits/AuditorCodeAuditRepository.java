
package acme.features.auditor.codeAudits;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeAudits.CodeAudit;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("select c from CodeAudit c where c.id = :id")
	CodeAudit findCodeAuditById(int id);

	@Query("select c from CodeAudit c where c.draftMode = false")
	Collection<CodeAudit> findCreatedCodeAudits();

}
