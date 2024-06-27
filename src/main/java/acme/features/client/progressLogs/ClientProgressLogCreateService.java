
package acme.features.client.progressLogs;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.entities.progressLogs.ProgressLog;
import acme.roles.Client;

@Service
public class ClientProgressLogCreateService extends AbstractService<Client, ProgressLog> {

	@Autowired
	private ClientProgressLogRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Contract contract;

		masterId = super.getRequest().getData("masterId", int.class);
		contract = this.repository.findOneContractById(masterId);

		Client c = contract.getClient();

		int activeClientId = super.getRequest().getPrincipal().getActiveRoleId();
		Client activeClient = this.repository.findOneClientById(activeClientId);
		boolean clientOwnsContract = c == activeClient;

		status = clientOwnsContract && contract != null && !contract.isDraftMode() && super.getRequest().getPrincipal().hasRole(contract.getClient());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProgressLog object;
		int masterId;
		Contract contract;

		masterId = super.getRequest().getData("masterId", int.class);
		contract = this.repository.findOneContractById(masterId);

		object = new ProgressLog();
		object.setDraftMode(true);
		object.setContract(contract);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProgressLog object) {
		assert object != null;
		super.bind(object, "recordId", "completeness", "comment", "responsiblePerson");
		final Date cMoment = MomentHelper.getCurrentMoment();
		object.setRegistrationMoment(cMoment);
	}

	@Override
	public void validate(final ProgressLog object) {

		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("recordId")) {
			ProgressLog existing;

			existing = this.repository.findOneProgressLogByRecordId(object.getRecordId());
			super.state(existing == null, "recordId", "client.progress-log.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("completeness")) {
			Double existing;
			existing = this.repository.findPublishedProgressLogWithMaxCompletenessPublished(object.getContract().getId()).orElse(0.);
			super.state(object.getCompleteness() > existing, "completeness", "client.progress-log.form.error.completeness-too-low");
		}

	}

	@Override
	public void perform(final ProgressLog object) {
		assert object != null;
		object.setDraftMode(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "recordId", "completeness", "comment", "responsiblePerson", "draftMode");

		dataset.put("masterId", object.getContract().getId());
		dataset.put("draftMode", object.isDraftMode());

		super.getResponse().addData(dataset);
	}
}
