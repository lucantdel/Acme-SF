
package acme.features.manager.project;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeAudits.AuditRecord;
import acme.entities.codeAudits.CodeAudit;
import acme.entities.contract.Contract;
import acme.entities.invoices.Invoice;
import acme.entities.progress_logs.ProgressLogs;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.projects.UserStory;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select p from Project p where p.manager.id = :id")
	Collection<Project> findProjectsByManagerId(int id);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select p from Project p where p.code = :code")
	Optional<Project> findOneProjectByCode(String code);

	@Query("select m from Manager m where m.id = :managerId")
	Manager findOneManagerById(int managerId);

	@Query("select pus.userStory from ProjectUserStory pus where pus.project.id = :projectId")
	Collection<UserStory> findUserStoriesByProjectId(int projectId);

	@Query("select us from UserStory us where us.manager.id =: managerId ")
	Collection<UserStory> findUserStoriesByManagerId(int managerId);

	@Query("select pus from ProjectUserStory pus where pus.project.id = :projectId")
	Collection<ProjectUserStory> findProjectUserStoryByProjectId(int projectId);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findActualSystemConfiguration();

	//Delete
	// TODO: Corregir consultas
	@Query("select c from Contract c where c.project.id = :id")
	Collection<Contract> findManyContractsByProjectId(int id);

	@Query("select pl from ProgressLogs pl where pl.contract.id IN :ids")
	Collection<ProgressLogs> findManyProgressLogsByContractIds(Set<Integer> ids);

	@Query("select ss from Sponsorship ss where ss.project.id = :id")
	Collection<Sponsorship> findManySponsorshipsByProjectId(int id);

	@Query("select i from Invoice i where i.sponsorship.id IN :ids")
	Collection<Invoice> findManyInvoicesBySponsorshipIds(Set<Integer> ids);

	@Query("select ca from CodeAudit ca where ca.project.id = :id")
	Collection<CodeAudit> findManyCodeAuditsByProjectId(int id);

	@Query("select ar from AuditRecord ar where ar.codeAudit.id IN :id")
	Collection<AuditRecord> findManyAuditsRecordsByCodeAuditsId(Set<Integer> id);

	@Query("select tm from TrainingModule tm where tm.project.id = :id")
	Collection<TrainingModule> findManyTrainingModuleByProjectId(int id);

	@Query("select ts from TrainingSession ts where ts.trainingModule.id IN :id")
	Collection<TrainingSession> findManyTrainingSessionByTrainingModuleId(Set<Integer> id);

}
