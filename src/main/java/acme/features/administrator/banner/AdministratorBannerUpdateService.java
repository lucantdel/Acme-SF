
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	@Autowired
	private AdministratorBannerRepository repository;


	@Override
	public void authorise() {
		boolean autorizacion = super.getRequest().getPrincipal().getActiveRole().getName() == "acme.client.data.accounts.Administrator";
		super.getResponse().setAuthorised(autorizacion);
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);

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

		if (object.getDisplayPeriodStart() != null && object.getDisplayPeriodEnd() != null && object.getUpdateMoment() != null) {

			if (!super.getBuffer().getErrors().hasErrors("updateMoment"))
				super.state(!object.getUpdateMoment().before(object.getDisplayPeriodStart()), "updateMoment", "administrator.banner.error.updateMoment");

			if (!super.getBuffer().getErrors().hasErrors("updateMoment"))
				super.state(!object.getUpdateMoment().after(object.getDisplayPeriodEnd()), "updateMoment", "administrator.banner.error.updateMoment2");

			if (!super.getBuffer().getErrors().hasErrors("displayPeriodStart"))
				super.state(object.getDisplayPeriodStart().before(object.getDisplayPeriodEnd()), "displayPeriodStart", "administrator.banner.error.periodStart");

			if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd"))
				super.state(object.getDisplayPeriodEnd().after(object.getDisplayPeriodStart()), "displayPeriodEnd", "administrator.banner.error.period");

			if (!super.getBuffer().getErrors().hasErrors("displayPeriodStart"))
				super.state(object.validateDisplayPeriod() == true, "displayPeriodStart", "administrator.banner.error.periodEnd2");

			if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd"))
				super.state(object.validateDisplayPeriod() == true, "displayPeriodEnd", "administrator.banner.error.periodEnd2");

			if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd"))
				super.state(!object.getDisplayPeriodStart().after(object.getDisplayPeriodEnd()), "displayPeriodEnd", "administrator.banner.error.periodEnd2");

		}
		if (object.getUpdateMoment() == null) {

			if (!super.getBuffer().getErrors().hasErrors("displayPeriodStart"))
				super.state(object.getDisplayPeriodStart().before(object.getDisplayPeriodEnd()), "displayPeriodStart", "administrator.banner.error.periodStart");

			if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd"))
				super.state(object.getDisplayPeriodEnd().after(object.getDisplayPeriodStart()), "displayPeriodEnd", "administrator.banner.error.period");

			if (!super.getBuffer().getErrors().hasErrors("displayPeriodStart"))
				super.state(object.validateDisplayPeriod() == true, "displayPeriodStart", "administrator.banner.error.periodEnd2");

			if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd"))
				super.state(object.validateDisplayPeriod() == true, "displayPeriodEnd", "administrator.banner.error.periodEnd2");

			if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd"))
				super.state(!object.getDisplayPeriodStart().after(object.getDisplayPeriodEnd()), "displayPeriodEnd", "administrator.banner.error.periodEnd2");

		}
		if (object.getDisplayPeriodStart() == null) {
			if (!super.getBuffer().getErrors().hasErrors("updateMoment"))
				super.state(object.getDisplayPeriodStart() != null, "updateMoment", "administrator.banner.error.updateMoment3");

			if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd"))
				super.state(object.getDisplayPeriodStart() != null, "displayPeriodEnd", "administrator.banner.error.periodEnd3");

		}

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
