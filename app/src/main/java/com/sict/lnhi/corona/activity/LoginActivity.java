package com.sict.lnhi.corona.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    Button btnlogin;
    EditText sdt, pass;
    TextView regist;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin = findViewById(R.id.login);
        sdt = findViewById(R.id.sdt);
        pass = findViewById(R.id.pass);
        loading = findViewById(R.id.loading);
        regist = findViewById(R.id.regist);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msdt = sdt.getText().toString().trim();
                String mpass = pass.getText().toString().trim();
                if (!msdt.isEmpty() || !mpass.isEmpty()) {

                    check(msdt, mpass);
                } else {
                    sdt.setError("Vui lòng nhập Email");
                    pass.setError("Vui lòng nhập Password");
                }
            }
        });
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    private void check(final String sdt, final String pass){
        loading.setVisibility(View.VISIBLE);
        btnlogin.setVisibility(View.GONE);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("error")){
                    loading.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    HomeActivity.sdt = response.toString();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);

                }
                else {
                    loading.setVisibility(View.GONE);
                    btnlogin.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this,"Số điện thoại hoặc mật khẩu chưa đúng",Toast.LENGTH_SHORT).show();
                }
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        btnlogin.setVisibility(View.VISIBLE);
                        Log.d("Error", error.toString());
                        Toast.makeText(LoginActivity.this,"Error" +error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sdt",sdt);
                params.put("pass",pass);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void addControl() {
        btnlogin = findViewById(R.id.login);
        sdt = findViewById(R.id.sdt);
        pass = findViewById(R.id.pass);
        loading = findViewById(R.id.loading);
        regist = findViewById(R.id.regist);
    }

}