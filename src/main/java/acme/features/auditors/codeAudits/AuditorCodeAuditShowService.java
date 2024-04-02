
package acme.features.auditors.codeAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditShowService extends AbstractService<Auditor, CodeAudit> {

	@Autowired
	private AuditorCodeAuditRepository rp;


	@Override
	public void authorise() {
		boolean status;
		int id;
		CodeAudit codeAudit;

		id = super.getRequest().getData("id", int.class);
		codeAudit = this.rp.findCodeAuditById();
		status = codeAudit != null && !codeAudit.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<CodeAudit> objects;

		objects = this.rp.findCreatedCodeAudits();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "execution", "type", "correctiveActions", "optionalLink", "project");

		super.getResponse().addData(dataset);
	}
}
