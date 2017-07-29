package com.meteorit.companyprofileapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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

import static android.R.anim.slide_in_left;

/**
 * Created by Minami on 24/07/2017.
 */

public class Home_fragment extends Fragment{

    ImageView imgtips,imgartikel,imgpublish,img1,img2,img3;
    TextView tips,artikel,publish,detailtips,detailpublish,detailartikel,judultips,judulartikel,judulpublish;
    ViewFlipper slider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.home_fragment,container,false);
        imgtips=(ImageView) v.findViewById(R.id.imgtips);
        imgartikel=(ImageView) v.findViewById(R.id.imgartikel);
        imgpublish=(ImageView) v.findViewById(R.id.imgpublish);
        img1=(ImageView) v.findViewById(R.id.imgsatu);
        img2=(ImageView) v.findViewById(R.id.imgdua);
        img3=(ImageView) v.findViewById(R.id.imgtiga);
        tips=(TextView) v.findViewById(R.id.tips);
        artikel=(TextView) v.findViewById(R.id.artikel);
        publish=(TextView) v.findViewById(R.id.publish);

        judultips=(TextView) v.findViewById(R.id.judultips);
        judulartikel=(TextView) v.findViewById(R.id.judulartikel);
        judulpublish=(TextView) v.findViewById(R.id.judulpublish);

        detailtips=(TextView) v.findViewById(R.id.detailtips);
        detailartikel=(TextView) v.findViewById(R.id.detailartikel);
        detailpublish=(TextView) v.findViewById(R.id.detailpublish);

        Glide.with(this).
                load(Config.gambarslide1).
                centerCrop().crossFade().
                placeholder(R.drawable.placeholder).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(img1);

        Glide.with(this).
                load(Config.gambarslide2).
                centerCrop().crossFade().
                placeholder(R.drawable.placeholder).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(img2);

        Glide.with(this).
                load(Config.gambarslide3).
                centerCrop().crossFade().
                placeholder(R.drawable.placeholder).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(img3);


        String subtips="";
        if(Config.kontentips.length() > 300){
            subtips=Config.kontentips.substring(0,300)+"...";
        }else{
            subtips=Config.kontentips;
        }

        String subartikel="";
        if(Config.kontenartikel.length() > 300){
            subartikel=Config.kontenartikel.substring(0,300)+"...";
        }else{
            subartikel=Config.kontenartikel;
        }

        String subpublish="";
        if(Config.kontenpublish.length() > 300){
            subpublish=Config.kontenpublish.substring(0,300)+"...";
        }else{
            subpublish=Config.kontenpublish;
        }

        judultips.setText(Config.judultips);
        judulartikel.setText(Config.judulartikel);
        judulpublish.setText(Config.judulpublish);

        tips.setText(subtips);
        artikel.setText(subartikel);
        publish.setText(subpublish);


        Glide.with(this).
                load(Config.gambartips).
                centerCrop().crossFade().
                placeholder(R.drawable.placeholder).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imgtips);


        Glide.with(this).
                load(Config.gambarartikel).
                centerCrop().crossFade().
                placeholder(R.drawable.placeholder).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imgartikel);


        Glide.with(this).
                load(Config.gambarpublish).
                centerCrop().crossFade().
                placeholder(R.drawable.placeholder).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imgpublish);



        slider=(ViewFlipper) v.findViewById(R.id.slider);
        slider.setAutoStart(true);
        slider.setFlipInterval(5000);
        slider.setInAnimation(AnimationUtils.loadAnimation(getActivity(), slide_in_left));
        slider.startFlipping();
        slider.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                slider.showNext();
            }
        });

        detailtips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),Homedetail_Activity.class);
                i.putExtra("judul",Config.judultips);
                i.putExtra("konten",Config.kontentips);
                i.putExtra("gambar",Config.gambartips);
                i.putExtra("tanggal",Config.tanggaltips);
                i.putExtra("ispublish",false);
                i.putExtra("header","Tips Hari ini");
                startActivity(i);
            }
        });

        detailartikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),Homedetail_Activity.class);
                i.putExtra("judul",Config.judulartikel);
                i.putExtra("konten",Config.kontenartikel);
                i.putExtra("gambar",Config.gambarartikel);
                i.putExtra("tanggal",Config.tanggalartikel);
                i.putExtra("ispublish",false);
                i.putExtra("header","Artikel Terbaru");
                startActivity(i);
            }
        });

        detailpublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),Homedetail_Activity.class);
                i.putExtra("judul",Config.judulpublish);
                i.putExtra("konten",Config.kontenpublish);
                i.putExtra("gambar",Config.gambarpublish);
                i.putExtra("tanggal",Config.tanggalpublish);
                i.putExtra("ispublish",true);
                i.putExtra("header","Publish Terbaru");
                startActivity(i);
            }
        });

        return v;
    }



}
