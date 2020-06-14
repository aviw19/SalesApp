package com.example.excel_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import static com.example.excel_test.MainActivity.MyPREFERENCES;

public class Options extends AppCompatActivity {
    Button buttonViewSheet1;
    Button buttonAddSheet1;
    Button buttonViewSheet1Inside;
    Button buttonViewSheet2;
    Button buttonAddNewBroker;
    Button buttonAddNewEmployee;
    Button buttonViewEmployee;
    Button buttonViewBroker;
    Button buttonlogout;
    SharedPreferences sharedPreferences;
    Button buttonSold;
    Button buttonUnSold;
    Button buttonBroker;
    String username;
    String password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username=sharedPreferences.getString("username","ad");
        password=sharedPreferences.getString("password","ad");

        setContentView(R.layout.activity_options);
        buttonlogout=findViewById(R.id.logout);
        buttonViewSheet1Inside=findViewById(R.id.viewsheet1inside);
        buttonViewSheet1=findViewById(R.id.viewsheet1);
        buttonAddSheet1=findViewById(R.id.addrow);
        buttonViewSheet2=findViewById(R.id.viewsheet2);
        buttonAddNewBroker=findViewById(R.id.newbroker);
        buttonAddNewEmployee=findViewById(R.id.newemployee);
        buttonViewEmployee=findViewById(R.id.employeesheet);
        buttonViewBroker=findViewById(R.id.brokersheet);
        buttonBroker=findViewById(R.id.viewbroker);
        buttonSold=findViewById(R.id.viewsold);
        if(!((username.equals("admin"))&&(password.equals("admin"))))
        {
            buttonViewBroker.setVisibility(View.GONE);
            buttonAddNewBroker.setVisibility(View.GONE);
            buttonViewEmployee.setVisibility(View.GONE);
            buttonAddNewEmployee.setVisibility(View.GONE);
        }
        buttonSold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Options.this,showSheetSold.class);
                startActivity(intent);
            }
        });
        buttonUnSold=findViewById(R.id.viewunsold);
        buttonUnSold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Options.this,showSheetUnsold.class);
                startActivity(intent);
            }
        });
        buttonViewSheet1Inside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Options.this,showSheet1.class);
                startActivity(intent);
            }
        });
        buttonAddSheet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Options.this,AddNewItem.class);
                startActivity(intent);
            }
        });
        buttonViewSheet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1qtwVKLRbNRfliNqXJVD80u1HTxZpEUJ9erhpIEauVqs/edit?usp=sharing"));
                startActivity(browserIntent);
            }
        });
        buttonViewSheet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1qtwVKLRbNRfliNqXJVD80u1HTxZpEUJ9erhpIEauVqs/edit?usp=sharing"));
                startActivity(browserIntent);
            }
        });
        buttonAddNewEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Options.this,AddEmployee.class);
                startActivity(intent);
            }
        });
        buttonAddNewBroker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Options.this,AddBroker.class);
                startActivity(intent);
            }
        });
        buttonViewEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1TMYi1uWV2cJB13g4SU9x9vogqabfTtW4w7aIazpPAMA/edit?usp=sharing"));
                startActivity(browserIntent);
            }
        });
        buttonViewBroker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1TMYi1uWV2cJB13g4SU9x9vogqabfTtW4w7aIazpPAMA/edit?usp=sharing"));
                startActivity(browserIntent);

            }
        });
        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("signedIn",false);
                editor.apply();
                Intent intent = new Intent(Options.this,MainActivity.class);
                startActivity(intent);

            }
        });
        buttonBroker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Options.this,Broker.class);
                startActivity(intent);
            }
        });
    }
}
