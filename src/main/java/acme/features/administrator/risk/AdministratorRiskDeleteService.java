
package acme.features.administrator.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risks.Risk;

@Service
public class AdministratorRiskDeleteService extends AbstractService<Administrator, Risk> {

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
		Risk object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneRiskById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Risk object) {
		assert object != null;

		super.bind(object, "name", "description", "moreInfo");
	}

	@Override
	public void validate(final Risk object) {
		assert object != null;

	}

	@Override
	public void perform(final Risk object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "reference", "identificationDate", "impact", "probability", "description", "optionalLink");
		dataset.put("value", object.value());

		super.getResponse().addData(dataset);
	}
}
