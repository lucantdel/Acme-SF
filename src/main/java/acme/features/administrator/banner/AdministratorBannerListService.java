
package acme.features.administrator.banner;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerListService extends AbstractService<Administrator, Banner> {

	@Autowired
	private AdministratorBannerRepository rp;


	@Override
	public void authorise() {
		boolean autorizacion = super.getRequest().getPrincipal().getActiveRole().getName() == "acme.client.data.accounts.Administrator";
		super.getResponse().setAuthorised(autorizacion);
	}
	@Override
	public void load() {
		Collection<Banner> objects;
		objects = this.rp.findAllBanners();
		super.getBuffer().addData(objects);
	}
	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "updateMoment", "displayPeriodStart", "displayPeriodEnd", "slogan");

		super.getResponse().addData(dataset);
	}
}
