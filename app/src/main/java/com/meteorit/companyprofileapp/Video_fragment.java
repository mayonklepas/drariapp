package com.meteorit.companyprofileapp;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeIntents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Minami on 24/07/2017.
 */

public class Video_fragment extends Fragment {

    RecyclerView rv;
    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> link=new ArrayList<>();
    ArrayList<String> judul=new ArrayList<>();
    RecyclerView.LayoutManager layman;
    RecyclerView.Adapter adapter;
    Spinner sp;
    ProgressBar pb;
    ArrayList<kategorientity> arrlist=new ArrayList<>();
    String id_kategori;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.video_fragment,container,false);
        pb=(ProgressBar) v.findViewById(R.id.pb);
        rv=(RecyclerView) v.findViewById(R.id.rv);
        layman=new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layman);
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        sp=(Spinner) v.findViewById(R.id.kategori);
        adapter=new Video_adapter(id,link,judul,getActivity());
        if(YouTubeIntents.isYouTubeInstalled(getActivity())){
            loadspinner();
        }else{
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.youtube")));
            } catch (Exception ex) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.youtube")));
            }
        }
        return v;
    }


    public void loadspinner(){
        RequestQueue rq= Volley.newRequestQueue(this.getActivity());
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/rest/video.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja=new JSONArray(response);
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                arrlist.add(new kategorientity(jo.getString("id"),jo.getString("kategori")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        rq.add(sr);
        rq.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {

            @Override
            public void onRequestFinished(Request<Object> request) {
                pb.setVisibility(View.GONE);
                ArrayAdapter<kategorientity> adapter=new ArrayAdapter<kategorientity>(getActivity(),
                        android.R.layout.simple_spinner_item,arrlist);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp.setAdapter(adapter);
            }
        });

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kategorientity kat=(kategorientity) parent.getSelectedItem();
                id_kategori=kat.getId();
                loadata(id_kategori);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               kategorientity kat=(kategorientity) parent.getSelectedItem();
                id_kategori=kat.getId();
                System.out.println(id_kategori);
                loadata(id_kategori);
            }
        });
    }

    public void loadata(String id_kategori){
        id.clear();
        judul.clear();
        link.clear();
        adapter.notifyDataSetChanged();
        pb.setVisibility(View.VISIBLE);
        RequestQueue rq= Volley.newRequestQueue(this.getActivity());
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/rest/video.php?id_kategori="+id_kategori,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja=new JSONArray(response);
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                id.add(jo.getInt("id"));
                                link.add(jo.getString("link"));
                                judul.add(jo.getString("judul"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        rv.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        rq.add(sr);
        rq.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {

            @Override
            public void onRequestFinished(Request<Object> request) {
                pb.setVisibility(View.GONE);
            }
        });
    }

    public class kategorientity{
        String id,kategori;

        public kategorientity(String id, String kategori) {
            this.id = id;
            this.kategori = kategori;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKategori() {
            return kategori;
        }

        public void setKategori(String kategori) {
            this.kategori = kategori;
        }

        @Override
        public String toString() {
            return kategori;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Artikel_fragment.kategorientity){
                Artikel_fragment.kategorientity c = (Artikel_fragment.kategorientity) obj;
                if(c.getKategori().equals(kategori) && c.getId()==id ) return true;
            }

            return false;
        }
    }

}
