
package acme.features.auditor.codeAudits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditCreateService extends AbstractService<Auditor, CodeAudit> {

	@Autowired
	protected AuditorCodeAuditRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}
	@Override
	public void load() {
		CodeAudit object;

		object = new CodeAudit();

		Auditor auditor;
		auditor = this.repository.findOneAuditorById(super.getRequest().getPrincipal().getActiveRoleId());
		super.getBuffer().addData(object);
		object.setAuditor(auditor);
		object.setDraftMode(true);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		super.bind(object, "code", "execution", "type", "correctiveActions", "optionalLink", "project", "draftMode");

	}
	@Override
	public void validate(final CodeAudit object) {
		assert object != null;
	}
	@Override
	public void perform(final CodeAudit object) {
		assert object != null;
		this.repository.save(object);
	}
	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "execution", "type", "correctiveActions", "optionalLink", "project", "draftMode");
		super.getResponse().addData(dataset);
	}

}
