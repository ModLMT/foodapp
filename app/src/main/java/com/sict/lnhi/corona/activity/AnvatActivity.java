package com.sict.lnhi.corona.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

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
import com.sict.lnhi.corona.adapter.AnvatAdapter;
import com.sict.lnhi.corona.model.Menu;
import com.sict.lnhi.corona.ultil.CheckConnection;
import com.sict.lnhi.corona.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnvatActivity extends AppCompatActivity {

    Toolbar toolbarav;
    ListView lvav;
    AnvatAdapter anvatAdapter;
    ArrayList<Menu> mangav;
    int idav = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anvat);
        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionToolbar();
            Getidtl();
            Getdataanvat();
            LoadMoredata();
        }else {
            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }

    }

    private void LoadMoredata() {

        lvav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChitietActivity.class);
                intent.putExtra("thongtinmonan", (Parcelable) mangav.get(i));
                startActivity(intent);
            }
        });

    }

    private void Getdataanvat() {
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
                            mangav.add(new Menu(id, tenmon,mota,anh,gia,idtheloai,idth));
                            anvatAdapter.notifyDataSetChanged();
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
                param.put("idtheloai", String.valueOf(idav));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarav);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarav.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //thêm mới
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.giohang:
                Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
                startActivity(intent);
                 Toast.makeText(this, "Giỏ hàng của bạn", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AnvatActivity.this, GiohangActivity.class));
                break;
            case R.id.setting:
                break;
            case R.id.donhang:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //hehe

    private void Getidtl() {
        idav = getIntent().getIntExtra("idtl",-1);
    }
    private void AnhXa() {
        toolbarav = (Toolbar) findViewById(R.id.toolbaranvat);
        lvav = (ListView) findViewById(R.id.lsviewanvat);
        mangav = new ArrayList<>();
        anvatAdapter = new AnvatAdapter(getApplicationContext(), mangav);
        lvav.setAdapter(anvatAdapter);
    }
}