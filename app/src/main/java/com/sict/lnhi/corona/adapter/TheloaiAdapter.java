package com.sict.lnhi.corona.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sict.lnhi.corona.R;
import com.sict.lnhi.corona.model.Theloai;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TheloaiAdapter extends BaseAdapter {

    ArrayList<Theloai> theloaiArrayList;
    Context context;

    public TheloaiAdapter(ArrayList<Theloai> theloaiArrayList, Context context) {
        this.theloaiArrayList = theloaiArrayList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return theloaiArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return theloaiArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txtten;
        ImageView imgtl;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.theloai, null);
            holder.txtten = view.findViewById(R.id.tentl);
            holder.imgtl = view.findViewById(R.id.imganhtl);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Theloai theloai = (Theloai) getItem(i);
        holder.txtten.setText(theloai.getTen());
        Picasso.with(context).load(theloai.getAnh()).placeholder(R.drawable.logo).error(R.drawable.login).into(holder.imgtl);
        return view;

    }
}