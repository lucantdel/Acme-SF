
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerShowService extends AbstractService<Administrator, Banner> {

	@Autowired
	private AdministratorBannerRepository rp;


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
		object = this.rp.findOneBannerById(id);

		super.getBuffer().addData(object);
	}
	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "updateMoment", "displayPeriodStart", "displayPeriodEnd", "picture", "slogan", "webDoc");

		super.getResponse().addData(dataset);
	}

}
