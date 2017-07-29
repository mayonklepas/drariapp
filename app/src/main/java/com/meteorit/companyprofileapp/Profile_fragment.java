package com.meteorit.companyprofileapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Minami on 24/07/2017.
 */

public class Profile_fragment extends Fragment {
    TextView keterangan,alamat,email,nohp;
    ImageView app_bar_image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.profile_fragment,container,false);
        keterangan=(TextView) v.findViewById(R.id.keterangan);
        alamat=(TextView) v.findViewById(R.id.alamat);
        email=(TextView) v.findViewById(R.id.email);
        nohp=(TextView) v.findViewById(R.id.nohp);
        app_bar_image=(ImageView) v.findViewById(R.id.app_bar_image);
        keterangan.setText(Config.keteranganprofile);
        alamat.setText(Config.alamatprofile);
        email.setText(Config.emailprofile);
        nohp.setText(Config.nohpprofile);
        Glide.with(getActivity()).
                load(Config.gambarprofile).
                placeholder(R.drawable.placeholder).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                crossFade().fitCenter().into(app_bar_image);
        return v;
    }
}
