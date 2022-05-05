package guru.qa.allure;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

public class SelenideTest {
    @Test
    public void testGitHubIssue() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("https://github.com/");
        $(".header-search-input").click();
        $(".header-search-input").sendKeys("NikolayPostanogov/qa_guru_12_5");
        $(".header-search-input").submit();

        $(linkText("NikolayPostanogov/qa_guru_12_5")).click();
        $(partialLinkText("Issues")).click();
        $(withText("#1")).should(Condition.visible);
    }
}
