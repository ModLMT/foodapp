package com.sict.lnhi.corona.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.os.Bundle;

import android.view.View;

import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sict.lnhi.corona.R;
import com.sict.lnhi.corona.adapter.DonhangAdapter;
import com.sict.lnhi.corona.model.Donhang;
import com.sict.lnhi.corona.ultil.CheckConnection;
import com.sict.lnhi.corona.ultil.Server;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DonhangActivity extends AppCompatActivity {

    Toolbar toolbardh;
    ListView lvdh;
    DonhangAdapter donhangAdapter;
    ArrayList<Donhang> mangdh;
    int iddh = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donhang);
        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionToolbar();
            Datadonhang();
        }else {
            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }

    }

    private void Datadonhang() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getdonhang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                int madonhang = 0;
                int mamonan = 0;
                String tenmonan = "";
                int giatien = 0;
                int soluong = 0;
                if (response != null){

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            madonhang = jsonObject.getInt("madonhang");
                            mamonan = jsonObject.getInt("mamonan");
                            tenmonan = jsonObject.getString("tenmonan");
                            giatien = jsonObject.getInt("giatien");
                            soluong = jsonObject.getInt("soluong");
                            mangdh.add(new Donhang(id,madonhang,mamonan,tenmonan,giatien,soluong));
                            donhangAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DonhangActivity.this, "Lỗi" +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbardh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbardh = (Toolbar) findViewById(R.id.toolbardh);
        lvdh = (ListView) findViewById(R.id.lsvdonhang);
        mangdh = new ArrayList<>();
        donhangAdapter = new DonhangAdapter(mangdh, getApplicationContext());
        lvdh.setAdapter(donhangAdapter);
    }
}