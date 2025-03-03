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

public class DeliveryTest {

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
    }

    @Test
    @DisplayName("1. Успешное планирование и перепланирование встречи")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        int daysToAddForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        int daysToAddForSecondMeeting = 7;
        String secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        fillForm(validUser, firstMeetingDate);
        verifySuccessNotification(firstMeetingDate);

        replanMeeting(secondMeetingDate);
        verifyReplanNotification();
        confirmReplan();
        verifySuccessNotification(secondMeetingDate);
    }

    private void confirmReplan() {
    }

    private void verifyReplanNotification() {
    }

    private void replanMeeting(String secondMeetingDate) {
    }

    private void verifySuccessNotification(String firstMeetingDate) {
    }

    private void fillForm(DataGenerator.UserInfo validUser, String firstMeetingDate) {
    }

    @Test
    @DisplayName("2. Пустой номер телефона")
    void shouldShowErrorWhenPhoneIsEmpty() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        String meetingDate = DataGenerator.generateDate(4);

        fillFormWithoutPhone(validUser, meetingDate);
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    private void fillFormWithoutPhone(DataGenerator.UserInfo validUser, String meetingDate) {
    }

    @Test
    @DisplayName("3. Пустое имя")
    void shouldShowErrorWhenNameIsEmpty() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        String meetingDate = DataGenerator.generateDate(4);

        fillFormWithoutName(validUser, meetingDate);
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    private void fillFormWithoutName(DataGenerator.UserInfo validUser, String meetingDate) {
    }

    @Test
    @DisplayName("4. Пустой город")
    void shouldShowErrorWhenCityIsEmpty() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        String meetingDate = DataGenerator.generateDate(4);

        fillFormWithoutCity(validUser, meetingDate);
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    private void fillFormWithoutCity(DataGenerator.UserInfo validUser, String meetingDate) {
    }

    @Test
    @DisplayName("5. Использование буквы Ё в имени")
    void shouldAcceptNameWithYoLetter() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        String meetingDate = DataGenerator.generateDate(4);
        String nameWithYo = validUser.getName() + " Семёнов";

        fillFormWithCustomName(validUser, meetingDate, nameWithYo);
        verifySuccessNotification(meetingDate);
    }

    private void fillFormWithCustomName(DataGenerator.UserInfo validUser, String meetingDate, String nameWithYo) {
    }

    @Test
    @DisplayName("6. Дата меньше 3 дней от текущей")
    void shouldShowErrorForDateLessThan3Days() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        String invalidDate = DataGenerator.generateDate(1);

        fillForm(validUser, invalidDate);
        $("[data-test-id='date'] .input_invalid .input__sub")
                .shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    @DisplayName("7. Недоступный город (латиница)")
    void shouldShowErrorForNonServiceCity() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        String meetingDate = DataGenerator.generateDate(7);
        String invalidCity = "Moscow";

        fillFormWithCustomCity(validUser, meetingDate, invalidCity);
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    private void fillFormWithCustomCity(DataGenerator.UserInfo validUser, String meetingDate, String invalidCity) {
    }

    @Test
    @DisplayName("8. Имя с использованием латиницы")
    void shouldShowErrorForNameWithLatinLetters() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        String meetingDate = DataGenerator.generateDate(7);
        String invalidName = DataGenerator.generateName("en");

        fillFormWithCustomName(validUser, meetingDate, invalidName);
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    @DisplayName("9. Неактивный чекбокс соглашения")
    void shouldShowErrorWhenCheckboxNotChecked() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        String meetingDate = DataGenerator.generateDate(7);

        fillFormWithoutAgreement(validUser, meetingDate);
        $("[data-test-id='agreement'].input_invalid")
                .shouldBe(Condition.visible);
    }

    private void fillFormWithoutAgreement(DataGenerator.UserInfo validUser, String meetingDate) {
    }

    @Test
    @DisplayName("10. Неверный формат телефона")
    void shouldShowErrorForInvalidPhoneFormat() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        String meetingDate = DataGenerator.generateDate(7);
        String invalidPhone = "12345";

        fillFormWithCustomPhone(validUser, meetingDate, invalidPhone);
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Неверный формат номера мобильного телефона"));
    }

    private void fillFormWithCustomPhone(DataGenerator.UserInfo validUser, String meetingDate, String invalidPhone) {
    }

}