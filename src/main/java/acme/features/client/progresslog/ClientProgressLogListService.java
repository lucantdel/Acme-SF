
package acme.features.client.progresslog;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.entities.progress_logs.ProgressLogs;
import acme.roles.Client;

@Service
public class ClientProgressLogListService extends AbstractService<Client, ProgressLogs> {

	@Autowired
	protected ClientProgressLogRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		Contract object;
		int Id;
		Id = super.getRequest().getData("Id", int.class);
		object = this.repository.findContractById(Id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getClient().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		Collection<ProgressLogs> objects;
		int Id;
		Id = super.getRequest().getData("Id", int.class);
		objects = this.repository.findProgressLogsByContract(Id);
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final ProgressLogs object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "recordId", "completenessPercentage", "responsiblePerson");
		int Id;
		Id = super.getRequest().getData("Id", int.class);
		super.getResponse().addGlobal("Id", Id);
		dataset.put("Id", Id);
		final Contract p = this.repository.findContractById(Id);
		final boolean showCreate = p.getDraftMode();
		super.getResponse().addGlobal("showCreate", showCreate);
		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<ProgressLogs> object) {
		assert object != null;
		int Id;
		Id = super.getRequest().getData("Id", int.class);
		super.getResponse().addGlobal("Id", Id);
		final Contract c = this.repository.findContractById(Id);
		final boolean showCreate = c.getDraftMode();
		super.getResponse().addGlobal("showCreate", showCreate);
	}

}
