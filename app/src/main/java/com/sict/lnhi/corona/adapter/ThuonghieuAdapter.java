package com.sict.lnhi.corona.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sict.lnhi.corona.R;
import com.sict.lnhi.corona.activity.ThuonghieuActivity;
import com.sict.lnhi.corona.model.Thuonghieu;
import com.sict.lnhi.corona.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ThuonghieuAdapter extends RecyclerView.Adapter<ThuonghieuAdapter.ItemHolder> {

    Context context;
    ArrayList<Thuonghieu> thuonghieuArrayList;

    public ThuonghieuAdapter(Context context, ArrayList<Thuonghieu> thuonghieuArrayList) {
        this.context = context;
        this.thuonghieuArrayList = thuonghieuArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_thuonghieu, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Thuonghieu thuonghieu = thuonghieuArrayList.get(position);
        Picasso.with(context).load(thuonghieu.getAnh())
                .placeholder(R.drawable.logo)
                .error(R.drawable.login)
                .into(holder.imgthuonghieu);
    }

    @Override
    public int getItemCount() {
        return thuonghieuArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgthuonghieu;

        public ItemHolder(View view){
            super(view);
            imgthuonghieu = (ImageView) view.findViewById(R.id.imgth);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ThuonghieuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("idth", thuonghieuArrayList.get(getPosition()).getId());
                    CheckConnection.showToast_Short(context, thuonghieuArrayList.get(getPosition()).getTen());
                    context.startActivity(intent);
                }
            });
        }
    }
}