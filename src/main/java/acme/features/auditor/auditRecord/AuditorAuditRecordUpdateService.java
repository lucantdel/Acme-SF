
package acme.features.auditor.auditRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.AuditRecord;
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

		super.bind(object, "codeAR", "startDate", "finishDate", "score", "link", "draftMode");
	}
	@Override
	public void validate(final AuditRecord object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.isDraftMode() == true, "draftMode", "auditor.auditRecord.error.draftMode");

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
		System.out.println(object.getCodeAudit().getCode() + "update");

		dataset = super.unbind(object, "codeAR", "startDate", "finishDate", "score", "link", "draftMode", "auditor");

		super.getResponse().addData(dataset);
	}

}
