package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private SelenideElement paymentButton = $(byText("Купить"));
    private SelenideElement paymentByCard = $(byText("Оплата по карте"));

    public PaymentPage payByCard () {
        paymentButton.click();
        paymentByCard.shouldBe(visible);
        return new PaymentPage();
    }

}
