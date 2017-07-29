package com.meteorit.companyprofileapp;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Minami on 24/07/2017.
 */

public class Kirimpesan_fragment extends Fragment {

    TextView nama,nohp,pesan;
    Button kirim;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.kirimpesan_fragment,container,false);
        nama=(TextView) v.findViewById(R.id.nama);
        nohp=(TextView) v.findViewById(R.id.nohp);
        pesan=(TextView) v.findViewById(R.id.pesan);
        kirim=(Button) v.findViewById(R.id.kirim);
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent mEmail = new Intent(Intent.ACTION_SEND);
                mEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{Config.emailprofile});
                mEmail.putExtra(Intent.EXTRA_SUBJECT, "Pesan Dari Aplikasi dengan No Handphone Pengirim: "+nohp.getText().toString());
                mEmail.putExtra(Intent.EXTRA_TEXT, pesan.getText().toString());
                mEmail.setType("message/rfc822");
                startActivity(Intent.createChooser(mEmail, "Pilih Email yang Digunakan"));*/
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + Config.emailprofile));
                startActivity(intent);
            }
        });
        return v;
    }
}
