package com.example.excel_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddBroker extends AppCompatActivity {
    EditText editTextBrokerName;
    EditText editTextBrokerContactNo;
    Button buttonAddBroker;
    ImageButton homeButton;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_broker);
        editTextBrokerName=findViewById(R.id.brokernameet);
        editTextBrokerContactNo=findViewById(R.id.brokercontactnoet);
        buttonAddBroker=findViewById(R.id.addbroker);

        buttonAddBroker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBroker();

            }
        });
        homeButton=findViewById(R.id.homebutton);
        backButton=findViewById(R.id.backbutton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddBroker.this,Options.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddBroker.this,Options.class);
                startActivity(intent);
            }
        });

    }

    private void addBroker() {
        final ProgressDialog loading = ProgressDialog.show(this,"Adding Broker","Please wait");
        final String brokername=editTextBrokerName.getText().toString().trim();
        final String brokercontactno = editTextBrokerContactNo.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxYFxNKZ54PSIMwTbS1Jv01_TEYLx-RKyXolz4tCnLqzIhIS28t/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(AddBroker.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),Options.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","addBroker");
                parmas.put("brokername",brokername);
                parmas.put("brokercontactnumber",brokercontactno);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }





}



