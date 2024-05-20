
package acme.testing.developer.trainingModule;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.trainingModule.TrainingModule;
import acme.testing.TestHarness;

public class DeveloperTrainingModuleShowTest extends TestHarness {

	@Autowired
	protected DeveloperTrainingModuleRepositoryTest repository;


	@ParameterizedTest
	@CsvFileSource(resources = "developer/trainingModule/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String creationMoment, final String updateMoment, final String details, final String difficultyLevel, final String project, final String totalEstimatedTime,
		final String link) {

		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "List my training modules");
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

	@Test
	public void test300Hacking() {
		Collection<TrainingModule> modules;
		String masterId;

		modules = this.repository.findAllTrainingModuleByDeveloperuserName("developer1");
		for (final TrainingModule mod : modules)
			if (mod.isDraftMode()) {
				masterId = String.format("id=%d", mod.getId());

				super.checkLinkExists("Sign in");
				super.request("/developer/trainingModule/show", masterId);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/developer/trainingModule/show", masterId);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor2", "auditor2");
				super.request("/developer/trainingModule/show", masterId);
				super.checkPanicExists();
				super.signOut();

				super.signIn("developer2", "developer2");
				super.request("/developer/trainingModule/show", masterId);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
