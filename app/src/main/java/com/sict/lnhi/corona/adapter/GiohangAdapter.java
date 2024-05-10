package com.sict.lnhi.corona.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sict.lnhi.corona.R;
import com.sict.lnhi.corona.activity.GiohangActivity;
import com.sict.lnhi.corona.activity.HomeActivity;
import com.sict.lnhi.corona.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arraygiohang;

    public GiohangAdapter(Context context, ArrayList<Giohang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int i) {
        return arraygiohang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txttengiohang, txtgiagh;
        public ImageView imggh;
        public Button btnt, btngt, btnc;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang, null);
            viewHolder.txttengiohang = (TextView) view.findViewById(R.id.tengiohang);
            viewHolder.txtgiagh = (TextView) view.findViewById(R.id.txtgiagh);
            viewHolder.imggh = (ImageView) view.findViewById(R.id.imggiohang);
            viewHolder.btnt = (Button) view.findViewById(R.id.btnt);
            viewHolder.btngt = (Button) view.findViewById(R.id.btngiatri);
            viewHolder.btnc = (Button) view.findViewById(R.id.btnc);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Giohang giohang = (Giohang) getItem(i);
        viewHolder.txttengiohang.setText(giohang.getTenma());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagh.setText(decimalFormat.format(giohang.getGia())+ " Đ");
        Picasso.with(context).load(giohang.getAnh())
                .placeholder(R.drawable.logo)
                .error(R.drawable.login)
                .into(viewHolder.imggh);
        viewHolder.btngt.setText(giohang.getSoluong() + "");
        int sl = Integer.parseInt(viewHolder.btngt.getText().toString());
        if (sl >= 10){
            viewHolder.btnc.setVisibility(View.INVISIBLE);
            viewHolder.btnt.setVisibility(View.VISIBLE);
        }else if (sl <=1){
            viewHolder.btnt.setVisibility(View.INVISIBLE);
        }else if (sl >=1){
            viewHolder.btnt.setVisibility(View.VISIBLE);
            viewHolder.btnc.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btngt.getText().toString()) +1;
                int slhientai = HomeActivity.manggiohang.get(i).getSoluong();
                long giaht = HomeActivity.manggiohang.get(i).getGia();
                HomeActivity.manggiohang.get(i).setSoluong(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slhientai;
                HomeActivity.manggiohang.get(i).setGia(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagh.setText(decimalFormat.format(giamoinhat)+ " Đ");
                GiohangActivity.EventUltil();
                if (slmoinhat > 9){
                    finalViewHolder.btnc.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnt.setVisibility(View.VISIBLE);
                    finalViewHolder.btngt.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btnt.setVisibility(View.VISIBLE);
                    finalViewHolder.btnc.setVisibility(View.VISIBLE);
                    finalViewHolder.btngt.setText(String.valueOf(slmoinhat));
                }
            }
        });
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.btnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder1.btngt.getText().toString()) -1;
                int slhientai = HomeActivity.manggiohang.get(i).getSoluong();
                long giaht = HomeActivity.manggiohang.get(i).getGia();
                HomeActivity.manggiohang.get(i).setSoluong(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slhientai;
                HomeActivity.manggiohang.get(i).setGia(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder1.txtgiagh.setText(decimalFormat.format(giamoinhat)+ " Đ");
                GiohangActivity.EventUltil();
                if (slmoinhat < 2){
                    finalViewHolder1.btnt.setVisibility(View.INVISIBLE);
                    finalViewHolder1.btnc.setVisibility(View.VISIBLE);
                    finalViewHolder1.btngt.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder1.btnt.setVisibility(View.VISIBLE);
                    finalViewHolder1.btnc.setVisibility(View.VISIBLE);
                    finalViewHolder1.btngt.setText(String.valueOf(slmoinhat));
                }

            }
        });
        return view;
    }
}