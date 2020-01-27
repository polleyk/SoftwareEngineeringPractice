package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException {
        if (amount < 0) {
            return;
        }

        if (amount <= balance) {
            balance = balance - amount;
        }
        else {
            throw new InsufficientFundsException("Insufficient funds");
        }
    }


    public static boolean isEmailValid(String email){

        int length = email.length();
        int atIndex = email.indexOf('@');

        if (atIndex == -1 || atIndex == 0 || atIndex == length-1) {
            return false;
        }

        int dot = email.lastIndexOf('.');
        if (atIndex > dot || dot > email.length() - 3){
            return false;
        }

        String[] emailSplit = email.split("@");
        for (int emailPiece = 0; emailPiece < emailSplit.length; emailPiece++) {

            String currPiece = emailSplit[emailPiece];
            for (int charIndex = 0; charIndex < currPiece.length(); charIndex++) {
                char currChar = currPiece.charAt(charIndex);

                Boolean valid = false;

                if (currChar == 45 || currChar == 46 || currChar == 95 ) {
                    valid = true;
                    if (charIndex == 0 || charIndex == currPiece.length() - 1) {
                        valid = false;
                    } else {
                        char left = currPiece.charAt(charIndex - 1);
                        char right = currPiece.charAt(charIndex + 1);
                        if (left == 45 || left == 46 || left == 95 || right == 45 || right == 46 || right == 95) {
                            valid = false;
                        }
                    }

                }
                else if (currChar >= 48 && currChar <= 57) {
                    valid = true;
                }
                else if (currChar >= 65 && currChar <= 90) {
                    valid = true;
                }
                else if (currChar >= 97 && currChar <= 122) {
                    valid = true;
                }

                if (!valid) {
                    return false;
                }
            }
        }

        return true;
    }
}
