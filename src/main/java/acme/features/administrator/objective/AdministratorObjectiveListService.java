
package acme.features.administrator.objective;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.objectives.Objective;

@Service
public class AdministratorObjectiveListService extends AbstractService<Administrator, Objective> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorObjectiveRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		/*
		 * El usuario debe tener el rol Administrator
		 */
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Objective> objects;

		objects = this.repository.findAllObjectives();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Objective object) {
		assert object != null;

		Dataset dataset;
		String payload;

		dataset = super.unbind(object, "title", "startMoment", "endMoment", "priority");
		payload = String.format(//
			"%s; %s; %s", //
			object.getDescription(), //
			object.isStatus(), //
			object.getOptionalLink());
		dataset.put("payload", payload);

		super.getResponse().addData(dataset);
	}

}
