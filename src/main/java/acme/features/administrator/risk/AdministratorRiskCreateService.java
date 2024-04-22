
package acme.features.administrator.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.risks.Risk;

@Service
public class AdministratorRiskCreateService extends AbstractService<Administrator, Risk> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorRiskRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Risk object;

		object = new Risk();
		object.setIdentificationDate(MomentHelper.getCurrentMoment());
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Risk object) {
		assert object != null;

		super.bind(object, "reference", "identificationDate", "impact", "probability", "description", "optionalLink");
	}

	@Override
	public void validate(final Risk object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("reference")) {
			Risk existing;

			existing = this.repository.findOneRiskByReferece(object.getReference());
			super.state(existing == null, "reference", "administrator.risk.form.error.duplicated");
		}

	}

	@Override
	public void perform(final Risk object) {
		assert object != null;
		if (object.getImpact() == null)
			object.setImpact(0.0);
		if (object.getProbability() == null)
			object.setProbability(0.0);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "reference", "identificationDate", "impact", "probability", "description", "optionalLink");

		super.getResponse().addData(dataset);
	}
}
