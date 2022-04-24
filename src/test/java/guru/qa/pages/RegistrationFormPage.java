package guru.qa.pages;

import com.codeborne.selenide.SelenideElement;
import guru.qa.pages.components.CalendarComponent;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationFormPage {
    CalendarComponent calendar = new CalendarComponent();

    //locators
    SelenideElement StadeAndCityInput = $("#stateCity-wrapper");
    //actions
    public RegistrationFormPage openPage() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");

        return this;
    }

    public RegistrationFormPage setFirstName(String firstName) {
        $("#firstName").setValue(firstName);

        return this;
    }

    public RegistrationFormPage setLastName(String lastName) {
        $("#lastName").setValue(lastName);

        return this;
    }

    public RegistrationFormPage setEmail(String email) {
        $("#userEmail").setValue(email);

        return this;
    }

    public RegistrationFormPage setGender(String gender) {
        $("#genterWrapper").$(byText(gender)).click();

        return this;
    }

    public RegistrationFormPage setPhoneNumber(String phoneNumber) {
        $("#userNumber").setValue(phoneNumber);

        return this;
    }

    public RegistrationFormPage setBirthDate(String day, String month, String year) {
        $("#dateOfBirthInput").click();
        calendar.setDate(day, month, year);
        return this;
    }

    public RegistrationFormPage setSubject(String subject) {
        $("#subjectsInput").setValue(subject).pressEnter();

        return this;
    }

    public RegistrationFormPage setAddress(String address) {
        $("#currentAddress").setValue(address);

        return this;
    }

    public RegistrationFormPage setHobby(String hobby) {
        $("#hobbiesWrapper").$(byText(hobby)).click();

        return this;
    }

    public RegistrationFormPage setPicture(String imgPath) {
        $("#uploadPicture").uploadFromClasspath(imgPath);

        return this;
    }

    public RegistrationFormPage setState(String state) {
        $("#state").click();
        StadeAndCityInput.$(byText(state)).click();

        return this;
    }

    public RegistrationFormPage setCity(String city) {
        $("#city").click();
        StadeAndCityInput.$(byText(city)).click();

        return this;
    }
    public RegistrationFormPage clickSubmit() {
        $("#submit").click();

        return this;
    }

    public RegistrationFormPage checkTitleResult () {
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));

        return this;
    }
    public RegistrationFormPage checkResult(String key, String value) {
        $(".table-responsive").$(byText(key)).
                parent().shouldHave(text(value));

        return this;
    }
}
