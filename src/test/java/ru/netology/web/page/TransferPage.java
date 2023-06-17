package ru.netology.web.page;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $(withText("Пополнение карты"));
    private SelenideElement amountInput = $("[data-test-id='amount'] input");
    public SelenideElement fromCard = $("[data-test-id=from] input");
    public SelenideElement topUpButton = $("[data-test-id=action-transfer]");
    private SelenideElement cancelButton = $("[data-test-id='action-cancel']");
    private SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public TransferPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage setRefill(int amount, DataHelper.CardInfo card) {
        refill(amount, card);
        return new DashboardPage();
    }

    public void refill(int amount, DataHelper.CardInfo card) {
        amountInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        amountInput.setValue(String.valueOf(amount));
        fromCard.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        fromCard.setValue(card.getCardNumber());
        topUpButton.click();
    }

    public void setErrorNotification(String textError) {
        errorNotification.shouldBe(Condition.visible).shouldHave(Condition.text("Ошибка"), Duration.ofSeconds(15));
    }

    public void setCancelButton() {
        cancelButton.click();
    }
}