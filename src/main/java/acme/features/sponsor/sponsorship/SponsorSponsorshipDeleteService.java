
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.invoices.Invoice;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipDeleteService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {
		boolean status;
		Sponsorship sph;
		Sponsor sponsor;

		sph = this.repository.findOneSponsorshipById(super.getRequest().getData("id", int.class));

		sponsor = sph == null ? null : this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());
		status = sph != null && super.getRequest().getPrincipal().getActiveRole() == Sponsor.class && sph.getSponsor().equals(sponsor) //
			&& sph.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	// creo uno nuevo y lo relleno segun el que me han dado
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	// enseño en el formulario el objeto para eliminarlo
	public void bind(final Sponsorship object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "moment", "startDuration", "finalDuration", "amount", "type", "email", "link", "project");
		//object.setProject(project); en este caso no le asigno project ya que lo vamos a eliminar
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(this.repository.countPublishedInvoicesBySponsorshipId(object.getId()) == 0, "code", "sponsor.sponsorship.form.error.deleteWithPublishedInvoices");
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		Collection<Invoice> invoices;

		// cascade if delete
		invoices = this.repository.findAllInvoicesBySponsorshipId(object.getId());
		this.repository.deleteAll(invoices);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "moment", "startDuration", "finalDuration", "amount", "type", "email", "link", "draftMode", "project");
		super.getResponse().addData(dataset);
	}

}
