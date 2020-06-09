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

public class AddEmployee extends AppCompatActivity {
    EditText editTextEmployeeName;
    EditText editTextEmployeePassword;
    Button buttonAddEmployee;
    ImageButton homeButton;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        editTextEmployeeName = findViewById(R.id.employeenameet);
        editTextEmployeePassword = findViewById(R.id.employeepasswordet);
        buttonAddEmployee = findViewById(R.id.addemployee);

        buttonAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployee();

            }
        });
        homeButton=findViewById(R.id.homebutton);
        backButton=findViewById(R.id.backbutton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddEmployee.this,Options.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddEmployee.this,Options.class);
                startActivity(intent);
            }
        });

    }

    private void addEmployee() {
        final ProgressDialog loading = ProgressDialog.show(this, "Adding Employee", "Please wait");
        final String brokername = editTextEmployeeName.getText().toString().trim();
        final String brokercontactno = editTextEmployeePassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxYFxNKZ54PSIMwTbS1Jv01_TEYLx-RKyXolz4tCnLqzIhIS28t/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(AddEmployee.this, response, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddEmployee.this, Options.class);
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
                parmas.put("action", "addEmployee");
                parmas.put("employeename", brokername);
                parmas.put("employeepassword", brokercontactno);

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
