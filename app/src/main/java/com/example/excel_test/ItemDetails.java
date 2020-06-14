package com.example.excel_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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

public class ItemDetails extends AppCompatActivity {
    String date; String lotno; String mark ;String rate  ;String broker;String sold;
    String total;String dispatch;String pkg ;String cd ;String partyname ;String city ;
    String transport;String bilty ;String remark ;String exinvoice ;String dio ;String grade ;String net ;
    TextView textViewDate; TextView textViewlotno;TextView textViewmark; TextView textViewrate;
    TextView textViewbroker; TextView textViewsold; TextView textViewtotal; TextView textViewdispatch;
    TextView textViewpkg; TextView textViewcd; TextView textViewpartyname; TextView textViewcity;
    TextView textViewtransport; TextView textViewbilty; TextView textViewremark; TextView textViewexinvoice;
    TextView textViewdio; TextView textViewgrade; TextView textViewnet;
    Button deletebutton;
    Button updatebutton;
    ImageButton homeButton;
    ImageButton backButton;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.itemdetails);
        Intent intent = getIntent();

        date=intent.getStringExtra("date");
        lotno=intent.getStringExtra("lotno");
        mark=intent.getStringExtra("mark");
        rate=intent.getStringExtra("rate");
        broker=intent.getStringExtra("broker");
        sold=intent.getStringExtra("sold");
        total=intent.getStringExtra("total");
        dispatch=intent.getStringExtra("dispatch");
        pkg=intent.getStringExtra("pkg");
        cd=intent.getStringExtra("cd");
        partyname=intent.getStringExtra("partyname");
        city=intent.getStringExtra("city");
        transport=intent.getStringExtra("transport");
        bilty=intent.getStringExtra("bilty");
        remark=intent.getStringExtra("remark");
        exinvoice=intent.getStringExtra("exinvoice");
        dio=intent.getStringExtra("dio");
        grade=intent.getStringExtra("grade");
        net=intent.getStringExtra("net");


        textViewDate=findViewById(R.id.datedtvdata);
        textViewcity=findViewById(R.id.citydtvdata);
        textViewbilty=findViewById(R.id.biltydtvdata);
        textViewbroker=findViewById(R.id.brokerdtvdata);
        textViewlotno=findViewById(R.id.lotnodtvdata);
        textViewcd=findViewById(R.id.cddtvdata);
        textViewmark=findViewById(R.id.markdtvdata);
        textViewpkg=findViewById(R.id.pkgdtvdata);
        textViewtransport=findViewById(R.id.transportdtvdata);
        textViewdio=findViewById(R.id.diodtvdata);
        textViewdispatch=findViewById(R.id.dispatchdtvdata);
        textViewrate=findViewById(R.id.ratedtvdata);
        textViewgrade=findViewById(R.id.gradedtvdata);
        textViewnet=findViewById(R.id.netdtvdata);
        textViewtotal=findViewById(R.id.totaldtvdata);
        textViewsold=findViewById(R.id.solddtvdata);
        textViewmark=findViewById(R.id.markdtvdata);
        textViewremark=findViewById(R.id.remarkdtvdata);
        textViewexinvoice=findViewById(R.id.exinvoicedtvdata);
        textViewpartyname=findViewById(R.id.partynamedtvdata);
        textViewDate.setText(date);
        textViewcity.setText(city);
        textViewbilty.setText(bilty);
        textViewtotal.setText(total);
        textViewmark.setText(mark);
        textViewpkg.setText(pkg);
        textViewpartyname.setText(partyname);
        textViewsold.setText(sold);
        textViewremark.setText(remark);
        textViewnet.setText(net);
        textViewgrade.setText(grade);
        textViewdispatch.setText(dispatch);
        textViewdio.setText(dio);
        textViewtransport.setText(transport);
        textViewbroker.setText(broker);
        textViewexinvoice.setText(exinvoice);
        textViewlotno.setText(lotno);
        textViewcd.setText(cd);
        textViewrate.setText(rate);
        if(sold.trim().toUpperCase().equals("YES"))
            textViewsold.setTextColor(Color.GREEN);
        else
            textViewsold.setTextColor(Color.RED);
        deletebutton=findViewById(R.id.deletebutton);
        homeButton=findViewById(R.id.homebutton);
        backButton=findViewById(R.id.backbutton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ItemDetails.this,Options.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ItemDetails.this,showSheet1.class);
                startActivity(intent);
            }
        });
        updatebutton=findViewById(R.id.updatebutton);
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ItemDetails.this,UpdateItem.class);
                intent.putExtra("date",date);
                intent.putExtra("lotno",lotno);
                intent.putExtra("mark",mark);
                intent.putExtra("rate",rate);
                intent.putExtra("broker",broker);
                intent.putExtra("sold",sold);
                intent.putExtra("grade",grade);
                intent.putExtra("total",total);
                intent.putExtra("net",net);
                intent.putExtra("pkg",pkg);
                intent.putExtra("dio",dio);
                intent.putExtra("transport",transport);
                intent.putExtra("city",city);
                intent.putExtra("dispatch",dispatch);
                intent.putExtra("exinvoice",exinvoice);
                intent.putExtra("bilty",bilty);
                intent.putExtra("remark",remark);
                intent.putExtra("cd",cd);
                intent.putExtra("partyname",partyname);
                startActivity(intent);
            }
        });
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // loading = ProgressDialog.show(getApplicationContext(),"Deleting Item","Please wait");
                deleteItem();
            }
        });


    }

    private void deleteItem() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxYFxNKZ54PSIMwTbS1Jv01_TEYLx-RKyXolz4tCnLqzIhIS28t/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //loading.dismiss();
                        Toast.makeText(ItemDetails.this,response,Toast.LENGTH_LONG).show();
                       Intent intent = new Intent(getApplicationContext(),showSheet1.class);
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
                parmas.put("action","removeItem");
                parmas.put("lotno",lotno);


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
