
package acme.features.authenticated.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.accounts.Principal;
import acme.client.data.accounts.UserAccount;
import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.roles.Manager;

@Service
public class AuthenticatedManagerCreateService extends AbstractService<Authenticated, Manager> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedManagerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Manager object;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		object = new Manager();
		object.setUserAccount(userAccount);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Manager object) {
		assert object != null;

		super.bind(object, "degree", "overview", "certifications", "link");
	}

	@Override
	public void validate(final Manager object) {
		assert object != null;
	}

	@Override
	public void perform(final Manager object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Manager object) {
		Dataset dataset;

		dataset = super.unbind(object, "degree", "overview", "certifications", "link");

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
