
package acme.features.manager.project;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.AbstractEntity;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudits.AuditRecord;
import acme.entities.codeAudits.CodeAudit;
import acme.entities.contract.Contract;
import acme.entities.invoices.Invoice;
import acme.entities.progressLogs.ProgressLog;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		/*
		 * El rol del usuario logueado debe ser Manager
		 * El proyecto que aparece debe pertenecer al manager logueado
		 * El proyecto debe estar en estado borrador
		 */
		boolean status;
		Project project;
		Manager manager;

		project = this.repository.findOneProjectById(super.getRequest().getData("id", int.class));
		manager = this.repository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());

		status = super.getRequest().getPrincipal().getActiveRole() == Manager.class //
			&& project.getManager().equals(manager) && project.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);

		Project object = this.repository.findOneProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "projectAbstract", "link", "cost", "indication");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;
	}

	@Override
	public void perform(final Project object) {
		/*
		 * TODO: verificar, corregir y cambiar codigo
		 * Se deben eliminar todos los objetos que tengan relacion con el proyecto que se va a eliminar:
		 * Contract, ProgressLogs, Sponsorship, Invoice, CodeAudit, AuditRecord, TrainingModule, TrainingSession, ProjectUserStory
		 */
		assert object != null;

		Collection<Contract> contracts;
		Collection<ProgressLog> progressLogs;

		Collection<Sponsorship> sponsorShips;
		Collection<Invoice> invoices;

		Collection<CodeAudit> codeAudits;
		Collection<AuditRecord> auditRecords;

		Collection<TrainingModule> trainingModule;
		Collection<TrainingSession> trainingSession;

		Collection<ProjectUserStory> pus;
		int id = object.getId();

		sponsorShips = this.repository.findManySponsorshipsByProjectId(id);
		if (sponsorShips != null) {
			Set<Integer> sponsorShipIds = sponsorShips.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			invoices = this.repository.findManyInvoicesBySponsorshipIds(sponsorShipIds);
			this.repository.deleteAll(invoices);
			this.repository.deleteAll(sponsorShips);
		}

		contracts = this.repository.findManyContractsByProjectId(id);
		if (contracts != null) {
			Set<Integer> contractIds = contracts.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			progressLogs = this.repository.findManyProgressLogsByContractIds(contractIds);
			this.repository.deleteAll(progressLogs);
			this.repository.deleteAll(contracts);
		}

		codeAudits = this.repository.findManyCodeAuditsByProjectId(id);
		if (codeAudits != null) {
			Set<Integer> codeAuditsIds = codeAudits.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			auditRecords = this.repository.findManyAuditsRecordsByCodeAuditsId(codeAuditsIds);
			this.repository.deleteAll(auditRecords);
			this.repository.deleteAll(codeAudits);
		}

		trainingModule = this.repository.findManyTrainingModuleByProjectId(id);
		if (trainingModule != null) {
			Set<Integer> trainingModuleIds = trainingModule.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			trainingSession = this.repository.findManyTrainingSessionByTrainingModuleId(trainingModuleIds);
			this.repository.deleteAll(trainingSession);
			this.repository.deleteAll(trainingModule);
		}

		pus = this.repository.findProjectUserStoryByProjectId(id);
		this.repository.deleteAll(pus);

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "title", "projectAbstract", "link", "cost", "indication", "draftMode");

		super.getResponse().addData(dataset);
	}

}
