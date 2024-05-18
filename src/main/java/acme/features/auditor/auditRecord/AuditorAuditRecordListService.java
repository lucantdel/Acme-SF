
package acme.features.auditor.auditRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.AuditRecord;
import acme.entities.codeAudits.CodeAudit;
import acme.features.auditor.codeAudits.AuditorCodeAuditRepository;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordListService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	private AuditorAuditRecordRepository	rp;

	@Autowired
	private AuditorCodeAuditRepository		repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		CodeAudit codeAudit;
		Auditor auditor;
		masterId = super.getRequest().getData("codeAuditId", int.class);
		codeAudit = this.repository.findCodeAuditById(masterId);

		auditor = codeAudit == null ? null : codeAudit.getAuditor();
		status = super.getRequest().getPrincipal().hasRole(auditor);

		boolean autorizacion = auditor.getUserAccount().getUsername().equals(super.getRequest().getPrincipal().getUsername());

		super.getResponse().setAuthorised(status && autorizacion);

	}
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("codeAuditId", int.class);
		Collection<AuditRecord> objects;
		objects = this.rp.findAllAuditRecordsByCodeAuditId(id);

		super.getBuffer().addData(objects);
	}
	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;
		String payload;

		dataset = super.unbind(object, "codeAR", "score", "draftMode");
		dataset.put("codeAudit", object.getCodeAudit().getCode());

		payload = String.format(//
			"%s;    %s;    %s;    %s",//
			object.getStartDate(),//
			object.getFinishDate(),//
			object.getLink(),//
			object.getAuditor().getUserAccount().getUsername());//
		dataset.put("payload", payload);

		super.getResponse().addData(dataset);
	}
}
