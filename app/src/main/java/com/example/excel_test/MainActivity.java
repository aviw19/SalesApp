package com.example.excel_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {
EditText username_et;
EditText password_et;
Button login_button;
String username;
String password;
ProgressDialog loading;
    public static final String MyPREFERENCES = "MyPrefs" ;
SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username_et=findViewById(R.id.username);
        password_et=findViewById(R.id.password);

        login_button=findViewById(R.id.login);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(sharedpreferences.getBoolean("signedIn",false)==true) {
            String us=sharedpreferences.getString("username","ad");
            String pwd=sharedpreferences.getString("password","ad");

            Intent intent = new Intent(MainActivity.this, Options.class);
            intent.putExtra("username",us);
            intent.putExtra("password",pwd);
            startActivity(intent);
        }
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading =  ProgressDialog.show(MainActivity.this,"Getting Logged In","please wait",false,true);
                verify();

            }
        });


    }
    @Override
    public void onBackPressed()
    {

    }

    private void verify() {
        username=username_et.getText().toString();
        password=password_et.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxYFxNKZ54PSIMwTbS1Jv01_TEYLx-RKyXolz4tCnLqzIhIS28t/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("username",username);
                        editor.putString("password",password);
                        editor.putBoolean("signedIn",true);
                        editor.apply();



                        if(response.equals("\"Correct\"")) {
                            Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(MainActivity.this,Options.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Incorrect UserName or Password", Toast.LENGTH_LONG).show();

                        }




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
                parmas.put("action","getEmployee");
                parmas.put("username",username);
                parmas.put("password",password);

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
