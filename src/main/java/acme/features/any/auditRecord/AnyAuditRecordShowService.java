
package acme.features.any.auditRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.AuditRecord;

@Service
public class AnyAuditRecordShowService extends AbstractService<Any, AuditRecord> {

	@Autowired
	private AnyAuditRecordRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
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
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "codeAR", "startDate", "finishDate", "score", "link", "published", "auditor");
		dataset.put("codeAuditCode", object.getCodeAudit().getCode());

		super.getResponse().addData(dataset);

	}
}
