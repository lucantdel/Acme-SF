
package acme.features.any.progressLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.progress_logs.ProgressLog;

@Service
public class AnyProgressLogShowService extends AbstractService<Any, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProgressLogRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		ProgressLog progressLog = this.repository.findOneProgressLogById(id);
		boolean status = progressLog != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		ProgressLog progressLog = this.repository.findOneProgressLogById(id);
		super.getBuffer().addData(progressLog);
	}

	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;
		Dataset dataset = super.unbind(object, "recordId", "completenessPercentage", "progressComment", "registrationMoment", "responsiblePerson");
		super.getResponse().addData(dataset);
	}
}
