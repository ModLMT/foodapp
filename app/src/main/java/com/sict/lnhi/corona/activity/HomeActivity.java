package com.sict.lnhi.corona.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.sict.lnhi.corona.R;
import com.sict.lnhi.corona.adapter.MenuAdapter;
import com.sict.lnhi.corona.adapter.TheloaiAdapter;
import com.sict.lnhi.corona.adapter.ThuonghieuAdapter;
import com.sict.lnhi.corona.model.Giohang;
import com.sict.lnhi.corona.model.Menu;
import com.sict.lnhi.corona.model.Theloai;
import com.sict.lnhi.corona.model.Thuonghieu;
import com.sict.lnhi.corona.ultil.CheckConnection;
import com.sict.lnhi.corona.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtsearch;
    DrawerLayout drawerLayout;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ArrayList<Menu> menuArrayList;
    MenuAdapter adapter;

    ArrayList<Theloai> theloaiArrayList;
    TheloaiAdapter adaptertl;
    ListView listViewmn;

    ArrayList<Thuonghieu> thuonghieuArrayList;
    ThuonghieuAdapter thuonghieuAdapter;
    RecyclerView recyclerViewth;

    int id = 0;
    String tentl = " ";
    String anhtl = " ";

    public static String sdt;
    public static ArrayList<Giohang> manggiohang;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AnhXa();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            QuangCao();
            DataMenu();
            //ActionToolbar();
            DataTheloai();
            SkSearch();
            SkMenu();
            DataThuonghieu();

        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "bạn hãy kiểm tra lại kết nối");
            finish();

        }

    }

    private void DataThuonghieu() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.thuonghieu,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            int id = 0;
                            String ten = "";
                            String anh = "";
                            String mota = "";
                            String diachi = "";
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    ten = jsonObject.getString("ten");
                                    anh = jsonObject.getString("anh");
                                    mota = jsonObject.getString("mota");
                                    diachi = jsonObject.getString("diachi");
                                    thuonghieuArrayList.add(new Thuonghieu(id, ten, anh, mota, diachi));
                                    thuonghieuAdapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                });
        requestQueue.add(jsonArrayRequest);
    }



    private void SkSearch() {
        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


            @Override
            public void afterTextChanged(Editable editable) {
                String s = edtsearch.getText().toString();
                adapter.searchMenu(s);

            }
        });

    }
    private void DataTheloai() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urltl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    tentl = jsonObject.getString("ten");
                                    anhtl = jsonObject.getString("anh");
                                    theloaiArrayList.add(new Theloai(id, tentl, anhtl));
                                    adaptertl.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                },
                new Response.ErrorListener(){
                    /**
                     * Callback method that an error has been occurred with the provided error code and optional
                     * user-readable message.
                     *
                     * @param error
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CheckConnection.showToast_Short(getApplicationContext(), error.toString());
                    }

                    public void afterTextChanged(Editable editable) {
                        String s = edtsearch.getText().toString();
                        adapter.searchMenu(s);

                    }
                });
        requestQueue.add(jsonArrayRequest);

    }

    private void SkMenu() {
        listViewmn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                switch (i){
                    case 0:
                        Intent inten = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(inten);
                        break;
                    case  1:
                        Intent intent = new Intent(HomeActivity.this, DouongActivity.class);
                        intent.putExtra("idtl", theloaiArrayList.get(i).getId());
                        startActivity(intent);
                        break;
                    case  2:
                        Intent intent1 = new Intent(HomeActivity.this, AnvatActivity.class);
                        intent1.putExtra("idtl", theloaiArrayList.get(i).getId());
                        startActivity(intent1);
                        break;
                    case  3:

                        startActivity(new Intent(HomeActivity.this, DonhangActivity.class));
                        break;
                }
            }
        });
    }
//    private void ActionToolbar() {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
//
//
//
//    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    private void DataMenu() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlmenu,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response !=null){
                            int id = 0;
                            String tenmon = "";
                            Integer gia = 0;
                            String mota = "";
                            String anh = "";
                            int idtheloai = 0;
                            int idth = 0;
                            for (int i = 0 ; i<response.length(); i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    tenmon = jsonObject.getString("tenmon");
                                    gia = jsonObject.getInt("gia");
                                    mota = jsonObject.getString("mota");
                                    anh = jsonObject.getString("anh");
                                    idtheloai = jsonObject.getInt("idtheloai");
                                    idth = jsonObject.getInt("idth");
                                    menuArrayList.add(new Menu(id,tenmon,mota,anh,gia,idtheloai,idth));
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonArrayRequest);
    }


    private void QuangCao() {
        ArrayList<String> qclist = new ArrayList<>();
        qclist.add("https://images.foody.vn/res/g69/682001/prof/s1242x600/foody-upload-api-foody-mobile-bonpas-bakery-coffee-180620174728.jpg");
        //qclist.add("https://https://www.highlandscoffee.com.vn/vnt_upload/weblink/banner-01.png");
        //qclist.add("https://static2.yan.vn/YanThumbNews/2167221/201908/720x400_0d419364-a5dd-43af-b56f-ab1dcb5ed055.jpg");
        qclist.add("https://static2.yan.vn/YanThumbNews/2167221/201908/720x400_0d419364-a5dd-43af-b56f-ab1dcb5ed055.jpg");
        qclist.add("http://micaysasin.vn/upload/hinhanh/untitled-5-7866.png");
        for (int i = 0 ; i<qclist.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(qclist.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }





    private void AnhXa() {

        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        edtsearch = (EditText) findViewById(R.id.searchmn);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        listViewmn = (ListView) findViewById(R.id.listview);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewfipper);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        menuArrayList = new ArrayList<>();
        adapter = new MenuAdapter(getApplicationContext(), menuArrayList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        //
       // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        //
        recyclerView.setAdapter(adapter);

        recyclerViewth = (RecyclerView) findViewById(R.id.recycleviewth);
        thuonghieuArrayList = new ArrayList<>();
        thuonghieuAdapter = new ThuonghieuAdapter(getApplicationContext(), thuonghieuArrayList);
        recyclerViewth.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        recyclerViewth.setAdapter(thuonghieuAdapter);

        theloaiArrayList = new ArrayList<>();
        theloaiArrayList.add(0, new Theloai(0, "Home","https://image.flaticon.com/icons/png/512/25/25694.png"));
        adaptertl = new TheloaiAdapter(theloaiArrayList, getApplicationContext());
        listViewmn.setAdapter(adaptertl);

        if (manggiohang != null){

        }else {
            manggiohang = new ArrayList<>();
        }
    }

    @Override
    //tạo menu
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.giohang:
                Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
                startActivity(intent);

                //sữa
                Toast.makeText(this, "Giỏ hàng của bạn", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, GiohangActivity.class));
                break;
            case R.id.setting:
                break;
            case R.id.donhang:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}