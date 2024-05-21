
package acme.testing.developer.trainingSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.trainingModule.TrainingModule;
import acme.testing.TestHarness;

public class DeveloperTrainingSessionDeleteTest extends TestHarness {

	@Autowired
	protected DeveloperTrainingSessionRepositoryTest repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code) {

		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "List my training modules");
		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);

		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		Collection<TrainingModule> modules;
		String masterId;

		modules = this.repository.findAllTrainingModuleByDeveloperuserName("developer1");
		for (final TrainingModule mod : modules)
			if (mod.isDraftMode()) {
				masterId = String.format("id=%d", mod.getId());

				super.checkLinkExists("Sign in");
				super.request("/developer/trainingModule/delete", masterId);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/developer/trainingModule/delete", masterId);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor2", "auditor2");
				super.request("/developer/trainingModule/delete", masterId);
				super.checkPanicExists();
				super.signOut();

				super.signIn("developer2", "developer2");
				super.request("/lecturer/course/delete", masterId);
				super.checkPanicExists();
				super.signOut();
			}
	}
	@Test
	public void test301Hacking() {
		Collection<TrainingModule> modules;
		String masterId;

		super.signIn("developer1", "developer1");
		modules = this.repository.findAllTrainingModuleByDeveloperuserName("developer1");
		for (final TrainingModule mod : modules)
			if (!mod.isDraftMode()) {
				masterId = String.format("id=%d", mod.getId());
				super.request("/developer/trainingModule/delete", masterId);
				super.checkPanicExists();

			}
		super.signOut();

	}

}
