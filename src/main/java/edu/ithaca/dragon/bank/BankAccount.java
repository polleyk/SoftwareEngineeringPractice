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

        if (email.indexOf('@') == -1 || email.indexOf('@') == length -1 || email.indexOf('@') == 0 ) {
            return false;
        }
        if (email.split("@").length > 2) {
            return false;
        }
        if (email.contains("#")) {
            return false;
        }
        if (email.indexOf('.') == -1 ||email.indexOf('.') == 0 || email.indexOf('.') == length -1 ||
                email.contains(".@") || email.contains("@.") || email.contains("..")) {
            return false;
        }
        if (email.indexOf('-') == 0 || email.indexOf('-') == length -1 ||
                email.contains("-@") || email.contains("@-") || email.contains("--")) {
            return false;
        }
        if (email.indexOf('.') > length - 3) {
            return false;
        }
        return true;
    }

    /**
     * makes sure money amount is valid
     * @param amount
     * @return true if positive and 2 or fewer decimal places, false otherwise
     */
    public static boolean isAmountValid(double amount) {
        return false;
    }
}
