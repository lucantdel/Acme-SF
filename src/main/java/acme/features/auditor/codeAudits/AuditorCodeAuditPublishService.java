
package acme.features.auditor.codeAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.codeAudits.CodeAudit;
import acme.entities.projects.Project;
import acme.features.auditor.auditRecord.AuditorAuditRecordRepository;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditPublishService extends AbstractService<Auditor, CodeAudit> {

	@Autowired
	protected AuditorCodeAuditRepository	repository;

	@Autowired
	protected AuditorAuditRecordRepository	rp;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		CodeAudit ca;
		Auditor auditor;
		masterId = super.getRequest().getData("id", int.class);

		ca = this.repository.findCodeAuditById(masterId);
		auditor = ca == null ? null : ca.getAuditor();
		status = ca != null && super.getRequest().getPrincipal().hasRole(auditor);
		boolean autorizacion = auditor.getUserAccount().getUsername().equals(super.getRequest().getPrincipal().getUsername());

		super.getResponse().setAuthorised(status && autorizacion);
	}

	@Override
	public void load() {
		CodeAudit object;

		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCodeAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		super.bind(object, "code", "execution", "type", "correctiveActions", "optionalLink");

	}
	@Override
	public void validate(final CodeAudit object) {
		assert object != null;
		String mark = object.Mark(this.rp.getScoreOfAsociatedPublishedAuditRecords(object));

		if (!super.getBuffer().getErrors().hasErrors("published"))
			super.state(object.isPublished() == false, "published", "auditor.codeAudit.error.published");

		if (mark != null)
			if (!super.getBuffer().getErrors().hasErrors("Mark"))
				super.state(!object.Mark(this.rp.getScoreOfAsociatedPublishedAuditRecords(object)).trim().equals("F") && !object.Mark(this.rp.getScoreOfAsociatedPublishedAuditRecords(object)).trim().equals("F-"), "Mark", "auditor.codeAudit.error.Mark");
		if (mark == null)
			super.state(object.Mark(this.rp.getScoreOfAsociatedPublishedAuditRecords(object)) != null, "Mark", "auditor.codeAudit.error.Mark");
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;
		object.setPublished(true);
		this.repository.save(object);
	}
	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;
		SelectChoices projectsChoices;
		Collection<Project> projects;

		projects = this.repository.findAllProjects();
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "execution", "type", "correctiveActions", "optionalLink", "published");
		String mark = object.Mark(this.rp.getScoreOfAsociatedPublishedAuditRecords(object));
		dataset.put("Mark", mark);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);
	}

}
