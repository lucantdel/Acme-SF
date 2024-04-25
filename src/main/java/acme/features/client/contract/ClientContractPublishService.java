
package acme.features.client.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.roles.Client;

@Service
public class ClientContractPublishService extends AbstractService<Client, Contract> {

	@Autowired
	protected ClientContractRepository repository;


	@Override
	public void authorise() {
		Contract object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.getContractById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getClient().getUserAccount().getId() == userAccountId && object.getDraftMode());
	}

	@Override
	public void load() {
		Contract object;
		object = new Contract();
		object.setDraftMode(true);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		Contract object2;
		int id;

		id = super.getRequest().getData("id", int.class);
		object2 = this.repository.getContractById(id);
		object.setProject(object2.getProject());
		object.setClient(object2.getClient());
		super.bind(object, "code", "provider", "moment", "customer", "goals", "budget");
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "moment", "provider", "customer", "goals", "budget", "project", "client", "draftMode");
		dataset.put("project", object.getProject().getCode());
		super.getResponse().addData(dataset);
	}

}
