package com.example.excel_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
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
import java.util.List;
import java.util.Map;

public class AddNewItem extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private SearchView searchView;
    private MenuItem searchMenuItem;

    EditText editTextDate;
    EditText editTextLotno;
    EditText editTextGrade;
    EditText editTextNet;
    EditText editTextPKG;
    EditText editTextTotal;
    EditText editTextMark;
    EditText editTextSold;
    EditText editTextRate;
    EditText editTextCD;
    EditText editTextBroker;
    EditText editTextPartyName;
    EditText editTextCity;
    EditText editTextTransport;
    EditText editTextDio;
    EditText editTextExinvoice;
    EditText editTextDispatch;
    EditText editTextBilty;
    EditText editTextRemark;
    Spinner brokerspinner;
    Button addItemButton;
    ImageButton homeButton;
    ImageButton backButton;
    ArrayList<String> brokers = new ArrayList<>();
    ProgressDialog loading;
     String Broker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        editTextDate = findViewById(R.id.dateet);
        editTextLotno = findViewById(R.id.lotnoet);
        editTextGrade = findViewById(R.id.gradeet);
        editTextNet = findViewById(R.id.netet);
        editTextPKG = findViewById(R.id.pkget);
        editTextTotal = findViewById(R.id.totalet);
        editTextMark = findViewById(R.id.market);
        editTextSold = findViewById(R.id.soldet);
        editTextRate = findViewById(R.id.rateet);
        editTextCD = findViewById(R.id.cdet);
        brokerspinner = findViewById(R.id.brokeret);
        editTextPartyName = findViewById(R.id.partyet);
        editTextCity = findViewById(R.id.cityet);
        editTextTransport = findViewById(R.id.transportet);
        editTextDio = findViewById(R.id.dioet);
        editTextExinvoice = findViewById(R.id.exinvoiceet);
        editTextDispatch = findViewById(R.id.dispatchet);
        editTextBilty= findViewById(R.id.biltyet);
        editTextRemark = findViewById(R.id.remarket);
        addItemButton = findViewById(R.id.additembutton);
        homeButton=findViewById(R.id.homebutton);
        backButton=findViewById(R.id.backbutton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddNewItem.this,Options.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddNewItem.this,showSheet1.class);
                startActivity(intent);
            }
        });
        loading =  ProgressDialog.show(this,"Loading","please wait",false,true);
        getItems();
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

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToSheet();
            }
        });
    }
    private void   addItemToSheet() {

        final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");
        final String Date = editTextDate.getText().toString().trim();
        final String Lotno = editTextLotno.getText().toString().trim();
        final String Grade = editTextGrade.getText().toString().trim();
        final String Net = editTextNet.getText().toString().trim();
        final String PKG = editTextPKG.getText().toString().trim();
        final String Total = editTextTotal.getText().toString().trim();
        final String Mark = editTextMark.getText().toString().trim();
        final String Sold = editTextSold.getText().toString().trim();
        final String Rate = editTextRate.getText().toString().trim();
        final String CD = editTextCD.getText().toString().trim();

        final String PartyName = editTextPartyName.getText().toString().trim();
        final String City = editTextCity.getText().toString().trim();
        final String Transport = editTextTransport.getText().toString().trim();
        final String Dio = editTextDio.getText().toString().trim();
        final String Exinvoice = editTextExinvoice.getText().toString().trim();
        final String Dispatch = editTextDispatch.getText().toString().trim();
        final String Bilty = editTextBilty.getText().toString().trim();
        final String Remark = editTextRemark.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxYFxNKZ54PSIMwTbS1Jv01_TEYLx-RKyXolz4tCnLqzIhIS28t/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(AddNewItem.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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
                parmas.put("action","addItem");
                parmas.put("date",Date);
                parmas.put("lotno",Lotno);
                parmas.put("grade",Grade);
                parmas.put("net",Net);
                parmas.put("pkg",PKG);
                parmas.put("total",Total);
                parmas.put("mark",Mark);
                parmas.put("sold",Sold);
                parmas.put("rate",Rate);
                parmas.put("cd",CD);
                parmas.put("broker",Broker);
                parmas.put("partyname",PartyName);
                parmas.put("city",City);
                parmas.put("transport",Transport);
                parmas.put("dio",Dio);
                parmas.put("exinvoice",Exinvoice);
                parmas.put("dispatch",Dispatch);
                parmas.put("bilty",Bilty);
                parmas.put("remark",Remark);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }
    private void getItems() {


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

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        brokers.add("Broker Name");

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

        loading.dismiss();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
