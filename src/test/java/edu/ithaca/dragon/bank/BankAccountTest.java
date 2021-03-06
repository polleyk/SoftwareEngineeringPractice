package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        //equivalence class: has balance of some sort
        //border case: balance = 0
        BankAccount bankAccount = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount.getBalance());
        //middle case
        bankAccount = new BankAccount("a@b.com", 500);
        assertEquals(500, bankAccount.getBalance());
        //border case: many monies
        bankAccount = new BankAccount("a@b.com", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException, IllegalArgumentException {
        BankAccount bankAccount = new BankAccount("a@b.com", 500);

        //withdraw 0
        bankAccount.withdraw(0);
        assertEquals(500, bankAccount.getBalance());

        //withdraw negative amount
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-1.01)); //border
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100.5)); //middle
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100000.99)); //border

        //withdraw amount >2 decimals
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0.001)); //border
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0.50505)); //middle
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0.888888888)); //border

        //withdraw negative amount >2 decimals
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-0.001)); //border
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100.50505)); //middle
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100000.888888888)); //border

        //withdraw more than in account
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(501)); //border
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(1000)); //middle
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(1000000)); //border

        //withdraw less than or equal to amount in bank
        bankAccount.withdraw(1.01); //border
        assertEquals(498.99, bankAccount.getBalance());
        bankAccount.withdraw(198.99); //middle
        assertEquals(300, bankAccount.getBalance());
        bankAccount.withdraw(300); //border
        assertEquals(0, bankAccount.getBalance());
    }

    @Test
    void depositTest() throws IllegalArgumentException {
        BankAccount bankAccount = new BankAccount("a@b.com", 500);

        //deposit 0
        bankAccount.deposit(0);
        assertEquals(500, bankAccount.getBalance());

        //deposit negative amount
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-1.01)); //border
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-100.5)); //middle
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-100000.99)); //border

        //deposit amount >2 decimals
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0.001)); //border
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0.50505)); //middle
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0.888888888)); //border

        //deposit negative amount >2 decimals
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-0.001)); //border
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-100.50505)); //middle
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-100000.888888888)); //border

        //deposit positive amount
        bankAccount.deposit(0.01); //border
        assertEquals(500.01, bankAccount.getBalance());
        bankAccount.deposit(100.5); //middle
        assertEquals(600.51, bankAccount.getBalance());
        bankAccount.deposit(100000); //border
        assertEquals(100600.51, bankAccount.getBalance());

        //deposit valid amount into empty account
        BankAccount bankAccount2 = new BankAccount("c@d.com", 0);
        bankAccount2.deposit(0.01); //border
        assertEquals(0.01, bankAccount2.getBalance());
        bankAccount2.deposit(100.5); //middle
        assertEquals(100.51, bankAccount2.getBalance());
        bankAccount2.deposit(100000); //border
        assertEquals(100100.51, bankAccount2.getBalance());
    }

    @Test
    void transferTest() throws IllegalArgumentException, InsufficientFundsException {
        BankAccount acct1 = new BankAccount("a@b.com", 500);
        BankAccount acct2 = new BankAccount("c@d.com", 0);

        //transfer 0
        acct1.transfer(acct2,0);
        assertEquals(500, acct1.getBalance());
        assertEquals(0, acct2.getBalance());

        //transfer negative amount
        assertThrows(IllegalArgumentException.class, () -> acct1.transfer(acct2,-1.01)); //border
        assertThrows(IllegalArgumentException.class, () ->acct1.transfer(acct2,-100.5)); //middle
        assertThrows(IllegalArgumentException.class, () -> acct1.transfer(acct2,-100000.99)); //border

        //transfer amount >2 decimals
        assertThrows(IllegalArgumentException.class, () -> acct1.transfer(acct2,0.001)); //border
        assertThrows(IllegalArgumentException.class, () -> acct1.transfer(acct2,0.50505)); //middle
        assertThrows(IllegalArgumentException.class, () -> acct1.transfer(acct2,0.888888888)); //border

        //transfer negative amount >2 decimals
        assertThrows(IllegalArgumentException.class, () -> acct1.transfer(acct2,-0.001)); //border
        assertThrows(IllegalArgumentException.class, () -> acct1.transfer(acct2,-100.50505)); //middle
        assertThrows(IllegalArgumentException.class, () -> acct1.transfer(acct2,-100000.888888888)); //border

        //transfer more than in account
        assertThrows(InsufficientFundsException.class, () -> acct2.transfer(acct1, 0.01)); //border
        assertThrows(InsufficientFundsException.class, () -> acct1.transfer(acct2, 500.5)); //middle
        assertThrows(InsufficientFundsException.class, () -> acct1.transfer(acct2, 1000000)); //border

        //transfer less than or equal to amount in bank
        acct1.transfer(acct2, 0.01); //border
        assertEquals(499.99, acct1.getBalance());
        assertEquals(0.01, acct2.getBalance());

        acct2.transfer(acct1, 0.01); //border
        assertEquals(500, acct1.getBalance());
        assertEquals(0, acct2.getBalance());

        acct1.transfer(acct2, 500); //border
        assertEquals(0, acct1.getBalance());
        assertEquals(500, acct2.getBalance());

        acct2.transfer(acct1, 100.5); //middle
        assertEquals(100.50, acct1.getBalance());
        assertEquals(399.50, acct2.getBalance());
    }

    @Test
    void isEmailValidTest(){
        //sorry there are many

        //test @ symbols
        //class 1: 0 @ symbols
        assertFalse(BankAccount.isEmailValid("")); //border case
        assertFalse(BankAccount.isEmailValid("noatsign")); //middle case
        //class 2: @ symbol at end
        assertFalse(BankAccount.isEmailValid("@")); //border case
        assertFalse(BankAccount.isEmailValid("@noprefix"));
        assertFalse(BankAccount.isEmailValid("nodomain@"));
        //class 3: @ symbol in middle
        assertTrue(BankAccount.isEmailValid("a@b.com")); //border case - small email
        assertTrue(BankAccount.isEmailValid("prefix@domain.com")); //middle case
        //class 4: many @ symbols
        assertFalse(BankAccount.isEmailValid("too@many@ats")); //border case: 2 @s
        assertFalse(BankAccount.isEmailValid("t@o@o@m@a@n@y")); //border case: lots of @s
        assertFalse(BankAccount.isEmailValid("prefix@domain@extra@com")); //middle case

        //test # symbols
        //class 1: 0 #s
        assertTrue(BankAccount.isEmailValid("nopounds@good.email")); //border and middle case?
        //class 2: any #s
        assertFalse(BankAccount.isEmailValid("a#pound@bad.email")); //border case: 1#
        assertFalse(BankAccount.isEmailValid("bad@po#und.email"));
        assertFalse(BankAccount.isEmailValid("p#o#u#n#d#s#@b#a#d.ema#il")); //border case
        assertFalse(BankAccount.isEmailValid("two#pounds@bad.e#mail")); //middle case

        //test . symbols (should hold same for -)
        //class 1: no dots!
        assertTrue(BankAccount.isEmailValid("nodots@is.fine")); //is there another case idk
        //class 2: dot between good chars (nums or letters)
        assertTrue(BankAccount.isEmailValid("good.dot@good.email")); //border case: 1 dot
        assertTrue(BankAccount.isEmailValid("l.o.t.s.o.f.d.o.t.s@fine.email")); //border case
        assertTrue(BankAccount.isEmailValid("some.dots.are.good@fine-good.email")); //middle case
        //class 3: dot not surrounded by nums or letters
        assertFalse(BankAccount.isEmailValid(".bad@bad.email")); //border cases: dot at ends
        assertFalse(BankAccount.isEmailValid("bad.@bad.email"));
        assertFalse(BankAccount.isEmailValid("nogood@.bad.email"));
        assertFalse(BankAccount.isEmailValid("double..dots@bad.email")); //middle cases: dots by
        assertFalse(BankAccount.isEmailValid("double.dots@bad..email"));

        //test domain valid dot things
        //class 1: dot at least 2 chars after
        assertTrue(BankAccount.isEmailValid("good@good.go")); //border case: only 2
        assertTrue(BankAccount.isEmailValid("good@good.holycrapthisislong")); //border: lots and lots
        assertTrue(BankAccount.isEmailValid("good@good.com")); //middle case
        //class 2: too few chars after dot
        assertFalse(BankAccount.isEmailValid("bad@bad.b")); //border: only 1
        assertFalse(BankAccount.isEmailValid("nodot@bad.")); //other border: nothing after dot
        //class 3: no dot!
        assertFalse(BankAccount.isEmailValid("nodot@bad")); //this is kind of it my dudes

    }

    @Test
    void isAmountValidTest() {

        //class: negative numbers, 0 decimals
        assertFalse(BankAccount.isAmountValid(-1)); //border
        assertFalse(BankAccount.isAmountValid(-100000)); //border
        assertFalse(BankAccount.isAmountValid(-100)); //middle

        //class: negative numbers,  1-2 decimals
        assertFalse(BankAccount.isAmountValid(-0.01)); //border
        assertFalse(BankAccount.isAmountValid(-100000.90)); //border
        assertFalse(BankAccount.isAmountValid(-100.5)); //middle

        //class: negative numbers, >2 decimals
        assertFalse(BankAccount.isAmountValid(-0.0000001)); //border
        assertFalse(BankAccount.isAmountValid(-1000.9999999999)); //border
        assertFalse(BankAccount.isAmountValid(-1.505)); //middle

        //class: positive numbers, 0 decimals
        assertTrue(BankAccount.isAmountValid(0)); //border
        assertTrue(BankAccount.isAmountValid(10000000)); //border
        assertTrue(BankAccount.isAmountValid(500)); //middle

        //class: positive numbers, 1-2 decimals
        assertTrue(BankAccount.isAmountValid(0.01)); //border
        assertTrue(BankAccount.isAmountValid(100000.99)); //border
        assertTrue(BankAccount.isAmountValid(500.5)); //middle

        //class: positive numbers, >2 decimals
        assertFalse(BankAccount.isAmountValid(0.000001)); //border
        assertFalse(BankAccount.isAmountValid(10000000.8888888888)); //border
        assertFalse(BankAccount.isAmountValid(1.505)); //middle
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        //bad email and bad balance
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", -100.005));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", -100.50));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100.005));

        //bad balance - negative
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -0.01));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -10));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100));

        //bad balance - too many decimals
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 0.0001));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 100.50505));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 1000000.888888888));

        //bad balance - both
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -0.0001));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100.50505));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -1000000.888888888));
    }

}