package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DBUtils;
import data.DataHelper;
import io.qameta.allure.model.Status;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;

import static com.codeborne.selenide.Selenide.clearBrowserLocalStorage;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.devtools.v102.network.Network.clearBrowserCookies;

public class TestFormPayment {

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
        open("http://localhost:8080");
        DBUtils.cleanTable();
        Configuration.holdBrowserOpen = true;
    }


    @AfterEach
    void memoryClear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Test
    void shouldGetPaymentPage() {
        var mainPage = new MainPage();
        mainPage.payByCard();
    }

    @Test
    void shouldPayByCardSuccessfully() {
        var cardInfo = DataHelper.giveValidDataOfTheApprovedCard();
        var mainPage = new MainPage();
        var paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.successfulPayment();
    }

    @Test
    void shouldRefuseToPerformTheOperation() {
        var cardInfo = DataHelper.giveValidDataOfTheDeclinedCard();
        var mainPage = new MainPage();
        var paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.declinedPayment();
    }

    @Test
    void shouldNotSubmitEmptyFields() {
        var cardInfo = DataHelper.giveEmptyFields();
        var mainPage = new MainPage();
        var paymentPage = mainPage.payByCard();
        paymentPage.notFilledForm();
    }

    @Test
    void shouldNotAcceptNonValidValuesOnAnApprovedCard() {
        var cardInfo = DataHelper.giveInvalidDataOfTheApprovedCard();
        var mainPage = new MainPage();
        var paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
        paymentPage.ownerErrorVisible();
        paymentPage.cvcErrorVisible();
    }

    @Test
    void shouldNotAcceptNonValidValuesOnAnDeclinedCard() {
        var cardInfo = DataHelper.giveInvalidDataOfTheDeclinedCard();
        var mainPage = new MainPage();
        var paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
        paymentPage.ownerErrorVisible();
        paymentPage.cvcErrorVisible();
    }

    @Test
    void shouldNotApproveTheOperationWithAnUnregisteredCard() {
        var cardInfo = DataHelper.giveAnyCardNumber();
        var mainPage = new MainPage();
        var paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.declinedPayment();
    }

    @Test
    void mustNotPassExpired(){
        var cardInfo = DataHelper.giveValidDataOfTheApprovedCardExpired();
        var mainPage = new MainPage();
        var paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.expiredCardErrorVisible();
    }

    @Test
    void allFieldsInvalidData() {
        var cardInfo = DataHelper.allFieldsAreInvalidData();
        var mainPage = new MainPage();
        var paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberErrorVisible();
        paymentPage.monthErrorVisible();
        paymentPage.yearErrorVisible();
        paymentPage.ownerErrorVisible();
        paymentPage.cvcErrorVisible();
    }

    //Запрос в бд
    @Test
    void shouldPayByCardSuccessfully2() {
        var cardInfo = DataHelper.giveValidDataOfTheApprovedCard();
        var mainPage = new MainPage();
        var paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.successfulPayment();
        assertEquals("APPROVED",DBUtils.getPaymentStatus());
    }

    @Test
    void shouldRefuseToPerformTheOperation2() {
        var cardInfo = DataHelper.giveValidDataOfTheDeclinedCard();
        var mainPage = new MainPage();
        var paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.declinedPayment();
        assertEquals("DECLINED",DBUtils.getPaymentStatus());
    }
}


