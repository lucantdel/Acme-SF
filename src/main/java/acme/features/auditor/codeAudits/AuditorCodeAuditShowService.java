
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

		id = super.getRequest().getData("id", int.class);
		codeAudit = this.rp.findCodeAuditById(id);
		status = codeAudit != null;

		super.getResponse().setAuthorised(status);
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

		dataset = super.unbind(object, "code", "execution", "type", "correctiveActions", "optionalLink", "project", "draftMode", "auditor");
		String mark = object.Mark(this.repository.getScoreOfAsociatedAuditRecords(object));
		dataset.put("Mark", mark);
		super.getResponse().addData(dataset);
	}
}
