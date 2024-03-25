
package acme.features.authenticated.objective;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.objectives.Objective;

@Service
public class AuthenticatedObjectiveShowService extends AbstractService<Authenticated, Objective> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedObjectiveRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Objective objective;
		Date deadline;

		id = super.getRequest().getData("id", int.class);
		objective = this.repository.findOneObjectiveById(id);
		deadline = MomentHelper.deltaFromCurrentMoment(-30, ChronoUnit.DAYS);
		status = MomentHelper.isAfter(objective.getInstantiationMoment(), deadline);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Objective object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneObjectiveById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Objective object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "instantiationMoment", "title", "description", "priority", "status", "startMoment", "endMoment", "optionalLink");

		super.getResponse().addData(dataset);
	}

}
