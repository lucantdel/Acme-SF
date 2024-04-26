
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;


	@Override
	public void authorise() {
		boolean autorizacion = super.getRequest().getPrincipal().getActiveRole().getName() == "acme.client.data.accounts.Administrator";
		super.getResponse().setAuthorised(autorizacion);
	}

	@Override
	public void load() {
		Banner object;

		object = new Banner();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "updateMoment", "displayPeriodStart", "displayPeriodEnd", "picture", "slogan", "webDoc");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd"))
			super.state(object.validateDisplayPeriod() == true, "displayPeriodEnd", "administrator.banner.error.period");

	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "updateMoment", "displayPeriodStart", "displayPeriodEnd", "picture", "slogan", "webDoc");

		super.getResponse().addData(dataset);
	}

}
