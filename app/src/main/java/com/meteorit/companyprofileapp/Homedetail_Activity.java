package com.meteorit.companyprofileapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Homedetail_Activity extends AppCompatActivity {
    TextView judul,tanggal,konten;
    ImageView app_img_bar;
    FloatingActionButton btshare;
    Button download;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homedetail_activity);
        judul=(TextView) findViewById(R.id.judul);
        tanggal=(TextView) findViewById(R.id.tanggal);
        konten=(TextView) findViewById(R.id.konten);
        app_img_bar=(ImageView) findViewById(R.id.app_bar_image);
        btshare=(FloatingActionButton) findViewById(R.id.btshare);
        download=(Button) findViewById(R.id.download);
        tb=(Toolbar) findViewById(R.id.toolbar);
        final Bundle ex=getIntent().getExtras();
        tb.setTitle(ex.getString("header"));
        if(ex.getBoolean("ispublish")==true){
            download.setVisibility(View.VISIBLE);
        }else{
            download.setVisibility(View.INVISIBLE);
        }
        String stanggal="";
        try {
            Date dt=new SimpleDateFormat("yyyy-MM-dd").parse(ex.getString("tanggal"));
            stanggal=new SimpleDateFormat("EEE dd MMM yyyy").format(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        judul.setText(ex.getString("judul"));
        tanggal.setText(stanggal);
        konten.setText(ex.getString("konten"));
        Glide.with(this).
                load(ex.getString("gambar")).
                centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).
                crossFade().into(app_img_bar);
        btshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, judul.getText()+"\n"+tanggal.getText()+"\n\n"+konten.getText()+
                        ".\n\nBy Dr Ari Arumugum, MD, FACS.");
                startActivity(Intent.createChooser(sharingIntent,"Share Ke"));
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Config.filependukung));
                startActivity(browserIntent);
            }
        });
    }

}
