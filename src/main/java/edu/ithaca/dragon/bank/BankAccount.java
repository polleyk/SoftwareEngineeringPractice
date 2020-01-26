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
        if (amount <= balance) {
            balance -= amount;
        } else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        int length = email.length();
        int currIndex = email.indexOf('@');

        if (currIndex == -1) {
            return false;
        }
        else if (currIndex == 0) {
            return false;
        }
        else if (currIndex == length - 1) {
            return false;
        }

        int dot = email.lastIndexOf('.');
        if (currIndex > dot) {
            return false;
        }
        else if (currIndex > email.length() - 3) {
            return false;
        }

        String[] emailSplit = email.split("@");
        for (int currEmail = 0; currEmail < emailSplit.length; currEmail++) {

            String currPiece = emailSplit[currEmail];
            for (int charIndex = 0; charIndex < currPiece.length(); charIndex++) {
                char currChar = currPiece.charAt(charIndex);

                Boolean isValid = false;

                if (currChar == 45 || currChar == 46 || currChar == 95) {
                    isValid = true;
                    if (charIndex == 0 || charIndex == currPiece.length() - 1) {
                        isValid = false;
                    } else {
                        char left = currPiece.charAt(charIndex - 1);
                        char right = currPiece.charAt(charIndex + 1);
                        if (left == 45 || left == 46 || left == 95 || right == 45 || right == 46 || right == 95) {
                            isValid = false;
                        }
                    }

                }
                else if (currChar >= 48 && currChar <= 57) {
                    isValid = true;
                }
                else if (currChar >= 65 && currChar <= 90) {
                    isValid = true;
                }
                else if (currChar >= 97 && currChar <= 122) {
                    isValid = true;
                }

                if (!isValid) {
                    return false;
                }
            }
        }
        return true;
    }
}
