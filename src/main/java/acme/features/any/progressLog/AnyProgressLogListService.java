
package acme.features.any.progressLog;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.progress_logs.ProgressLog;

@Service
public class AnyProgressLogListService extends AbstractService<Any, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProgressLogRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<ProgressLog> objects = this.repository.findMany();
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;
		Dataset dataset = super.unbind(object, "recordId", "completenessPercentage", "progressComment", "registrationMoment", "responsiblePerson");
		super.getResponse().addData(dataset);
	}
}
