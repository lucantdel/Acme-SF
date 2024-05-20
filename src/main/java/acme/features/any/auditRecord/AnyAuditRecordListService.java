
package acme.features.any.auditRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.AuditRecord;

@Service
public class AnyAuditRecordListService extends AbstractService<Any, AuditRecord> {

	@Autowired
	private AnyAuditRecordRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("codeAuditId", int.class);
		Collection<AuditRecord> objects;
		objects = this.repository.findAllAuditRecordsByCodeAuditId(id);

		super.getBuffer().addData(objects);
	}
	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;
		String payload;

		dataset = super.unbind(object, "codeAR", "score", "published");
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
