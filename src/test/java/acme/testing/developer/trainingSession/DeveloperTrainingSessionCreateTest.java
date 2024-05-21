
package acme.testing.developer.trainingSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class DeveloperTrainingSessionCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "developer/trainingSession/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String creationMoment, final String details, final String difficultyLevel, final String project, final String totalEstimatedTime, final String link) {
		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "List my training modules");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("creationMoment", creationMoment);
		super.fillInputBoxIn("details", details);
		super.fillInputBoxIn("difficultyLevel", difficultyLevel);
		super.fillInputBoxIn("project", project);
		super.fillInputBoxIn("totalEstimatedTime", totalEstimatedTime);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Developer", "List my training modules");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, creationMoment);
		super.checkColumnHasValue(recordIndex, 2, difficultyLevel);
		super.checkColumnHasValue(recordIndex, 3, project);
		super.checkColumnHasValue(recordIndex, 4, "0");
		super.checkColumnHasValue(recordIndex, 5, "true");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("creationMoment", creationMoment);
		super.checkInputBoxHasValue("details", details);
		super.checkInputBoxHasValue("difficultyLevel", difficultyLevel);
		super.checkInputBoxHasValue("project", project);
		super.checkInputBoxHasValue("totalEstimatedTime", totalEstimatedTime);
		super.checkInputBoxHasValue("link", link);

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "developer/trainingSession/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String creationMoment, final String details, final String difficultyLevel, final String project, final String totalEstimatedTime, final String link) {
		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "List my training modules");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("creationMoment", creationMoment);
		super.fillInputBoxIn("details", details);
		super.fillInputBoxIn("difficultyLevel", difficultyLevel);
		super.fillInputBoxIn("project", project);
		super.fillInputBoxIn("totalEstimatedTime", totalEstimatedTime);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/developer/trainingSession/list");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/developer/trainingSession/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor2", "auditor2");
		super.request("developer/trainingSession/list");
		super.checkPanicExists();
		super.signOut();
	}
}
