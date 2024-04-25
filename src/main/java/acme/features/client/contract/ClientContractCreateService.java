
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.projects.Project;
import acme.roles.Client;

@Service
public class ClientContractCreateService extends AbstractService<Client, Contract> {

	@Autowired
	private ClientContractRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Contract contract;
		Client client;

		client = this.repository.getClientById(super.getRequest().getPrincipal().getActiveRoleId());
		contract = new Contract();
		contract.setDraftMode(true);
		contract.setClient(client);
		super.getBuffer().addData(contract);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.getProjectById(projectId);

		super.bind(object, "code", "moment", "provider", "customer", "goals", "budget", "project");
		object.setProject(project);
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "moment", "provider", "customer", "goals", "budget");
		final SelectChoices choices = new SelectChoices();
		Collection<Project> projects;
		projects = this.repository.getPublishedProjects();
		for (final Project p : projects)
			choices.add(Integer.toString(p.getId()), p.getCode() + " - " + p.getTitle(), false);

		dataset.put("projects", choices);
		super.getResponse().addData(dataset);

	}

}
