
package acme.features.auditor.auditRecord;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.AuditRecord;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordListService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	private AuditorAuditRecordRepository rp;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}
	@Override
	public void load() {
		int id;
		id = Integer.valueOf(super.getRequest().getData().values().stream().collect(Collectors.toList()).get(0).toString());
		Collection<AuditRecord> objects;
		objects = this.rp.findAllAuditRecordsByCodeAuditId(id);
		super.getBuffer().addData(objects);
	}
	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "codeAR", "score", "draftMode");
		dataset.put("codeAuditCode", object.getCodeAudit().getCode());

		super.getResponse().addData(dataset);
	}
}
