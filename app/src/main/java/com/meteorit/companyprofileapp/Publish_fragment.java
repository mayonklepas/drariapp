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

/**
 * Created by Minami on 24/07/2017.
 */

public class Publish_fragment extends Fragment {

    RecyclerView rv;
    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> tanggal=new ArrayList<>();
    ArrayList<String> gambar=new ArrayList<>();
    ArrayList<String> judul=new ArrayList<>();
    ArrayList<String> konten=new ArrayList<>();
    ArrayList<String> file_pendukung=new ArrayList<>();
    RecyclerView.LayoutManager layman;
    RecyclerView.Adapter adapter;
    ProgressBar pb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.publish_fragment,container,false);
        pb=(ProgressBar) v.findViewById(R.id.pb);
        rv=(RecyclerView) v.findViewById(R.id.rv);
        layman=new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layman);
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter=new Publish_adapter(id,gambar,judul,konten,tanggal,file_pendukung,getActivity());
        loadata();
        return v;
    }


    public void loadata(){
        id.clear();
        judul.clear();
        konten.clear();
        gambar.clear();
        adapter.notifyDataSetChanged();
        pb.setVisibility(View.VISIBLE);
        RequestQueue rq= Volley.newRequestQueue(this.getActivity());
        StringRequest sr=new StringRequest(Request.Method.GET, Config.url+"/rest/publish.php?",
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
                                file_pendukung.add(Config.url+"/src/gambar/"+jo.getString("file_pendukung"));
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
}
