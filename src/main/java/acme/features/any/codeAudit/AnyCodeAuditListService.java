
package acme.features.any.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.CodeAudit;

@Service
public class AnyCodeAuditListService extends AbstractService<Any, CodeAudit> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudit> objects;

		objects = this.repository.findAllCodeAuditPublished();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;
		String payload;

		dataset = super.unbind(object, "code", "execution", "type", "auditor");
		String mark = object.Mark(this.repository.getScoreOfAsociatedPublishedAuditRecords(object));
		dataset.put("Mark", mark);
		dataset.put("project", object.getProject().getCode());
		payload = String.format(//
			"%s    %s    %s",//
			object.getCorrectiveActions(),//
			object.getOptionalLink(),//
			object.getAuditor().getUserAccount().getUsername());//
		dataset.put("payload", payload);
		super.getResponse().addData(dataset);
	}
}
