package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        assertFalse( BankAccount.isEmailValid(""));

        assertTrue(BankAccount.isEmailValid("letsgo@gmail.com"));
        assertFalse(BankAccount.isEmailValid("bad-dash-@gmail.com"));
        assertFalse(BankAccount.isEmailValid("nodomain@"));
        assertFalse(BankAccount.isEmailValid("@noprefix.com"));
        assertFalse(BankAccount.isEmailValid("two..dots@email"));
        assertFalse(BankAccount.isEmailValid("test@two..dots"));
        assertFalse(BankAccount.isEmailValid("hast#tag@nogood.com"));
        assertFalse(BankAccount.isEmailValid("pound@bad#domain.com"));
        assertFalse(BankAccount.isEmailValid(".start@dot.com"));
        assertFalse(BankAccount.isEmailValid("test@two..com"));
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}