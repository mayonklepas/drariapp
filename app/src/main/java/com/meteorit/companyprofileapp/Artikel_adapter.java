package com.meteorit.companyprofileapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Minami on 24/07/2017.
 */

public class Artikel_adapter extends RecyclerView.Adapter<Artikel_adapter.Holder> {

    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> tanggal=new ArrayList<>();
    ArrayList<String> gambar=new ArrayList<>();
    ArrayList<String> judul=new ArrayList<>();
    ArrayList<String> konten=new ArrayList<>();
    Context ct;

    public Artikel_adapter(ArrayList<Integer> id, ArrayList<String> gambar, ArrayList<String> judul, ArrayList<String> konten,ArrayList<String> tanggal, Context ct) {
        this.id = id;
        this.gambar = gambar;
        this.judul = judul;
        this.konten = konten;
        this.tanggal=tanggal;
        this.ct = ct;
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView judul;
        public TextView konten;
        public ImageView gambar;
        public TextView readmore;
        public Holder(View itemView) {
            super(itemView);
            judul=(TextView) itemView.findViewById(R.id.judul);
            konten=(TextView) itemView.findViewById(R.id.konten);
            gambar=(ImageView) itemView.findViewById(R.id.gambar);
            readmore=(TextView) itemView.findViewById(R.id.readmore);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.artikel_adapter,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.judul.setText(judul.get(position));
        String detailkata="";
        final String allkata=konten.get(position);
        final String tanggalpost=tanggal.get(position);
        if(konten.get(position).length() < 300){
            detailkata=konten.get(position);
        }else{
            detailkata=konten.get(position).substring(0,300)+"...";
        }
        holder.konten.setText(detailkata);
        Glide.with(ct).
                load(gambar.get(position)).
                centerCrop().crossFade().
                placeholder(R.drawable.placeholder).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(holder.gambar);
        holder.readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ct,Artikeldetail_Activity.class);
                i.putExtra("tanggal",tanggalpost);
                i.putExtra("judul",judul.get(position));
                i.putExtra("konten",allkata);
                i.putExtra("gambar", gambar.get(position));
                ct.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return id.size();
    }


}
