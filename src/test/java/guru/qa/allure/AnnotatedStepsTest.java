package guru.qa.allure;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;

public class AnnotatedStepsTest {
    private static final String REPOSITORY = "NikolayPostanogov/qa_guru_12_5";
    private static final int ISSUE_NUMBER = 1;
    @Test
    public void testGitHubIssue() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.openIssuesTab();
        steps.shouldSeeIssueWithNumber(ISSUE_NUMBER);
    }
}
