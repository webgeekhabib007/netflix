package com.example.netflix.Helpers;

public interface PaymentResultListener {
    public void onPaymentSuccess(String s);
    public void onPaymentError(int i, String s);
}
