
package acme.features.any.claim;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.claim.Claim;

@Service
public class AnyClaimCreateService extends AbstractService<Any, Claim> {

	@Autowired
	protected AnyClaimRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	// cargamos objeto vacio cn fecha
	@Override
	public void load() {
		Claim newClaim = new Claim();

		final Date instantiation = MomentHelper.getCurrentMoment();

		newClaim.setInstantiationMoment(instantiation);

		super.getBuffer().addData(newClaim);
	}

	// le metemos los datos del objeto desde el objeto de la request
	@Override
	public void bind(final Claim object) {
		assert object != null;

		super.bind(object, "code", "heading", "description", "department", "email", "link");
	}

	@Override
	public void validate(final Claim object) {
		assert object != null;

		// asegurar que el estado del boton confirmed esta seleccionado
		if (!super.getBuffer().getErrors().hasErrors("confirm")) {
			final boolean confirm = super.getRequest().getData("confirm", boolean.class);

			// aqui mira el estado
			super.state(confirm, "confirm", "any.claim.form.error.not-confirmed");
		}

		// aqui se asegura de que el nuevo objeto creado no tenga el mismo code que alguno ya creado
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final boolean duplicatedCode = this.repository.findPublishedClaims().stream().anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!duplicatedCode, "code", "any.claim.form.error.duplicated-code = Duplicated code");
		}

	}

	// lo metemos en el repositorio
	@Override
	public void perform(final Claim object) {
		assert object != null;

		this.repository.save(object);
	}

	// devolvemos por partes el objeto nuevo que se ha guardado, para que sea mostrado en la vista
	@Override
	public void unbind(final Claim object) {
		assert object != null;

		final Dataset dataset = super.unbind(object, "code", "heading", "description", "department", "email", "link");

		dataset.put("confirm", false);

		super.getResponse().addData(dataset);
	}
}
