package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import ru.netology.delivery.test.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


class DeliveryTest {


    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Город']").setValue(validUser.getCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").setValue(validUser.getName());
        $("[name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[type='button'] .button__text").click();
        $(".notification__title").shouldBe(Condition.visible, Duration.ofSeconds(4));
        $("[data-test-id='success-notification'] .notification__content").should(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(secondMeetingDate);
        $("[type='button'] .button__text").click();
        $("[data-test-id='replan-notification'] .notification__title").should(Condition.exactText("Необходимо подтверждение"));
        $("[data-test-id='replan-notification'] span.button__text").click();
        $("[data-test-id='success-notification'] .notification__content").should(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }


    @Test
    @DisplayName("Should Test blank name")
    void shouldTestBlankName() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 800;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Город']").setValue(validUser.getCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[type='button'] .button__text").click();
        $("[data-test-id='name'].input_invalid .input__sub").should(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Should Test Blank City field")
    void shouldTestBlankCity() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Город']").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").setValue(validUser.getName());
        $("[name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[type='button'] .button__text").click();
        $("[data-test-id='city'].input_invalid .input__sub").should(Condition.exactText("Поле обязательно для заполнения"));
    }


    @Test
    @DisplayName("Should Test Unavailable First Date")
    void shouldTestDateUnavailable() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 1;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 0;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Город']").val(validUser.getCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").val(validUser.getName());
        $("[name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[type='button'] .button__text").click();
        $("[data-test-id='date'] .input_invalid .input__sub").should(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    @DisplayName("Should Test Unavailable second Date")
    void shouldTestSecondDateUnavailable() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 5;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 1;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Город']").val(validUser.getCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").val(validUser.getName());
        $("[name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[type='button'] .button__text").click();
        $(".notification__title").shouldBe(Condition.visible, Duration.ofSeconds(4));
        $("[data-test-id='success-notification'] .notification__content").should(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(secondMeetingDate);
        $("[type='button'] .button__text").click();
        $("[data-test-id='date'] .input_invalid .input__sub").should(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    @DisplayName("Should Test Unavailable zero Date")
    void shouldTestZeroDate() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 0;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 0;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Город']").val(validUser.getCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").val(validUser.getName());
        $("[name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[type='button'] .button__text").click();
        $("[data-test-id='date'] .input_invalid .input__sub").should(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    @DisplayName("Should Test Unavailable City")
    void shouldTestEnglishCity() {
        var validUser = DataGenerator.Registration.generateUser("en");
        var daysToAddForFirstMeeting = 90;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 120;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Город']").val("Вашингтон");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").val(validUser.getName());
        $("[name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[type='button'] .button__text").click();
        $("[data-test-id='city'].input_invalid .input__sub").should(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    @DisplayName("Should Test City with Symbols")
    void shouldTestCityWithSymbols() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 90;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 120;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Город']").val(validUser.getCity() + "!@#");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").val(validUser.getName());
        $("[name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[type='button'] .button__text").click();
        $("[data-test-id='city'].input_invalid .input__sub").should(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    @DisplayName("Should Test City with Numbers")
    void shouldTestCityWithNum() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 90;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 120;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Город']").val(validUser.getCity() + "123.0");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").val(validUser.getName());
        $("[name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[type='button'] .button__text").click();
        $("[data-test-id='city'].input_invalid .input__sub").should(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    @DisplayName("Should Test City with blanks")
    void shouldTestCityWithBlanks() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 90;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 120;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Город']").val("  " + validUser.getCity() + "  ");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").val(validUser.getName());
        $("[name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[type='button'] .button__text").click();
        $("[data-test-id='city'].input_invalid .input__sub").should(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    @DisplayName("Should Test Unavailable Name in English")
    void shouldTestEnglishName() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 90;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 120;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Город']").val(validUser.getCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").val(DataGenerator.generateName("en"));
        $("[name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[type='button'] .button__text").click();
        $("[data-test-id='name'].input_invalid .input__sub").should(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }


    @Test
    @DisplayName("Should Test Unavailable Name with Symbols")
    void shouldTestNameWithSymbols() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 90;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 120;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Город']").val(validUser.getCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").val("$^&" + validUser.getName() + ")(%!");
        $("[name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[type='button'] .button__text").click();
        $("[data-test-id='name'].input_invalid .input__sub").should(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    @DisplayName("Should Test Unavailable Name with Numbers")
    void shouldTestNameWithSpaces() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 90;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 300;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Город']").val(validUser.getCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").val("0.1" + validUser.getName() + "55");
        $("[name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[type='button'] .button__text").click();
        SelenideElement cityError = $("[data-test-id='city'].input_invalid .input__sub");
        $("[data-test-id='name'].input_invalid .input__sub").should(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    @DisplayName("Should Test other Phone")
    void shouldTestOtherPhoneNum() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 90;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 120;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Город']").val(validUser.getCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input.input__control").val(validUser.getName());
        $("[name='phone']").setValue(DataGenerator.generatePhone("cn"));
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[type='button'] .button__text").click();
        SelenideElement cityError = $("[data-test-id='city'].input_invalid .input__sub");
        SelenideElement nameError = $("[data-test-id='name'] input.input__control");
        $(".notification__title").shouldBe(Condition.visible, Duration.ofSeconds(4));
        $("[data-test-id='success-notification'] .notification__content").should(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(secondMeetingDate);
        $("[type='button'] .button__text").click();
        $("[data-test-id='replan-notification'] .notification__title").should(Condition.exactText("Необходимо подтверждение"));
        $("[data-test-id='replan-notification'] span.button__text").click();
        $("[data-test-id='success-notification'] .notification__content").should(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }
}