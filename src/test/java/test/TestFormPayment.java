package test;

import com.codeborne.selenide.Configuration;
import data.DataHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.MainPage;

import static com.codeborne.selenide.Selenide.clearBrowserLocalStorage;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.devtools.v102.network.Network.clearBrowserCookies;

public class TestFormPayment {

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
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
}


