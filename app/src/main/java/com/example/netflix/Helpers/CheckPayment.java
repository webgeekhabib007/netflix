package com.example.netflix.Helpers;

import android.content.Context;
import android.util.Log;

import com.example.netflix.Activities.PaymentGateway;
import com.example.netflix.Activities.PaymentOverdue;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckPayment  {
    PaymentGateway context1;
    PaymentOverdue context2;
    JSONObject paymentOptions;
    int type=0;
    public CheckPayment(PaymentGateway applicationContext, JSONObject options) {
        this.context1 = applicationContext;
        this.paymentOptions = options;
        this.type=1;
    }
    public CheckPayment(PaymentOverdue applicationContext, JSONObject options) {
        this.context2 = applicationContext;
        this.paymentOptions = options;
        this.type=2;
    }
    public void check() {
        boolean flag=false;
        try{
            String amount = paymentOptions.getString("amount");
            if(String.valueOf("349").equals(amount) || String.valueOf("649").equals(amount) || String.valueOf("799").equals(amount)){
                if(this.type == 1)this.context1.onPaymentSuccess("Success");
                else this.context2.onPaymentSuccess("Success");
            }else{
                if(this.type == 2)this.context1.onPaymentError(0,"Error");
                else this.context2.onPaymentError(0,"Error");
            }
        }catch (Exception exp){
            exp.printStackTrace();
        }
    }
}
