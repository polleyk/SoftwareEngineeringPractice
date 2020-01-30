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
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }

        if (isAmountValid(startingBalance)) {
            this.balance = startingBalance;
        } else {
            throw new IllegalArgumentException("Starting balance: " + startingBalance + "is invalid, cannot create account");
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
    public void withdraw (double amount) throws InsufficientFundsException, IllegalArgumentException {
        if (isAmountValid(amount)) {
            if (amount <= balance) {
                balance = balance - amount;
            } else {
                throw new InsufficientFundsException("Insufficient funds");
            }
        } else {
            throw new IllegalArgumentException("Invalid amount");
        }
    }


    /**
     * @post increases the balance by amount if amount is valid
     * @param amount to be deposited
     * @throws IllegalArgumentException if amount is negative or decimal has more than 2 sig. digits
     */
    public void deposit (double amount) throws IllegalArgumentException {
        if (isAmountValid(amount)) {
            balance += amount;
        } else {
            throw new IllegalArgumentException("Invalid amount");
        }
    }

    /**
     * @post this account balance is reduced by amount, passed account balance increased
     * @param account the account to transfer the money into
     * @param amount the amount of money to transfer
     * @throws InsufficientFundsException if amount is greater than balance
     * @throws IllegalArgumentException if amount is invalid
     */
    public void transfer (BankAccount account, double amount) throws InsufficientFundsException, IllegalArgumentException {

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

        //no negatives
        if (amount < 0) {
            return false;
        }

        String amount_string = String.valueOf(amount);
        int length = amount_string.length();
        int decimalIndex = amount_string.indexOf('.');

        //scientific notation handling
        int eIndex = amount_string.indexOf('E');

        if (eIndex != -1) {
            if (amount_string.contains("-")) {
                return false;
            }
            int exponent = Integer.parseInt(amount_string.substring(eIndex+1, length));
            if (eIndex - decimalIndex > exponent+3) {
                return false;
            }
            return true;
        }

        //check number decimals, non exponent
        int lastIndex = length-1;
        int decimals = lastIndex - decimalIndex;
        if (decimals > 2) {
            return false;
        }
        return true;

    }
}
