
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.roles.Client;

@Service
public class ClientContractListService extends AbstractService<Client, Contract> {

	@Autowired
	protected ClientContractRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Contract> objects;

		objects = this.repository.getAllContract();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "moment", "provider", "customer", "project");
		dataset.put("project", object.getProject().getCode());

		super.getResponse().addData(dataset);
	}

}
