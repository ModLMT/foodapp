package com.sict.lnhi.corona.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.sict.lnhi.corona.R;
import com.sict.lnhi.corona.adapter.GiohangAdapter;
import com.sict.lnhi.corona.ultil.CheckConnection;

import java.text.DecimalFormat;

public class GiohangActivity extends AppCompatActivity {

    ListView listViewgh;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnthanhtoan;
    Button btnttmh;
    Toolbar toolbargiohang;
    GiohangAdapter giohangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        AnhXa();
        ActionToolbar();
        CheckData();
        EventUltil();
        CachOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btnttmh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.manggiohang.size() >0){
                    Intent intent = new Intent(getApplicationContext(), ThongtinuserActivity.class);
                    startActivity(intent);
                }else {
                    CheckConnection.showToast_Short(getApplicationContext(), "Giỏ hàng của bạn đang trống");
                }
            }
        });
    }

    private void CachOnItemListView() {
        listViewgh.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GiohangActivity.this);
                builder.setTitle("Xác nhận xoá sản phẩm");
                builder.setMessage("Bạn có chắc muốn xoá sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (HomeActivity.manggiohang.size() <= 0){
                            txtthongbao.setVisibility(View.VISIBLE);
                        }else {
                            HomeActivity.manggiohang.remove(i);
                            giohangAdapter.notifyDataSetChanged();
                            EventUltil();
                            if (HomeActivity.manggiohang.size() <= 0){
                                txtthongbao.setVisibility(View.VISIBLE);
                            }else {
                                txtthongbao.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUltil() {
        long tongtien = 0;
        for (int i = 0; i<HomeActivity.manggiohang.size(); i++){
            tongtien += HomeActivity.manggiohang.get(i).getGia();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + " Đ");
    }

    private void CheckData() {
        if (HomeActivity.manggiohang.size() <=0){
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            listViewgh.setVisibility(View.INVISIBLE);
        }else {
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            listViewgh.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        listViewgh = (ListView) findViewById(R.id.lsvgiohang);
        txtthongbao = (TextView) findViewById(R.id.txtthongbao);
        txttongtien = (TextView) findViewById(R.id.txttongtien);
        btnthanhtoan = (Button) findViewById(R.id.btnthanhtoan);
        btnttmh = (Button) findViewById(R.id.btntt);
        toolbargiohang = (Toolbar) findViewById(R.id.toolbargh);
        giohangAdapter = new GiohangAdapter(GiohangActivity.this, HomeActivity.manggiohang);
        listViewgh.setAdapter(giohangAdapter);
    }
}