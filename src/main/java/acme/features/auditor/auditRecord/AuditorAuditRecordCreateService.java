
package acme.features.auditor.auditRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.AuditRecord;
import acme.features.auditor.codeAudits.AuditorCodeAuditRepository;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordCreateService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	protected AuditorAuditRecordRepository	rp;

	@Autowired
	protected AuditorCodeAuditRepository	repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}
	@Override
	public void load() {
		AuditRecord object;

		object = new AuditRecord();

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
		if (!super.getBuffer().getErrors().hasErrors("finishDate"))
			super.state(object.validatePeriod() == true, "finishDate", "auditor.auditRecord.error.period");
	}
	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		this.repository.save(object);
	}
	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startDate", "finishDate", "score", "optionalLink", "draftMode", "codeAudit", "auditor");

		super.getResponse().addData(dataset);
	}

}
