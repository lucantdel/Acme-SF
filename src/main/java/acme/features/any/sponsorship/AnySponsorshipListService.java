
package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;

@Service
public class AnySponsorshipListService extends AbstractService<Any, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnySponsorshipRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Sponsorship> objects;

		objects = this.repository.findPublishedSponsorships();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset;
		String payload;

		//dataset = super.unbind(object, "code", "moment", "startDuration", "finalDuration", "amount", "type", "email", "link");
		// para que solo se vena las importantes
		dataset = super.unbind(object, "code", "moment", "amount", "type");
		payload = String.format(//
			"%s; %s; %s; %s; %s; %s;", //
			object.getStartDuration(), //
			object.getFinalDuration(), //
			object.getEmail(), //
			object.getLink(), //
			object.getProject().getCode(), //
			object.getSponsor().getName());
		dataset.put("payload", payload);

		super.getResponse().addData(dataset);
	}

}
