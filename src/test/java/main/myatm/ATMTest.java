package main.myatm;

import org.junit.Test;
import org.mockito.InOrder;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class ATMTest {
    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeMoneyInATMThrownIllegalArgumentException()  {
        new ATM(-5);
    }


    @Test
    public void testGetMoneyInATMExpectedEquals() {
        double actionMoney = 45;
        ATM atm = new ATM(actionMoney);
        double expectedResult = 45;
        assertEquals(atm.getMoneyInATM(), expectedResult, 0.0);
    }



    @Test
    public void testGetMoneyInATMExpectedNotEquals() {
        double actionMoney = 45;
        ATM atm = new ATM(actionMoney);
        double expectedResult = 46;
        assertNotEquals(atm.getMoneyInATM(), expectedResult, 0.0);
    }

    @Test (expected = NullPointerException.class)
    public void testValidateCardCardIsNullThrownIllegalArgumentException() {
        ATM atm = new ATM(5);
        atm.validateCard(null,5);
    }

    @Test
    public void testValidateCardBlockedCard() {
        ATM atm = new ATM(5);
        Card card = mock(Card.class);
        when(card.isBlocked()).thenReturn(true);
        boolean result = atm.validateCard(card,5);

        assertEquals(result,false);
    }
    @Test
     public void testValidateCardCardAccepted() {
        ATM atm = new ATM(5);
        Card card = mock(Card.class);
        int pinCode = 1111;
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
        boolean result = atm.validateCard(card,pinCode);

        assertEquals(result, true);
    }


    @Test (expected = NoCardInserted.class)
    public void testCheckBalanceCardIsNullThrownNoCardInsertion() throws NoCardInserted {
        ATM atm = new ATM(5);

        atm.checkBalance();

    }

    @Test
    public void testCheckBalanceExcpectedAllGood() throws NoCardInserted {
        ATM atm = new ATM(5);

        Card card = mock(Card.class);
        int pinCode = 1111;
        Account account = mock(Account.class);
        double actualValue = 1000;
        when(account.getBalance()).thenReturn(actualValue);
        when(card.getAccount()).thenReturn(account);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
         atm.validateCard(card,pinCode);
        double expectedResult = 1000;
            assertEquals(atm.checkBalance(),expectedResult,0.0);

    }

    @Test
      public void testCheckBalanceResultIsAnother() throws NoCardInserted {
        ATM atm = new ATM(5);

        Card card = mock(Card.class);
        int pinCode = 1111;
        Account account = mock(Account.class);
        double actualValue = 1000;
        when(account.getBalance()).thenReturn(actualValue);
        when(card.getAccount()).thenReturn(account);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
         atm.validateCard(card,pinCode);
        double expectedResult = 1001;
        assertNotEquals(atm.checkBalance(), expectedResult, 0.0);

    }

    @Test (expected = NoCardInserted.class)
      public void testGetCashCardIsNullThrownNoCardInserted() throws NoCardInserted, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {
        ATM atm = new ATM(1000);
        double amount =123;
        assertNull(atm.getCash(amount));

    }

    @Test (expected = NotEnoughMoneyInAccount.class)
    public void testGetCashThronwNotEnoughMoneyInAccount() throws NoCardInserted, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {

        double amount =1001;
        ATM atm = new ATM(5);

        Card card = mock(Card.class);
        int pinCode = 1111;
        Account account = mock(Account.class);
        double actualValue = 1000;
        when(account.getBalance()).thenReturn(actualValue);
        when(card.getAccount()).thenReturn(account);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
       atm.validateCard(card,pinCode);
        atm.getCash(amount);
    }

    @Test (expected = NotEnoughMoneyInATM.class)
    public void testGetCashThronwNotEnoughMoneyInATM() throws NoCardInserted, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {

        double amount =1001;
        ATM atm = new ATM(5);

        Card card = mock(Card.class);
        int pinCode = 1111;
        Account account = mock(Account.class);
        double actualValue = 1005;
        when(account.getBalance()).thenReturn(actualValue);
        when(card.getAccount()).thenReturn(account);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
         atm.validateCard(card,pinCode);
        atm.getCash(amount);
    }

    @Test
    public void testGetCashBalanceBalanceCheckedAtLeastOnce() throws NoCardInserted, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {

        double amount =1000;
        ATM atm = new ATM(10000);
        Card card = mock(Card.class);
        int pinCode = 1111;
        Account account = mock(Account.class);
        double actualValue = 10000;
        when(account.getBalance()).thenReturn(actualValue);
        when(card.getAccount()).thenReturn(account);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
         atm.validateCard(card,pinCode);
        atm.getCash(amount);
        verify(account, atLeastOnce()).getBalance();
    }

    @Test
    public void testGetCashBalanceBalanceOrderGetBalanceBeforeWithDraw() throws NoCardInserted, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {

        double amount =1000;
        ATM atm = new ATM(10000);
        Card card = mock(Card.class);
        int pinCode = 1111;
        Account account = mock(Account.class);
        double actualValue = 10000;
        when(account.getBalance()).thenReturn(actualValue);
        when(card.getAccount()).thenReturn(account);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
        atm.validateCard(card, pinCode);
        atm.getCash(amount);
        InOrder order = inOrder(account);
        order.verify(account).getBalance();
        order.verify(account).withdrow(amount);

    }




}