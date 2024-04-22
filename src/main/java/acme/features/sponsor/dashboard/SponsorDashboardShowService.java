
package acme.features.sponsor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		SponsorDashboard dashboard;
		int totalNumberOfInvoicesWithLowTax;
		int totalNumberOfSponsorshipsWithLink;
		double avgSponsorshipsAmount;
		double devSponsorshipsAmount;
		double minSponsorshipsAmount;
		double maxSponsorshipsAmount;
		double avgInvoicesQuantity;
		double devInvoicesQuantity;
		double minInvoicesQuantity;
		double maxInvoicesQuantity;

		totalNumberOfInvoicesWithLowTax = this.repository.totalNumberOfInvoicesWithLowTax();
		totalNumberOfSponsorshipsWithLink = this.repository.totalNumberOfSponsorshipsWithLink();
		avgSponsorshipsAmount = this.repository.avgSponsorshipsAmount();
		devSponsorshipsAmount = this.repository.devSponsorshipsAmount();
		minSponsorshipsAmount = this.repository.minSponsorshipsAmount();
		maxSponsorshipsAmount = this.repository.maxSponsorshipsAmount();
		avgInvoicesQuantity = this.repository.avgInvoicesQuantity();
		devInvoicesQuantity = this.repository.devInvoicesQuantity();
		minInvoicesQuantity = this.repository.minInvoicesQuantity();
		maxInvoicesQuantity = this.repository.maxInvoicesQuantity();

		dashboard = new SponsorDashboard();
		dashboard.setTotalNumberOfInvoicesWithLowTax(totalNumberOfInvoicesWithLowTax);
		dashboard.setTotalNumberOfSponsorshipsWithLink(totalNumberOfSponsorshipsWithLink);
		dashboard.setAvgSponsorshipsAmount(avgSponsorshipsAmount);
		dashboard.setDevSponsorshipsAmount(devSponsorshipsAmount);
		dashboard.setMinSponsorshipsAmount(minSponsorshipsAmount);
		dashboard.setMaxSponsorshipsAmount(maxSponsorshipsAmount);
		dashboard.setAvgInvoicesQuantity(avgInvoicesQuantity);
		dashboard.setDevInvoicesQuantity(devInvoicesQuantity);
		dashboard.setMinInvoicesQuantity(minInvoicesQuantity);
		dashboard.setMaxInvoicesQuantity(maxInvoicesQuantity);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumberOfInvoicesWithLowTax", "totalNumberOfSponsorshipsWithLink",  // 
			"avgSponsorshipsAmount", "devSponsorshipsAmount", "minSponsorshipsAmount", "maxSponsorshipsAmount",  //
			"avgInvoicesQuantity", "devInvoicesQuantity", "minInvoicesQuantity", "maxInvoicesQuantity");

		super.getResponse().addData(dataset);

	}
}
