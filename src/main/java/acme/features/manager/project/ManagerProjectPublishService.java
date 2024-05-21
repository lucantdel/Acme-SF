
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

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
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "projectAbstract", "link", "cost", "indication");
	}

	@Override
	public void validate(final Project object) {
		/*
		 * No puede haber proyectos con el mismo codigo
		 * No se pueden introducir cantidades negativas
		 * La divisa introducida debe existir en el sistema
		 * 
		 * No puede tener errores criticos (indication debe ser false)
		 * Tiene que tener al menos una historia de usuario
		 * Todas las historias de usuario deben estar publicadas
		 */
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final int proyectId = super.getRequest().getData("id", int.class);
			final boolean duplicatedCode = this.repository.findAllProjects().stream().filter(e -> e.getId() != proyectId).anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!duplicatedCode, "code", "manager.project.form.error.duplicated-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("cost")) {
			Double amount;
			amount = object.getCost().getAmount();
			super.state(amount >= 0, "cost", "manager.project.form.error.negative-cost");

			final SystemConfiguration sc = this.repository.findActualSystemConfiguration();
			final String currency = object.getCost().getCurrency();
			super.state(sc.getAcceptedCurrencies().contains(currency), "cost", "manager.project.form.error.not-accepted-currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("indication"))
			super.state(!object.isIndication(), "indication", "manager.project.form.error.has-fatal-error");

		Collection<UserStory> userStories;
		int totalUserStories;
		boolean allUserStoriesPublished;

		userStories = this.repository.findUserStoriesByProjectId(object.getId());

		totalUserStories = userStories.size();

		allUserStoriesPublished = userStories.stream().allMatch(us -> !us.isDraftMode());

		super.state(totalUserStories >= 1, "*", "manager.project.form.error.not-enough-user-stories");

		super.state(allUserStoriesPublished, "*", "manager.project.form.error.not-all-user-stories-published");
	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "projectAbstract", "link", "cost", "indication", "draftMode");

		super.getResponse().addData(dataset);

	}
}
