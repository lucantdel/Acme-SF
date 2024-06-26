
package acme.features.manager.project;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Manager;

@Service
public class ManagerProjectCreateService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		/*
		 * El rol del usuario logueado debe ser Manager
		 */
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		Manager manager;

		manager = this.repository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Project();
		object.setDraftMode(true);
		object.setManager(manager);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "projectAbstract", "indication", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		/*
		 * No puede haber proyectos con el mismo codigo
		 * No se pueden introducir cantidades negativas
		 * La divisa introducida debe existir en el sistema
		 */
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Optional<Project> existing;

			existing = this.repository.findOneProjectByCode(object.getCode());
			if (existing.isPresent())
				super.state(existing.get() == null, "code", "manager.project.form.error.duplicated-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("cost")) {
			Double amount;
			amount = object.getCost().getAmount();
			super.state(amount >= 0, "cost", "manager.project.form.error.negative-cost");

			final SystemConfiguration sc = this.repository.findActualSystemConfiguration();
			final String currency = object.getCost().getCurrency();
			super.state(sc.getAcceptedCurrencies().contains(currency), "cost", "manager.project.form.error.not-accepted-currency");
		}
	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "projectAbstract", "indication", "cost", "link", "draftMode");

		super.getResponse().addData(dataset);
	}
}
