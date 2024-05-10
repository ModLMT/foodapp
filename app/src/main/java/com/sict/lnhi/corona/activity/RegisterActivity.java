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
import com.sict.lnhi.corona.ultil.Server;


import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText edtten, edtemail, edtpass, edtsdt;
    Button register, huy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edtten.getText().toString().trim();
                String email = edtemail.getText().toString().trim();
                String pass = edtpass.getText().toString().trim();
                String sdt = edtsdt.getText().toString().trim();
                if (ten.isEmpty() || email.isEmpty() || pass.isEmpty() || sdt.isEmpty()){
                    edtten.setError("Vui lòng nhập tên");
                    edtemail.setError("Vui lòng nhập Email");
                    edtpass.setError("Vui lòng nhập Password");
                    edtsdt.setError("Vui lòng nhập số điện thoại");
                }else {
                    Dangki();
                }

            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

    private void Dangki(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.register,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }else {
                            Toast.makeText(RegisterActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Xảy ra lỗi" +error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "Lỗi\n" + error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ten", edtten.getText().toString().trim());
                params.put("email", edtemail.getText().toString().trim());
                params.put("pass", edtpass.getText().toString().trim());
                params.put("sdt", edtsdt.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void AnhXa(){
        register = (Button) findViewById(R.id.register);
        edtten = (EditText) findViewById(R.id.ten);
        edtemail = (EditText) findViewById(R.id.email);
        edtpass = (EditText) findViewById(R.id.pass);
        edtsdt = (EditText) findViewById(R.id.sdt);
        huy = (Button) findViewById(R.id.huy);
    }
}