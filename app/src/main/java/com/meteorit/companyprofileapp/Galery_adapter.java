package com.meteorit.companyprofileapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Minami on 24/07/2017.
 */

public class Galery_adapter extends RecyclerView.Adapter<Galery_adapter.Holder> {

    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> gambar=new ArrayList<>();
    ArrayList<String> judul=new ArrayList<>();
    Context ct;

    public Galery_adapter(ArrayList<Integer> id, ArrayList<String> gambar, ArrayList<String> judul,Context ct) {
        this.id = id;
        this.gambar = gambar;
        this.judul = judul;
        this.ct = ct;
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView judul;
        public ImageView gambar;
        public TextView readmore;
        public Holder(View itemView) {
            super(itemView);
            judul=(TextView) itemView.findViewById(R.id.judul);
            gambar=(ImageView) itemView.findViewById(R.id.gambar);
            readmore=(TextView) itemView.findViewById(R.id.readmore);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.galery_adapter,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        String detailkata="";
        if(judul.get(position).length() < 100){
            detailkata=judul.get(position);
        }else{
            detailkata=judul.get(position).substring(0,100)+"...";
        }
        holder.judul.setText(detailkata);
        Glide.with(ct).
                load(gambar.get(position)).
                centerCrop().crossFade().
                placeholder(R.drawable.placeholder).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(holder.gambar);
        System.out.println(gambar.get(position));
        holder.gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ct);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater li = dialog.getLayoutInflater();
                View inflate = li.inflate(R.layout.custompopup, null);
                dialog.setContentView(inflate);
                ImageView imgpop = (ImageView) inflate.findViewById(R.id.popupbg);
                imgpop.setImageDrawable(holder.gambar.getDrawable());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return id.size();
    }


}
