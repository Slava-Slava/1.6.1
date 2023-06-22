package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

class MoneyTransferTest {
  DashboardPage dashboardPage;

  @BeforeEach
  public void Authentication() {
    open("http://localhost:9999");
    var LoginPageV1 = new LoginPageV1();
    var autoInfo = DataHelper.getAuthInfo();
    var verificationPage = LoginPageV1.validLogin(autoInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(autoInfo);
    dashboardPage = verificationPage.validVerify(verificationCode);
  }

  @Test
  void transferFromCardToCard() {
    var oneCardNumber = getOneCardNumber();
    var twoCardNumber = getTwoCardNumber();
    var oneCardBalance = dashboardPage.getCardBalance(0);
    var twoCardBalance = dashboardPage.getCardBalance(1);
    var amount = 10000;
    var expectedBalanceOneCard = oneCardBalance - amount;
    var expectedBalanceTwoCard = twoCardBalance + amount;
    var transferPages = dashboardPage.selectCardToTransfer(twoCardNumber);
    dashboardPage = transferPages.setRefill(10000,getOneCardNumber());
    var actualBalanceOneCard = dashboardPage.getCardBalance(0);
    var actualBalanceTwoCard = dashboardPage.getCardBalance(1);
    assertEquals(expectedBalanceOneCard, actualBalanceOneCard);
    assertEquals(expectedBalanceTwoCard ,actualBalanceTwoCard);
  }
  @Test
  void cardBalanceChecksFromTheCardListPage() {
    var oneCardNumber = getOneCardNumber();
    var twoCardNumber = getTwoCardNumber();
    var oneCardBalance = dashboardPage.getCardBalance(0);
    var twoCardBalance = dashboardPage.getCardBalance(1);
    var amount = 20000;
    var transferPages = dashboardPage.selectCardToTransfer(oneCardNumber);
    transferPages.refill(20000,getTwoCardNumber());
    transferPages.setErrorNotification("Перевод не выполнен.Сумма перевода превышает баланс на карте");
    var actualBalanceOneCard = dashboardPage.getCardBalance(0);
    var actualBalanceTwoCard = dashboardPage.getCardBalance(1);
    assertEquals(oneCardBalance, actualBalanceOneCard);
    assertEquals(twoCardBalance, actualBalanceTwoCard);
  }
}

