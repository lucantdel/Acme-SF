
package acme.testing;

import acme.client.helpers.StringHelper;
import acme.client.testing.ApplicationAbstractTest;

public abstract class TestHarness extends ApplicationAbstractTest {

	// Business methods -------------------------------------------------------

	@Override
	protected void signIn(final String username, final String password) {
		assert !StringHelper.isBlank(username);
		assert !StringHelper.isBlank(password);

		super.requestHome();
		super.clickOnMenu("Sign in");
		super.fillInputBoxIn("username", username);
		super.fillInputBoxIn("password", password);
		super.fillInputBoxIn("remember", "true");
		super.clickOnSubmit("Sign in");
		super.checkCurrentPath("/any/system/welcome");
		super.checkLinkExists("Account");
	}

	@Override
	protected void signOut() {
		super.requestHome();
		super.clickOnMenu("Sign out");
		super.checkCurrentPath("/any/system/welcome");
		super.checkLinkExists("Sign in");
	}

	protected void signUp(final String username, final String password, final String name, final String surname, final String email, final String phone) {
		assert !StringHelper.isBlank(username);
		assert !StringHelper.isBlank(password);
		assert !StringHelper.isBlank(name);
		assert !StringHelper.isBlank(surname);
		assert !StringHelper.isBlank(email);

		super.requestHome();
		super.clickOnMenu("Sign up");
		super.fillInputBoxIn("username", username);
		super.fillInputBoxIn("password", password);
		super.fillInputBoxIn("confirmation", password);
		super.fillInputBoxIn("identity.name", name);
		super.fillInputBoxIn("identity.surname", surname);
		super.fillInputBoxIn("identity.email", email);
		super.fillInputBoxIn("identity.phone", phone);
		super.fillInputBoxIn("accept", "true");
		super.clickOnSubmit("Sign up");
		super.checkCurrentPath("/any/system/welcome");
		super.checkLinkExists("Sign in");
	}
}
