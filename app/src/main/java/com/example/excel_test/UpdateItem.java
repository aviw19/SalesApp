package com.example.excel_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateItem extends AppCompatActivity {
    String date; String lotno; String mark ;String rate  ;String broker;String sold;
    String total;String dispatch;String pkg ;String cd ;String partyname ;String city ;
    String transport;String bilty ;String remark ;String exinvoice ;String dio ;String grade ;String net ;
    EditText EditTextDate; EditText EditTextlotno;EditText EditTextmark; EditText EditTextrate;
    EditText EditTextbroker; EditText EditTextsold; EditText EditTexttotal; EditText EditTextdispatch;
    EditText EditTextpkg; EditText EditTextcd; EditText EditTextpartyname; EditText EditTextcity;
    EditText EditTexttransport; EditText EditTextbilty; EditText EditTextremark; EditText EditTextexinvoice;
    EditText EditTextdio; EditText EditTextgrade; EditText EditTextnet;
    Button updatewithchanges;
    Button updatebutton;
    ImageButton homeButton;
    String Broker;
    ImageButton backButton;
    ProgressDialog loading;
    ArrayList<String> brokers = new ArrayList<>();
    Spinner brokerspinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateitem);
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
        EditTextDate=findViewById(R.id.datedtvdata);
        EditTextcity=findViewById(R.id.citydtvdata);
        EditTextbilty=findViewById(R.id.biltydtvdata);

        EditTextlotno=findViewById(R.id.lotnodtvdata);
        EditTextcd=findViewById(R.id.cddtvdata);
        EditTextmark=findViewById(R.id.markdtvdata);
        EditTextpkg=findViewById(R.id.pkgdtvdata);
        EditTexttransport=findViewById(R.id.transportdtvdata);
        EditTextdio=findViewById(R.id.diodtvdata);
        EditTextdispatch=findViewById(R.id.dispatchdtvdata);
        EditTextrate=findViewById(R.id.ratedtvdata);
        EditTextgrade=findViewById(R.id.gradedtvdata);
        EditTextnet=findViewById(R.id.netdtvdata);
        EditTexttotal=findViewById(R.id.totaldtvdata);
        EditTextsold=findViewById(R.id.solddtvdata);
        EditTextmark=findViewById(R.id.markdtvdata);
        EditTextremark=findViewById(R.id.remarkdtvdata);
        EditTextexinvoice=findViewById(R.id.exinvoicedtvdata);
        EditTextpartyname=findViewById(R.id.partynamedtvdata);
        EditTextDate.setText(date);
        EditTextcity.setText(city);
        EditTextbilty.setText(bilty);
        EditTexttotal.setText(total);
        EditTextmark.setText(mark);
        EditTextpkg.setText(pkg);
        EditTextpartyname.setText(partyname);
        EditTextsold.setText(sold);
        EditTextremark.setText(remark);
        EditTextnet.setText(net);
        EditTextgrade.setText(grade);
        EditTextdispatch.setText(dispatch);
        EditTextdio.setText(dio);
        EditTexttransport.setText(transport);
       // EditTextbroker.setText(broker);
        EditTextexinvoice.setText(exinvoice);
        EditTextlotno.setText(lotno);
        EditTextcd.setText(cd);
        EditTextrate.setText(rate);
        updatebutton=findViewById(R.id.updatebutton);
        updatewithchanges=findViewById(R.id.updatewithchangesbutton);
        homeButton=findViewById(R.id.homebutton);
        backButton=findViewById(R.id.backbutton);
        brokerspinner =findViewById(R.id.brokerdtvdata);

        getAllBrokers();
        brokerspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                    Broker="";
                else
                    Broker= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(UpdateItem.this,Options.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(UpdateItem.this,showSheet1.class);
                startActivity(intent);
            }
        });
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItem();
            }
        });
        updatewithchanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItem();
                getBroker();


            }
        });
    }

    private void getAllBrokers() {
        loading=ProgressDialog.show(this,"Please wait","Please wait");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbxYFxNKZ54PSIMwTbS1Jv01_TEYLx-RKyXolz4tCnLqzIhIS28t/exec?action=getBrokernames",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }


    private void parseItems(String jsonResposnce) {
        loading.dismiss();

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        brokers.add(broker);

        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String brokername = jo.getString("brokername");

                brokers.add(brokername);



            }
        } catch (JSONException e) {
            e.printStackTrace();
        } ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, brokers);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        brokerspinner.setAdapter(adapter);

      //  loading.dismiss();

    }

    private void getBroker() {
        final String brokerget = Broker;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxYFxNKZ54PSIMwTbS1Jv01_TEYLx-RKyXolz4tCnLqzIhIS28t/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // loading.dismiss();
                        Toast.makeText(UpdateItem.this, response, Toast.LENGTH_LONG).show();
                        PackageManager pm=getPackageManager();
                        try {
                            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                            String phoneNumberWithCountryCode = "+91"+response;
                            String message = "Lot No :"+EditTextlotno.getText().toString()+"\n"+"Wt :"+EditTextnet.getText().toString()+"\n"+"Pkg :"+EditTextpkg.getText().toString()+"\n"+"Grade :"+EditTextgrade.getText().toString()+"\n"+"Mark :"+EditTextmark.getText().toString()+"\n"+"Rate :"+EditTextrate.getText().toString()+"\n"+"\n"+"Please send party details";
                            startActivity(
                                    new Intent(Intent.ACTION_VIEW,
                                            Uri.parse(
                                                    String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumberWithCountryCode, message)
                                            )
                                    )
                            );
                        }
                        catch (PackageManager.NameNotFoundException e) {
                            System.out.println("Hello");
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
                parmas.put("action", "getBroker");
                parmas.put("brokername",brokerget);
                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);
    }


    private void updateItem() {

        //final ProgressDialog loading = ProgressDialog.show(this, "Adding Item", "Please wait");
        final String Date = EditTextDate.getText().toString().trim();
        final String Lotno = EditTextlotno.getText().toString().trim();
        final String Grade = EditTextgrade.getText().toString().trim();
        final String Net = EditTextnet.getText().toString().trim();
        final String PKG = EditTextpkg.getText().toString().trim();
        final String Total = EditTexttotal.getText().toString().trim();
        final String Mark = EditTextmark.getText().toString().trim();
        final String Sold = EditTextsold.getText().toString().trim();
        final String Rate = EditTextrate.getText().toString().trim();
        final String CD = EditTextcd.getText().toString().trim();
        //final String Broker = EditTextbroker.getText().toString().trim();
        final String PartyName = EditTextpartyname.getText().toString().trim();
        final String City = EditTextcity.getText().toString().trim();
        final String Transport = EditTexttransport.getText().toString().trim();
        final String Dio = EditTextdio.getText().toString().trim();
        final String Exinvoice = EditTextexinvoice.getText().toString().trim();
        final String Dispatch = EditTextdispatch.getText().toString().trim();
        final String Bilty = EditTextbilty.getText().toString().trim();
        final String Remark = EditTextremark.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxYFxNKZ54PSIMwTbS1Jv01_TEYLx-RKyXolz4tCnLqzIhIS28t/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                       // loading.dismiss();
                        Toast.makeText(UpdateItem.this, response, Toast.LENGTH_LONG).show();


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
                parmas.put("action", "updateItem");
                parmas.put("date", Date);
                parmas.put("lotno", Lotno);
                parmas.put("grade", Grade);
                parmas.put("net", Net);
                parmas.put("pkg", PKG);
                parmas.put("total", Total);
                parmas.put("mark", Mark);
                parmas.put("sold", Sold);
                parmas.put("rate", Rate);
                parmas.put("cd", CD);
                parmas.put("broker", Broker);
                parmas.put("partyname", PartyName);
                parmas.put("city", City);
                parmas.put("transport", Transport);
                parmas.put("dio", Dio);
                parmas.put("exinvoice", Exinvoice);
                parmas.put("dispatch", Dispatch);
                parmas.put("bilty", Bilty);
                parmas.put("remark", Remark);

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
