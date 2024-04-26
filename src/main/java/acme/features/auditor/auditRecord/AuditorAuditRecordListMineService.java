
package acme.features.auditor.auditRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.AuditRecord;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordListMineService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	private AuditorAuditRecordRepository rp;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}
	@Override
	public void load() {

		Collection<AuditRecord> objects;
		Principal principal;
		principal = super.getRequest().getPrincipal();

		objects = this.rp.findAuditRecordsByAuditorId(principal.getActiveRoleId());

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
