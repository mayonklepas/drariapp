package com.meteorit.companyprofileapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class Splashscreen_Activity extends AppCompatActivity {
    CircleImageView splsahimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_activity);
        splsahimg=(CircleImageView) findViewById(R.id.splashimg);
        loadata();
    }


    public void loadata(){
        RequestQueue rq= Volley.newRequestQueue(this);
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/rest/home.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jo=new JSONObject(response);
                            JSONArray jatips=jo.getJSONArray("tips");
                            for (int i = 0; i < jatips.length() ; i++) {
                                JSONObject jotips=jatips.getJSONObject(i);
                                Config.judultips=jotips.getString("judul");
                                Config.kontentips=jotips.getString("konten");
                                Config.gambartips=Config.url+"/src/gambar/"+jotips.getString("gambar");
                                Config.tanggaltips=jotips.getString("tanggal");
                            }

                            JSONArray jaartikel=jo.getJSONArray("artikel");
                            for (int i = 0; i < jaartikel.length() ; i++) {
                                JSONObject joartikel=jaartikel.getJSONObject(i);
                                Config.judulartikel=joartikel.getString("judul");
                                Config.kontenartikel=joartikel.getString("konten");
                                Config.gambarartikel=Config.url+"/src/gambar/"+joartikel.getString("gambar");
                                Config.tanggaltips=joartikel.getString("tanggal");
                            }

                            JSONArray japublish=jo.getJSONArray("publish");
                            for (int i = 0; i < japublish.length() ; i++) {
                                JSONObject jopublish=japublish.getJSONObject(i);
                                Config.judulpublish=jopublish.getString("judul");
                                Config.kontenpublish=jopublish.getString("konten");
                                Config.gambarpublish=Config.url+"/src/gambar/"+jopublish.getString("gambar");
                                Config.tanggaltips=jopublish.getString("tanggal");
                                Config.filependukung=Config.url+"/src/gambar/"+jopublish.getString("file_pendukung");
                            }

                            JSONArray jaslider=jo.getJSONArray("slider");
                            for (int i = 0; i < jaslider.length() ; i++) {
                                JSONObject joslider=jaslider.getJSONObject(i);
                                Config.gambarslide1=Config.url+"/src/gambar/"+joslider.getString("gambar1");
                                Config.gambarslide2=Config.url+"/src/gambar/"+joslider.getString("gambar2");
                                Config.gambarslide3=Config.url+"/src/gambar/"+joslider.getString("gambar3");
                            }

                            JSONArray japrofile=jo.getJSONArray("profile");
                            for (int i = 0; i < japrofile.length() ; i++) {
                                JSONObject joprofile=japrofile.getJSONObject(i);
                                Config.namaprofile=joprofile.getString("nama");
                                Config.alamatprofile=joprofile.getString("alamat");
                                Config.nohpprofile=joprofile.getString("nohp");
                                Config.emailprofile=joprofile.getString("email");
                                Config.keteranganprofile=joprofile.getString("keterangan");
                                Config.gambarprofile=Config.url+"/src/gambar/"+joprofile.getString("gambar");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Splashscreen_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        rq.add(sr);
        rq.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {

            @Override
            public void onRequestFinished(Request<Object> request) {
                Glide.with(Splashscreen_Activity.this).
                        load(Config.gambarprofile).dontAnimate().
                        placeholder(R.drawable.placeholder).
                        diskCacheStrategy(DiskCacheStrategy.ALL).
                        into(splsahimg);
                task t=new task();
                t.execute();
            }
        });
    }

    public class task extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i=new Intent(Splashscreen_Activity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

}
