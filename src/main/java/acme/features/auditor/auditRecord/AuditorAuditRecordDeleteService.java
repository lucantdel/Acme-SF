
package acme.features.auditor.auditRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.AuditRecord;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordDeleteService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	private AuditorAuditRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		AuditRecord ra;
		Auditor auditor;
		masterId = super.getRequest().getData("id", int.class);

		ra = this.repository.findAuditRecordById(masterId);
		auditor = ra == null ? null : ra.getAuditor();
		status = ra != null && ra.isDraftMode() && super.getRequest().getPrincipal().hasRole(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditRecordById(id);

		super.getBuffer().addData(object);
	}
	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		super.bind(object, "code", "startDate", "finishDate", "score", "optionalLink", "draftMode", "codeAudit", "auditor");
	}
	@Override
	public void validate(final AuditRecord object) {
		assert object != null;
	}
	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		this.repository.delete(object);

	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startDate", "finishDate", "score", "optionalLink", "draftMode", "codeAudit", "auditor");

		super.getResponse().addData(dataset);
	}

}
