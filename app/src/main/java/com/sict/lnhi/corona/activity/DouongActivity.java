package com.sict.lnhi.corona.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sict.lnhi.corona.R;
import com.sict.lnhi.corona.adapter.DouongAdapter;
import com.sict.lnhi.corona.model.Menu;
import com.sict.lnhi.corona.ultil.CheckConnection;
import com.sict.lnhi.corona.ultil.Server;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DouongActivity extends AppCompatActivity {

    Toolbar toolbardu;
    ListView lvdu;
    DouongAdapter douongAdapter;
    ArrayList<Menu> mangdu;
    int iddu = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douong);
        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            Getidtl();
            ActionToolbar();
            Getdatadouong();
            LoadMoredata();
        }else {
            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
            finish();
        }

    }

    private void LoadMoredata() {

        lvdu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChitietActivity.class);
                intent.putExtra("thongtinmonan", mangdu.get(i));
                startActivity(intent);
            }
        });

    }

    private void Getdatadouong() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.urldu;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
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
                            mangdu.add(new Menu(id, tenmon,mota,anh,gia,idtheloai,idth));
                            douongAdapter.notifyDataSetChanged();
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
                param.put("idtheloai", String.valueOf(iddu));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbardu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Getidtl() {
        iddu = getIntent().getIntExtra("idtl",-1);
        Log.d("giatritheloai", iddu+"");
    }

    private void AnhXa() {
        toolbardu = (Toolbar) findViewById(R.id.toolbardouong);
        lvdu = (ListView) findViewById(R.id.lsviewdouong);
        mangdu = new ArrayList<>();
        douongAdapter = new DouongAdapter(getApplicationContext(), mangdu);
        lvdu.setAdapter(douongAdapter);
    }

}