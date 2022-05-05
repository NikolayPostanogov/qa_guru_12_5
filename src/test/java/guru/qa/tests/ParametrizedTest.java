package guru.qa.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import guru.qa.pages.RegistrationFormPage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.format;

public class ParametrizedTest {
    String imgName = "img.png",
            imgPath = "img/" + imgName;

    @BeforeAll
    static void setUp() {
        Configuration.holdBrowserOpen = false;
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
    }


    @CsvSource(value = {
            "Alex | Egorov | test1@qa.guru | Male | 0001112223 | 2000 | May | 17 | Maths | adress1 | Sports | NCR | Delhi ",
            "Egor | Alexov | test2@qa.guru | Other | 3332221110 | 1980 | January | 01 | Maths | adress2 | Sports | NCR | Delhi"
    }, delimiter = '|')

    @ParameterizedTest(name = "Параметризованный тест формы пользователя {0} {1}")
    void formTests(String firstName,
                   String lastName,
                   String email,
                   String gender,
                   String phoneNumber,
                   String year,
                   String month,
                   String day,
                   String subject,
                   String address,
                   String hobby,
                   String state,
                   String city) {
        String expectedFullName = format("%s %s", firstName, lastName);
        String expectedBirthDate = format("%s %s,%s", day, month, year);
        String expectedStateAndCity = format("%s %s", state, city);

        RegistrationFormPage registrationFormPage = new RegistrationFormPage();


        registrationFormPage.openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setGender(gender)
                .setPhoneNumber(phoneNumber)
                .setBirthDate(day, month, year)
                .setSubject(subject)
                .setAddress(address)
                .setHobby(hobby)
                .setPicture(imgPath)
                .setState(state)
                .setCity(city)
                .clickSubmit();


        registrationFormPage.checkTitleResult()
                .checkResult("Student Name", expectedFullName)
                .checkResult("Student Email", email)
                .checkResult("Gender", gender)
                .checkResult("Mobile", phoneNumber)
                .checkResult("Date of Birth", expectedBirthDate)
                .checkResult("Subjects", subject)
                .checkResult("Hobbies", hobby)
                .checkResult("Picture", imgName)
                .checkResult("Address", address)
                .checkResult("State and City", expectedStateAndCity);
    }

    @ValueSource(strings = {
            "Maths",
            "Chemistry"
    })
    @ParameterizedTest
    void formTests1(String testData) {
        RegistrationFormPage registrationFormPage = new RegistrationFormPage();

        registrationFormPage.openPage()
                .setSubject(testData);

        $("#subjectsWrapper").shouldHave(text(testData));
    }

    static Stream<Arguments> formTests2() {
        return Stream.of(
                Arguments.of("Alex", "Egorov", "test1@qa.guru", "Male", "0001112223", "2000", "May", "17", "Maths", "adress1", "Sports", "NCR", "Delhi"),
                Arguments.of("Egor", "Alexov", "test2@qa.guru", "Other", "3332221110", "1980", "January", "01", "Maths", "adress2", "Sports", "NCR", "Delhi")
        );
    }

    @MethodSource
    @ParameterizedTest
    void formTests2(String firstName,
                    String lastName,
                    String email,
                    String gender,
                    String phoneNumber,
                    String year,
                    String month,
                    String day,
                    String subject,
                    String address,
                    String hobby,
                    String state,
                    String city) {
        String expectedFullName = format("%s %s", firstName, lastName);
        String expectedBirthDate = format("%s %s,%s", day, month, year);
        String expectedStateAndCity = format("%s %s", state, city);

        RegistrationFormPage registrationFormPage = new RegistrationFormPage();


        registrationFormPage.openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setGender(gender)
                .setPhoneNumber(phoneNumber)
                .setBirthDate(day, month, year)
                .setSubject(subject)
                .setAddress(address)
                .setHobby(hobby)
                .setPicture(imgPath)
                .setState(state)
                .setCity(city)
                .clickSubmit();


        registrationFormPage.checkTitleResult()
                .checkResult("Student Name", expectedFullName)
                .checkResult("Student Email", email)
                .checkResult("Gender", gender)
                .checkResult("Mobile", phoneNumber)
                .checkResult("Date of Birth", expectedBirthDate)
                .checkResult("Subjects", subject)
                .checkResult("Hobbies", hobby)
                .checkResult("Picture", imgName)
                .checkResult("Address", address)
                .checkResult("State and City", expectedStateAndCity);
    }
}
