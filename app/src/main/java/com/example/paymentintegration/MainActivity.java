package com.example.paymentintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    Button payBtn;
    TextView payText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Checkout.preload(getApplicationContext());
        
        payText = (TextView) findViewById(R.id.payTxt);
        payBtn = (Button) findViewById(R.id.payBtn);
        
        
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                makePayment();
                
            }
        });
        
        
    }

    private void makePayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_PLIYps3QerEyXO");

        checkout.setImage(R.drawable.logo);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Manisha");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
           // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//500*100
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","8507513832");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        payText.setText("Successfull payment ID : " + s);
    }

    @Override
    public void onPaymentError(int i, String s) {

        payText.setText("Failed and cause is :" + s);

    }

}