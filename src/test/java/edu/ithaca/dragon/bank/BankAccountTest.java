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

        assertTrue(BankAccount.isEmailValid("letsgo@gmail.com")); // equivalence class: all valid    boarder case: no
        assertFalse(BankAccount.isEmailValid("bad-dash-@gmail.com")); // equivalence class: prefix    boarder case: no
        assertFalse(BankAccount.isEmailValid("nodomain@")); // equivalence class: domain     boarder case: yes
        assertFalse(BankAccount.isEmailValid("@noprefix.com")); // equivalence class: prefix       boarder case: yes
        assertFalse(BankAccount.isEmailValid("two..dots@email.com")); // equivalence class: prefix    boarder case: yes
        assertFalse(BankAccount.isEmailValid("test@two..dots")); // equivalence class: domain     boarder case: yes
        assertFalse(BankAccount.isEmailValid("hast#tag@nogood.com")); // equivalence class: prefix    boarder case: no
        assertFalse(BankAccount.isEmailValid("pound@bad#domain.com")); // equivalence class: domain   boarder case: no
        assertFalse(BankAccount.isEmailValid(".start@dot.com")); // equivalence class: prefix     boarder case: no
        assertFalse(BankAccount.isEmailValid("test@two..com")); // equivalence class: domain    boarder case: yes

        // not present: equivalence class - both invalid not present, boarder case - multiple "@" case not present
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