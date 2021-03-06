package com.meteorit.companyprofileapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Artikeldetail_Activity extends AppCompatActivity {
    TextView judul,tanggal,konten;
    ImageView app_img_bar;
    FloatingActionButton btshare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artikeldetail_activity);
        judul=(TextView) findViewById(R.id.judul);
        tanggal=(TextView) findViewById(R.id.tanggal);
        konten=(TextView) findViewById(R.id.konten);
        app_img_bar=(ImageView) findViewById(R.id.app_bar_image);
        btshare=(FloatingActionButton) findViewById(R.id.btshare);
        Bundle ex=getIntent().getExtras();
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
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, judul.getText()+"\n"+tanggal.getText()+"\n\n"+konten.getText()+
                        ".\n\nBy Dr Ari Arumugum, MD, FACS.");
                startActivity(Intent.createChooser(sharingIntent,"Share Ke"));
            }
        });
    }

}
