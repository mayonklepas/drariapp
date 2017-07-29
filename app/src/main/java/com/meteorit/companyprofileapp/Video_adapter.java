package com.meteorit.companyprofileapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

/**
 * Created by Minami on 24/07/2017.
 */

public class Video_adapter extends RecyclerView.Adapter<Video_adapter.Holder> {

    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<String> link=new ArrayList<>();
    ArrayList<String> judul=new ArrayList<>();
    Context ct;

    public Video_adapter(ArrayList<Integer> id, ArrayList<String> link, ArrayList<String> judul, Context ct) {
        this.id = id;
        this.link = link;
        this.judul = judul;
        this.ct = ct;
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView judul;
        public YouTubeThumbnailView link;
        public Holder(View itemView) {
            super(itemView);
            judul=(TextView) itemView.findViewById(R.id.judul);
            link=(YouTubeThumbnailView) itemView.findViewById(R.id.gambar);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.video_adapter,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.judul.setText(judul.get(position));
        holder.link.initialize("AIzaSyBK-6flB7-yTDUpVOSmJ0AFdNLm8nyfMLQ", new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(link.get(position).split("/")[4]);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                        Toast.makeText(ct, errorReason.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
            }
        });
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ct,
                        "AIzaSyBK-6flB7-yTDUpVOSmJ0AFdNLm8nyfMLQ",link.get(position).split("/")[4],0,true,false);
                ct.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return id.size();
    }


}
