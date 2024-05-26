
package acme.features.auditor.auditRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.AuditRecord;
import acme.entities.codeAudits.CodeAudit;
import acme.features.auditor.codeAudits.AuditorCodeAuditRepository;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordUpdateService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	protected AuditorAuditRecordRepository	repository;

	@Autowired
	protected AuditorCodeAuditRepository	rp;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		AuditRecord ra;
		Auditor auditor;
		masterId = super.getRequest().getData("id", int.class);

		ra = this.repository.findAuditRecordById(masterId);
		auditor = ra == null ? null : ra.getAuditor();
		status = ra != null && super.getRequest().getPrincipal().hasRole(auditor);

		boolean autorizacion = auditor.getUserAccount().getUsername().equals(super.getRequest().getPrincipal().getUsername());

		super.getResponse().setAuthorised(status && autorizacion);
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

		super.bind(object, "codeAR", "startDate", "finishDate", "score", "link", "published");
	}
	@Override
	public void validate(final AuditRecord object) {
		assert object != null;
		CodeAudit ca;
		ca = this.rp.findCodeAuditByCode(object.getCodeAudit().getCode());
		if (object.getLink() != null)
			if (!super.getBuffer().getErrors().hasErrors("link"))
				if (object.getLink().length() > 255)
					super.state(object.getLink().length() <= 255, "link", "auditor.auditRecord.error.Link");

		if (!super.getBuffer().getErrors().hasErrors("published"))
			super.state(object.isPublished() == false, "published", "auditor.auditRecord.error.published");

		if (!super.getBuffer().getErrors().hasErrors("codeAR")) {
			AuditRecord existing;
			existing = this.repository.findOneAuditRecordByCode(object.getCodeAR());
			super.state(existing == null || existing.equals(object), "codeAR", "auditor.auditRecord.error.duplicated");
		}
		if (object.getStartDate() != null && object.getFinishDate() != null) {
			if (!super.getBuffer().getErrors().hasErrors("finishDate"))
				super.state(object.validatePeriod() == true, "finishDate", "auditor.auditRecord.error.period");

			if (!super.getBuffer().getErrors().hasErrors("finishDate"))
				super.state(object.getFinishDate().after(object.getStartDate()), "finishDate", "auditor.auditRecord.error.period2");

			if (!super.getBuffer().getErrors().hasErrors("startDate"))
				super.state(object.getStartDate().before(object.getFinishDate()), "startDate", "auditor.auditRecord.error.period3");
			if (!super.getBuffer().getErrors().hasErrors("startDate"))
				super.state(ca.getExecution().before(object.getStartDate()), "startDate", "auditor.auditRecord.error.execution");
		}
		if (object.getStartDate() == null)
			if (!super.getBuffer().getErrors().hasErrors("finishDate"))
				super.state(object.getStartDate() != null, "finishDate", "auditor.auditRecord.error.periodN");
		if (object.getFinishDate() == null)
			if (!super.getBuffer().getErrors().hasErrors("startDate"))
				super.state(object.getFinishDate() != null, "startDate", "auditor.auditRecord.error.periodN");
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
		dataset = super.unbind(object, "codeAR", "startDate", "finishDate", "score", "link", "published", "auditor");
		dataset.put("codeAudit", object.getCodeAudit().getCode());

		super.getResponse().addData(dataset);
	}

}
