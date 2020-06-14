package com.example.excel_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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

public class showSheetUnsold extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    ListAdapter adapter;
    ProgressDialog loading;
    ImageButton homeButton;
    ImageButton backButton;
    SimpleAdapter simpleAdapter;
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewmaster);
        listView = (ListView) findViewById(R.id.lv_items);
        homeButton=findViewById(R.id.homebutton);
        backButton=findViewById(R.id.backbutton);
        inputSearch=findViewById(R.id.inputSearch);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(showSheetUnsold.this,Options.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(showSheetUnsold.this,Options.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(this);
        getItems();
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                showSheetUnsold.this.simpleAdapter.getFilter().filter(cs);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

        });
    }


    private void getItems() {

        loading =  ProgressDialog.show(this,"Loading","please wait",false,true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbxYFxNKZ54PSIMwTbS1Jv01_TEYLx-RKyXolz4tCnLqzIhIS28t/exec?action=getItems",
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

    String status;
    private void parseItems(String jsonResposnce) {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String date = jo.getString("date");
                if(date.length()>10)
                    date=date.substring(0,10);
                String lotno = jo.getString("lotno");
                String mark = jo.getString("mark");
                String rate = jo.getString("rate");
                String sold = jo.getString("sold");
                String broker = jo.getString("broker");
                String pkg = jo.getString("pkg");
                String net = jo.getString("net");
                String grade = jo.getString("grade");
                String cd = jo.getString("cd");
                String partyname = jo.getString("partyname");
                String city = jo.getString("city");
                String transport = jo.getString("transport");
                String dio = jo.getString("dio");
                String exinvoice= jo.getString("exinvoice");
                String dispatch = jo.getString("dispatch");
                String bilty = jo.getString("bilty");
                String remark = jo.getString("remark");
                String total= jo.getString("total");
                status=sold;



                HashMap<String, String> item = new HashMap<>();
                item.put("date", date);
                item.put("lotno", lotno);
                item.put("mark",mark);
                item.put("rate",rate);
                item.put("broker",broker);
                item.put("sold",sold);
                item.put("total",total);
                item.put("remark",remark);
                item.put("bilty",bilty);
                item.put("dispatch",dispatch);
                item.put("exinvoice",exinvoice);
                item.put("dio",dio);
                item.put("transport",transport);
                item.put("city",city);
                item.put("partyname",partyname);
                item.put("cd",cd);
                item.put("grade",grade);
                item.put("pkg",pkg);
                item.put("net",net);
                if(sold.equalsIgnoreCase("NO")||(sold).equalsIgnoreCase("UNSOLD"))
                    list.add(item);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


         simpleAdapter= new SimpleAdapter(this,list,R.layout.list_row_item,
                new String[]{"date","sold","rate","lotno","broker","mark","total","net","grade","pkg"},
                new int[]{R.id.datetvdata,R.id.statustvdata,R.id.ratetvdata,R.id.lotnotvdata,R.id.brokertvdata,R.id.marktvdata,R.id.totalwttvdata,R.id.netwttvdata,R.id.gradetvdata,R.id.pkgdtvdata})
        {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(R.id.statustvdata);
                if((text1.getText().toString().equalsIgnoreCase("YES"))||(text1.getText().toString().equalsIgnoreCase("SOLD"))) {
                    text1.setTextColor(Color.GREEN);
                }
                else
                {
                    text1.setTextColor(Color.RED);
                }
                return view;
            };
        };



        listView.setAdapter(simpleAdapter);
        loading.dismiss();
    }
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ItemDetails.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String date = map.get("date").toString();
        String lotno = map.get("lotno").toString();
        String mark = map.get("mark").toString();
        String rate  = map.get("rate").toString();
        String broker = map.get("broker").toString();
        String sold = map.get("sold").toString();
        String total = map.get("total").toString();
        String dispatch = map.get("dispatch").toString();
        String pkg = map.get("pkg").toString();
        String cd = map.get("cd").toString();
        String partyname = map.get("partyname").toString();
        String city = map.get("city");
        String transport = map.get("transport").toString();
        String bilty = map.get("bilty").toString();
        String remark = map.get("remark").toString();
        String exinvoice = map.get("exinvoice").toString();
        String dio = map.get("dio").toString();
        String grade = map.get("grade").toString();
        String net = map.get("net").toString();


        // String sno = map.get("sno").toString();

        // Log.e("SNO test",sno);
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


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
