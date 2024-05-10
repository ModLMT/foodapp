package com.sict.lnhi.corona.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sict.lnhi.corona.R;
import com.sict.lnhi.corona.adapter.ThhieuAdapter;
import com.sict.lnhi.corona.model.Menu;
import com.sict.lnhi.corona.ultil.CheckConnection;
import com.sict.lnhi.corona.ultil.Server;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThuonghieuActivity extends AppCompatActivity {

    Toolbar toolbarth;
    ListView lvth;
    ThhieuAdapter thhieuAdapter;
    ArrayList<Menu> mangth;
    int idth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuonghieu);
        AnhXa();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            Getidtl();
            ActionToolbar();
            Getdatadouong();
            LoadMoredata();
            if (idth == 1){
                getSupportActionBar().setTitle("KFC");
            }
            if (idth == 2){
                getSupportActionBar().setTitle("BONPAS");
            }
            if (idth == 3){
                getSupportActionBar().setTitle("SASIN");
            }
            if (idth == 4){
                getSupportActionBar().setTitle("SASIN");
            }
        }else {
            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
            finish();
        }

    }

    private void LoadMoredata() {

        lvth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChitietActivity.class);
                intent.putExtra("thongtinmonan", mangth.get(i));
                startActivity(intent);
            }
        });

    }

    private void Getdatadouong() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getth, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tenmon = "";
                Integer gia = 0;
                String mota = "";
                String anh = "";
                int idtheloai = 0;
                int idth = 0;
                if (response != null){

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenmon = jsonObject.getString("tenmon");
                            gia = jsonObject.getInt("gia");
                            mota = jsonObject.getString("mota");
                            anh = jsonObject.getString("anh");
                            idtheloai = jsonObject.getInt("idtheloai");
                            idth = jsonObject.getInt("idth");
                            mangth.add(new Menu(id, tenmon,mota,anh,gia,idtheloai,idth));
                            thhieuAdapter.notifyDataSetChanged();
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

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String > param = new HashMap<>();
                param.put("idth", String.valueOf(idth));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarth);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarth.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    private void Getidtl() {
        idth = getIntent().getIntExtra("idth",-1);
        Log.d("thuonghieu", idth+"");
    }

    private void AnhXa() {
        toolbarth = (Toolbar) findViewById(R.id.toolbarthuonghieu);
        lvth = (ListView) findViewById(R.id.lsviewthuonghieu);
        mangth = new ArrayList<>();
        thhieuAdapter = new ThhieuAdapter(getApplicationContext(), mangth);
        lvth.setAdapter(thhieuAdapter);
    }

}