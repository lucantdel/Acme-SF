
package acme.testing.developer.trainingSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.trainingModule.TrainingModule;
import acme.testing.TestHarness;

public class DeveloperTrainingSessionUpdateTest extends TestHarness {

	@Autowired
	protected DeveloperTrainingSessionRepositoryTest repository;


	@ParameterizedTest
	@CsvFileSource(resources = "developer/trainingModule/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String creationMoment, final String updateMoment, final String details, final String difficultyLevel, final String project, final String totalEstimatedTime,
		final String link) {
		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "List my training modules");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("creationMoment", creationMoment);
		super.fillInputBoxIn("updateMoment", updateMoment);
		super.fillInputBoxIn("details", details);
		super.fillInputBoxIn("difficultyLevel", difficultyLevel);
		super.fillInputBoxIn("project", project);
		super.fillInputBoxIn("totalEstimatedTime", totalEstimatedTime);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("creationMoment", creationMoment);
		super.checkInputBoxHasValue("updateMoment", updateMoment);
		super.checkInputBoxHasValue("details", details);
		super.checkInputBoxHasValue("difficultyLevel", difficultyLevel);
		super.checkInputBoxHasValue("project", project);
		super.checkInputBoxHasValue("totalEstimatedTime", totalEstimatedTime);
		super.checkInputBoxHasValue("link", link);

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "developer/trainingModule/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String creationMoment, final String updateMoment, final String details, final String difficultyLevel, final String project, final String totalEstimatedTime,
		final String link) {
		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "List my training modules");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("creationMoment", creationMoment);
		super.fillInputBoxIn("updateMoment", updateMoment);
		super.fillInputBoxIn("details", details);
		super.fillInputBoxIn("difficultyLevel", difficultyLevel);
		super.fillInputBoxIn("project", project);
		super.fillInputBoxIn("totalEstimatedTime", totalEstimatedTime);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

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
				super.request("/developer/trainingModule/update", masterId);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/developer/trainingModule/update", masterId);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor2", "auditor2");
				super.request("/developer/trainingModule/update", masterId);
				super.checkPanicExists();
				super.signOut();

				super.signIn("developer2", "developer2");
				super.request("/developer/trainingModule/update", masterId);
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
				super.request("/developer/trainingModule/update", masterId);
				super.checkPanicExists();

			}
		super.signOut();

	}

}
