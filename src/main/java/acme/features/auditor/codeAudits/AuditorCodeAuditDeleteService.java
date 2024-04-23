
package acme.features.auditor.codeAudits;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.AuditRecord;
import acme.entities.codeAudits.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditDeleteService extends AbstractService<Auditor, CodeAudit> {

	@Autowired
	protected AuditorCodeAuditRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}
	@Override
	public void load() {
		CodeAudit object;

		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCodeAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		super.bind(object, "code", "execution", "type", "correctiveActions", "optionalLink", "project", "draftMode");

	}
	@Override
	public void validate(final CodeAudit object) {
		assert object != null;
	}
	@Override
	public void perform(final CodeAudit object) {
		assert object != null;
		List<AuditRecord> auditRecords;
		auditRecords = this.repository.getAllAsociatedAuditRecords(object);
		this.repository.deleteAll(auditRecords);
		this.repository.delete(object);
	}
	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "execution", "type", "correctiveActions", "optionalLink", "project", "draftMode");
		super.getResponse().addData(dataset);
	}

}
