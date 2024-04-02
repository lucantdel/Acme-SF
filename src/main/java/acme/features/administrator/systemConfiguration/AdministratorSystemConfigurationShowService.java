
package acme.features.administrator.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.systemConfiguration.SystemConfiguration;

@Service
public class AdministratorSystemConfigurationShowService extends AbstractService<Administrator, SystemConfiguration> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorSystemConfigurationRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		status = principal.hasRole(Administrator.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SystemConfiguration object;

		object = this.repository.findActualSystemConfiguration();

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;

		final Dataset tuple;

		tuple = super.unbind(object, "acceptedCurrencies", "systemCurrency");

		super.getResponse().addData(tuple);
	}
}
