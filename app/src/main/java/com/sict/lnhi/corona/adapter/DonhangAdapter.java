package com.sict.lnhi.corona.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;


import com.sict.lnhi.corona.R;
import com.sict.lnhi.corona.model.Donhang;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class DonhangAdapter extends BaseAdapter {

    ArrayList<Donhang> donhangArrayList;
    Context context;

    public DonhangAdapter(ArrayList<Donhang> donhangArrayList, Context context) {
        this.donhangArrayList = donhangArrayList;
        this.context = context;
    }

    //    public TheloaiAdapter(ArrayList<Theloai> theloaiArrayList, Context context, Layout layout) {
//        this.theloaiArrayList = theloaiArrayList;
//        this.context = context;
//        this.layout = layout;
//    }

    @Override
    public int getCount() {
        return donhangArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return donhangArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txtten, txtsl, txtgia;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_donhanguser, null);
            holder.txtten = view.findViewById(R.id.tenmonan);
            holder.txtsl = view.findViewById(R.id.soluong);
            holder.txtgia = view.findViewById(R.id.giatien);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Donhang donhang = (Donhang) getItem(i);
        holder.txtten.setText(donhang.getTenmonan());
        holder.txtsl.setText("Số lượng: "+donhang.getSoluong());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgia.setText("Giá: " +decimalFormat.format(donhang.getGiatien())+" Đ");
        return view;

    }
}