package com.sict.lnhi.corona.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sict.lnhi.corona.R;
import com.sict.lnhi.corona.model.Giohang;
import com.sict.lnhi.corona.model.Menu;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChitietActivity extends AppCompatActivity {

    Toolbar toolbarchitiet;
    ImageView imgchitiet;
    TextView txtten, txtgia, txtmota;
    Spinner spinner;
    Button btndm;

    int id = 0;
    String tenmon = "";
    String mota = "";
    String anh = "";
    int gia = 0;
    int idtheloai = 0;
    int idth = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);
        AnhXa();
        ActionToolBar();
        GetDataChitiet();
        CatchSpinner();
        EventButton();
    }

    private void EventButton() {
        btndm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.manggiohang.size() > 0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i<HomeActivity.manggiohang.size(); i++){
                        if (HomeActivity.manggiohang.get(i).getIdma() == id){
                            HomeActivity.manggiohang.get(i).setSoluong(HomeActivity.manggiohang.get(i).getSoluong() + sl);
                            if (HomeActivity.manggiohang.get(i).getSoluong() >=10){
                                HomeActivity.manggiohang.get(i).setSoluong(10);
                            }
                            HomeActivity.manggiohang.get(i).setGia(gia * HomeActivity.manggiohang.get(i).getSoluong());
                            exists = true;
                        }
                    }
                    if (exists == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi = soluong * gia;
                        HomeActivity.manggiohang.add(new Giohang(id, tenmon, giamoi, anh, soluong));
                    }
                }else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giamoi = soluong * gia;
                    HomeActivity.manggiohang.add(new Giohang(id, tenmon, giamoi, anh, soluong));
                }
                Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetDataChitiet() {
        Menu menu = (Menu) getIntent().getSerializableExtra("thongtinmonan");
        id = menu.getId();
        tenmon = menu.getTenmon();
        mota = menu.getMota();
        anh = menu.getAnh();
        gia = menu.getGia();
        idtheloai = menu.getIdtheloai();
        idth = menu.getIdth();
        txtten.setText(tenmon);
        txtmota.setText(mota);
        Picasso.with(getApplicationContext()).load(anh)
                .placeholder(R.drawable.logo)
                .error(R.drawable.login)
                .into(imgchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá: " + decimalFormat.format(gia) + " Đ");
    }
//sữa

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
//
    private void ActionToolBar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
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

                //sữa
                 Toast.makeText(this, "Giỏ hàng của bạn", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChitietActivity.this, GiohangActivity.class));
                break;
            case R.id.setting:
                break;
            case R.id.donhang:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //hehe
    private void AnhXa() {
        toolbarchitiet = (Toolbar) findViewById(R.id.toolbarct);
        imgchitiet = (ImageView) findViewById(R.id.imgchitiet);
        txtten = (TextView) findViewById(R.id.tenmonct);
        txtgia = (TextView) findViewById(R.id.giachitiet);
        txtmota = (TextView) findViewById(R.id.motachitiet);
        spinner = (Spinner) findViewById(R.id.spinner);
        btndm = (Button) findViewById(R.id.btnthemgh);
    }
}