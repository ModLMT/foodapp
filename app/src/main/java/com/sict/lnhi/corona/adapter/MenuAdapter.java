package com.sict.lnhi.corona.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sict.lnhi.corona.R;
import com.sict.lnhi.corona.activity.ChitietActivity;
import com.sict.lnhi.corona.model.Menu;
import com.sict.lnhi.corona.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ItemHolder> {
    Context context;
    ArrayList<Menu> menuArrayList;

    public MenuAdapter(Context context, ArrayList<Menu> menuArrayList) {
        this.context = context;
        this.menuArrayList = menuArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_nhi, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Menu menu = menuArrayList.get(position);
        holder.txtten.setText(menu.getTenmon());
        Picasso.with(context).load(menu.getAnh()).placeholder(R.drawable.logo).error(R.drawable.login).into(holder.imganh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgia.setText("Giá: " +decimalFormat.format(menu.getGia())+ "Đ");
    }

    @Override
    public int getItemCount() {
        return menuArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imganh;
        public TextView txtten, txtgia;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imganh = itemView.findViewById(R.id.imganh);
            txtten = itemView.findViewById(R.id.txtten);
            txtgia = itemView.findViewById(R.id.txtgia);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChitietActivity.class);
                    intent.putExtra("thongtinmonan", menuArrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.showToast_Short(context, menuArrayList.get(getPosition()).getTenmon());
                    context.startActivity(intent);
                }
            });
        }
    }
    public void searchMenu(String s){
        s = s.toUpperCase();
        int k = 0;
        for (int i = 0 ; i<menuArrayList.size(); i++){
            Menu menu = menuArrayList.get(i);
            String ten = menu.getTenmon().toUpperCase();
            if (ten.indexOf(s)>=0){
                menuArrayList.set(i, menuArrayList.get(k));
                menuArrayList.set(k, menu);
                k++;
            }
        }
        notifyDataSetChanged();
    }
}