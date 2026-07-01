package com.ibm.ejpmercado.mercado_ervin_java;

import java.util.Random;

// RECORD 
record Transaction(int transactionID, String paymentMethod, boolean isSuccessful) {
    final void showTransaction () {
        System.out.println("Transaction ID: " + transactionID + " Payment Method: " + paymentMethod + " Success: " + isSuccessful);
        System.out.println("");
    }
}

// INTERFACE
interface PaymentValidator {
    // Abstraction: every payment method must validate but its process of doing so remain hidden.
    boolean validatePaymentMethod();
}

// ABSTRACT + SEALED CLASS
abstract sealed class PaymentMethod permits PayPalPayment, BankTransferPayment, CreditCardPayment {
    private final int amount;
    // Encapsulation: preventing outside access from variables that shouldn't be accessed outside

    // Polymorphism: this function returns different Strings depending on the payment method.
    abstract String getPaymentType();

    public PaymentMethod(int amount) {
        this.amount = amount;
    }

    final Transaction processPayment() {
        // Inheritance: this reusable method allows for a repeated use on different subclasses of Payment Method
        boolean isValid = ((PaymentValidator) this).validatePaymentMethod();
        int id = new Random().nextInt();
        return new Transaction(id, getPaymentType(), isValid);

    }
}

// CONCRETE SUBCLASS
final class CreditCardPayment extends PaymentMethod implements PaymentValidator {

    private final String cardNumber;

    public CreditCardPayment(int amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    @Override
    String getPaymentType() {
        return "Credit Card";
    }

    @Override
    public boolean validatePaymentMethod() {
        System.out.println("Validating credit card...");
        return (this.cardNumber.length() == 16) ? true : false;
    }

}

// CONCRETE SUBCLASS
final class PayPalPayment extends PaymentMethod implements PaymentValidator {
    private final String email;

    PayPalPayment(int amount, String email) {
        super(amount);
        this.email = email;
    }

    boolean emailContainsAtSymbol() {
        return (this.email.contains("@")) ? true : false;
    }

    @Override
    String getPaymentType() {
        return "PayPal";
    }

    @Override
    public boolean validatePaymentMethod() {
        System.out.println("Validating PayPal...");
        return (emailContainsAtSymbol()) ? true : false;
    }

}

// NON-SEALED CONCRETE SUBCLASS (non-sealed mainly because other banks should be
// able to extend this)
non-sealed class BankTransferPayment extends PaymentMethod implements PaymentValidator {
    private final String accountNumber;

    BankTransferPayment(int amount, String accountNumber) {
        super(amount);
        this.accountNumber = accountNumber;
    }

    int getAccountNumberLength() {
        return this.accountNumber.length();
    }

    @Override
    String getPaymentType() {
        return "Bank Transfer";
    }

    @Override
    public boolean validatePaymentMethod() {
        System.out.println("Validating bank account...");
        return (getAccountNumberLength() == 10) ? true : false;
    }

}

class Day3 {

    public static void main(String[] args) {
        PaymentMethod creditCard = new CreditCardPayment(1000, "1234123412341234");
        PaymentMethod payPal = new PayPalPayment(1000, "ervinmercado31gmail.com");
        PaymentMethod BPI = new BankTransferPayment(1000, "1234561234");


        creditCard.processPayment().showTransaction();;
        payPal.processPayment().showTransaction();;
        BPI.processPayment().showTransaction();

    }
}
