package ru.netology.ibank.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.ibank.DataGenerator.Registration.getUser;
import static ru.netology.ibank.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.ibank.DataGenerator.getRandomLogin;
import static ru.netology.ibank.DataGenerator.getRandomPassword;

public class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");

    }

    @Test
    @DisplayName("Should successfully login")
    void shouldSuccessfulLoginWhenRegistered() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).should(Condition.visible);
    }

    @Test
    @DisplayName("Should not login when empty fields")
    void shouldNotLoginWhenEmpty() {
        $("button.button").click();
        $("[data-test-id='login'] .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"))
                .should(Condition.visible);
        $("[data-test-id='password'] .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"))
                .should(Condition.visible);
    }

    @Test
    @DisplayName("Should not login when not registered")
    void shouldNotLoginWhenNotRegistered() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .should(Condition.visible);
    }

    @Test
    @DisplayName("Should not login when blocked")
    void shouldNotLoginWhenBlocked() {
        var registeredUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"))
                .should(Condition.visible);
    }

    @Test
    @DisplayName("Should not login when active wrong login")
    void shouldNotLoginWhenRegisteredWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var randomLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(randomLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .should(Condition.visible);
    }

    @Test
    @DisplayName("Should not login when blocked wrong login")
    void shouldNotLoginWhenRegisteredBlockedWrongLogin() {
        var registeredUser = getRegisteredUser("blocked");
        var randomLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(randomLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .should(Condition.visible);
    }

    @Test
    @DisplayName("Should not login when wrong Password")
    void shouldNotLoginWhenRegisteredWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var randomPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(randomPassword);
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .should(Condition.visible);
    }

    @Test
    @DisplayName("Should not login when blocked wrong Password")
    void shouldNotLoginWhenRegisteredBlockedWrongPassword() {
        var registeredUser = getRegisteredUser("blocked");
        var randomPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(randomPassword);
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .should(Condition.visible);
    }
}