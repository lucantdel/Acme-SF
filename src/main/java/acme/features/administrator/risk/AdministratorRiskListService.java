
package acme.features.administrator.risk;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risks.Risk;

@Service
public class AdministratorRiskListService extends AbstractService<Administrator, Risk> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorRiskRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Risk> objects;

		objects = this.repository.findAllRisks();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset;
		String payload;

		dataset = super.unbind(object, "reference", "identificationDate");
		dataset.put("estimatedValue", object.estimatedValue());
		payload = String.format(//
			"%s; %s; %s; %s", //
			object.getDescription(), //
			object.getImpact(), //
			object.getProbability(),//
			object.getOptionalLink());
		dataset.put("payload", payload);

		super.getResponse().addData(dataset);
	}
}
