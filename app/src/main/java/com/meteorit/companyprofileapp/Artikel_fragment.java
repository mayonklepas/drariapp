package com.meteorit.companyprofileapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Minami on 24/07/2017.
 */

public class Artikel_fragment extends Fragment {
    RecyclerView rv;
    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> tanggal=new ArrayList<>();
    ArrayList<String> gambar=new ArrayList<>();
    ArrayList<String> judul=new ArrayList<>();
    ArrayList<String> konten=new ArrayList<>();
    RecyclerView.LayoutManager layman;
    RecyclerView.Adapter adapter;
    ProgressBar pb;
    String id_kategori;
    Spinner sp;
    ArrayList<kategorientity> arrlist=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.artikel_fragment,container,false);
        pb=(ProgressBar) v.findViewById(R.id.pb);
        rv=(RecyclerView) v.findViewById(R.id.rv);
        layman=new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layman);
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter=new Artikel_adapter(id,gambar,judul,konten,tanggal,getActivity());
        sp=(Spinner) v.findViewById(R.id.kategori);
        loadspinner();
        return v;
    }


    public void loadspinner(){
        RequestQueue rq= Volley.newRequestQueue(this.getActivity());
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/rest/artikel.php",
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
                loadata(id_kategori);
            }
        });
    }

    public void loadata(String id_kategori){
        id.clear();
        judul.clear();
        konten.clear();
        gambar.clear();
        adapter.notifyDataSetChanged();
        pb.setVisibility(View.VISIBLE);
        RequestQueue rq= Volley.newRequestQueue(this.getActivity());
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/rest/artikel.php?id_kategori="+id_kategori,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja=new JSONArray(response);
                            for (int i = 0; i < ja.length() ; i++) {
                                JSONObject jo=ja.getJSONObject(i);
                                id.add(jo.getInt("id"));
                                tanggal.add(jo.getString("tanggal"));
                                gambar.add(Config.url+"/src/gambar/"+jo.getString("gambar"));
                                judul.add(jo.getString("judul"));
                                konten.add(jo.getString("konten"));
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
            if(obj instanceof kategorientity){
                kategorientity c = (kategorientity) obj;
                if(c.getKategori().equals(kategori) && c.getId()==id ) return true;
            }

            return false;
        }
    }
}
