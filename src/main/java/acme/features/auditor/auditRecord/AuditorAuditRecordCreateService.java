
package acme.features.auditor.auditRecord;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.AuditRecord;
import acme.entities.codeAudits.CodeAudit;
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
		int id;
		Auditor auditor;
		id = super.getRequest().getData("codeAuditId", int.class);
		auditor = this.repository.getAuditorbyCodeAuditId(id);
		boolean autorizacion = auditor.getUserAccount().getUsername().equals(super.getRequest().getPrincipal().getUsername());
		super.getResponse().setAuthorised(autorizacion);

	}
	@Override
	public void load() {
		AuditRecord object;
		Integer id;
		Auditor auditor;
		CodeAudit codeAudit;
		id = super.getRequest().getData("codeAuditId", int.class);
		object = new AuditRecord();
		codeAudit = this.repository.findCodeAuditById(id);
		auditor = this.repository.getAuditorbyCodeAuditId(id);
		object.setCodeAudit(codeAudit);
		object.setAuditor(auditor);

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
		Integer id;
		id = Integer.valueOf(super.getRequest().getData().values().stream().collect(Collectors.toList()).get(0).toString());
		ca = this.repository.findCodeAuditById(id);

		if (!super.getBuffer().getErrors().hasErrors("codeAR")) {
			AuditRecord existing;
			existing = this.rp.findOneAuditRecordByCode(object.getCodeAR());
			super.state(existing == null, "codeAR", "auditor.auditRecord.error.duplicated");
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

		if (object.getLink() != null)
			if (!super.getBuffer().getErrors().hasErrors("link"))
				if (object.getLink().length() > 255)
					super.state(object.getLink().length() <= 255, "link", "auditor.auditRecord.error.Link");

		if (object.getCodeAudit() != null)
			if (!super.getBuffer().getErrors().hasErrors("codeAuditCode"))
				if (object.getCodeAudit().isPublished())
					super.state(!object.getCodeAudit().isPublished(), "codeAuditCode", "auditor.auditRecord.error.CodeAudit");
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		this.repository.save(object);
	}
	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;
		int caID;
		caID = super.getRequest().getData("codeAuditId", int.class);

		Dataset dataset;

		dataset = super.unbind(object, "codeAR", "startDate", "finishDate", "score", "link", "published");

		dataset.put("codeAuditCode", object.getCodeAudit().getCode());

		super.getResponse().addGlobal("codeAuditId", caID);

		super.getResponse().addData(dataset);
	}

}
