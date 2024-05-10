package com.sict.lnhi.corona.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.sict.lnhi.corona.R;
import com.sict.lnhi.corona.ultil.CheckConnection;
import com.sict.lnhi.corona.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongtinuserActivity extends AppCompatActivity {

    EditText txttenkh, sdtkh, diachikh;
    Button btnxn, btnhuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinuser);
        AnhXa();
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        }else {
            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }
    }




    private void EventButton() {
        btnxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = txttenkh.getText().toString().trim();
                final String sdt = sdtkh.getText().toString().trim();
                final String diachi = diachikh.getText().toString().trim();
                if (ten.length()>0 && sdt.length()>0 && diachi.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.donhang,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(final String madonhang) {
                                    Log.d("madonhang", madonhang.toString());
                                    if (Integer.parseInt(madonhang) > 0){
                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                        StringRequest request = new StringRequest(Request.Method.POST, Server.chitiet,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if (response.equals("1")){
                                                            HomeActivity.manggiohang.clear();
                                                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn đã đặt hàng thành công");
                                                            Intent intent = new Intent(getApplicationContext(), DonhangActivity.class);
                                                            startActivity(intent);
                                                            CheckConnection.showToast_Short(getApplicationContext(), "Mời bạn tiếp tục mua hàng");
                                                        }else {
                                                            CheckConnection.showToast_Short(getApplicationContext(), "Lỗi dữ liệu");
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
                                                JSONArray jsonArray = new JSONArray();
                                                for (int i = 0 ; i<HomeActivity.manggiohang.size(); i++){
                                                    JSONObject jsonObject = new JSONObject();
                                                    try {
                                                        jsonObject.put("madonhang", madonhang);
                                                        jsonObject.put("mamonan", HomeActivity.manggiohang.get(i).getIdma());
                                                        jsonObject.put("tenmonan", HomeActivity.manggiohang.get(i).getTenma());
                                                        jsonObject.put("giatien", HomeActivity.manggiohang.get(i).getGia());
                                                        jsonObject.put("soluong", HomeActivity.manggiohang.get(i).getSoluong());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    jsonArray.put(jsonObject);
                                                }
                                                HashMap<String, String> hashMap = new HashMap<>();
                                                hashMap.put("json", jsonArray.toString());
                                                return hashMap;
                                            }
                                        };
                                        queue.add(request);
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ThongtinuserActivity.this, "Lỗi" +error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("ten", ten);
                            hashMap.put("sdt", sdt);
                            hashMap.put("diachi", diachi);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    CheckConnection.showToast_Short(getApplicationContext(), "Vui lòng nhập đủ thông tin");
                }
            }
        });
    }

    private void AnhXa() {
        txttenkh = (EditText) findViewById(R.id.tenkh);
        sdtkh = (EditText) findViewById(R.id.edsdt);
        diachikh = (EditText) findViewById(R.id.eddiachi);
        btnxn = (Button) findViewById(R.id.btnxn);
        btnhuy = (Button) findViewById(R.id.btnhuy);
        sdtkh.setText(HomeActivity.sdt);
    }

}