package com.ibm.ejpmercado.mercado_ervin_java;

// import java.util.ArrayList;

/**
 * k
 */
interface Verifiable {
    boolean verifyPayment();
}

abstract class Payment {
    int amount;

    public Payment(int amount) {
        this.amount = amount;
    }

    void executePayment() {
    }

    void displayAnnouncement() {

    }
}

class CreditCardPayment extends Payment implements Verifiable {

    int cardNumber;

    public CreditCardPayment(int amount, int cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean verifyPayment() {
        System.out.println("Processing credit card payment...");
        return (String.valueOf(cardNumber).length() == 16) ? true : false;
    }
}

class PayPalPayment extends Payment implements Verifiable {
    String email;

    PayPalPayment(int amount, String email) {
        super(amount);
        this.email = email;
    }

    boolean emailContainsAtSymbol() {
        return (this.email.contains("@")) ? true : false;
    }

    @Override
    public boolean verifyPayment() {
        System.out.println("Processing PayPal payment...");
        return (emailContainsAtSymbol()) ? true : false;
    }

}

class BankTransferPayment extends Payment implements Verifiable {
    int accountNumber;

    BankTransferPayment(int accountNumber, int amount) {
        super(amount);
        this.accountNumber = accountNumber;
    }

    int getAccountNumberLength() {
        return String.valueOf(this.accountNumber).length();
    }

    @Override
    public boolean verifyPayment() {
        System.out.println("Processing bank transfer...");
        return (getAccountNumberLength() == 10) ? true : false;
    }

}

sealed abstract class PaymentType permits OnlinePaymentType, OfflinePaymentType {
}

final class OnlinePaymentType extends PaymentType {
}

final class OfflinePaymentType extends PaymentType {
}

sealed class Gateway permits PaymentGateway {
    void processPayments() {

    }
}

non-sealed class PaymentGateway extends Gateway {
}

record PaymentDetails(int transactionID, int amoumt, PaymentType paymentMethod, String timestamp) {

}

class Day3 {

    public static void main(String[] args) {
        Payment creditCard = new CreditCardPayment(1000, 0000000000000000);
        Payment payPal = new PayPalPayment(1000, "ervinmercado31@gmail.com");
        Payment BPI = new BankTransferPayment(1000, 0000000000000000);


        PaymentType online = new OnlinePaymentType();
        PaymentType offline = new OfflinePaymentType();

        Gateway paymentGateway = new PaymentGateway();
    }
}
