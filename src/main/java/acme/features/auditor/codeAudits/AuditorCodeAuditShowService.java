
package acme.features.auditor.codeAudits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.CodeAudit;
import acme.features.auditor.auditRecord.AuditorAuditRecordRepository;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditShowService extends AbstractService<Auditor, CodeAudit> {

	@Autowired
	private AuditorCodeAuditRepository		rp;

	@Autowired
	private AuditorAuditRecordRepository	repository;


	@Override
	public void authorise() {
		boolean status;
		int id;
		CodeAudit codeAudit;
		Auditor auditor;

		id = super.getRequest().getData("id", int.class);
		codeAudit = this.rp.findCodeAuditById(id);
		auditor = codeAudit == null ? null : codeAudit.getAuditor();

		status = codeAudit != null;
		boolean autorizacion = auditor.getUserAccount().getUsername().equals(super.getRequest().getPrincipal().getUsername());

		super.getResponse().setAuthorised(autorizacion && status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.rp.findCodeAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "execution", "type", "correctiveActions", "optionalLink", "draftMode", "auditor");
		String mark = object.Mark(this.repository.getScoreOfAsociatedPublishedAuditRecords(object));
		dataset.put("Mark", mark);
		dataset.put("project", object.getProject().getCode());

		super.getResponse().addData(dataset);
	}
}
