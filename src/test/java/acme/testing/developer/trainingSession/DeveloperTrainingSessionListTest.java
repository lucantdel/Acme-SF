
package acme.testing.developer.trainingSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class DeveloperTrainingSessionListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "developer/trainingModule/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String creationMoment, final String difficultyLevel, final String project, final String numberOfTrainingSessions, final String draftMode) {

		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "List my training modules");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, creationMoment);
		super.checkColumnHasValue(recordIndex, 2, difficultyLevel);
		super.checkColumnHasValue(recordIndex, 3, project);
		super.checkColumnHasValue(recordIndex, 4, numberOfTrainingSessions);
		super.checkColumnHasValue(recordIndex, 5, draftMode);

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/developer/trainingModule/list");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/developer/trainingModule/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor2", "auditor2");
		super.request("/developer/trainingModule/list");
		super.checkPanicExists();
		super.signOut();
	}

}
