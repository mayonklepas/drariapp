package com.meteorit.companyprofileapp;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CircleImageView imgprofile;
    TextView slidename,slideemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView nv = (NavigationView) findViewById(R.id.nav_view);
        View hview = nv.getHeaderView(0);
        imgprofile = (CircleImageView) hview.findViewById(R.id.imgprofile);
        slidename = (TextView) hview.findViewById(R.id.slidename);
        slideemail = (TextView) hview.findViewById(R.id.slideemail);
        slidename.setText(Config.namaprofile);
        slideemail.setText(Config.emailprofile);
        Glide.with(MainActivity.this).
                load(Config.gambarprofile).dontAnimate().
                placeholder(R.drawable.placeholder).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imgprofile);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getFragmentManager().beginTransaction().replace(R.id.container, new Home_fragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
            adb.setTitle("Konfirmasi");
            adb.setMessage("Yakin Ingin Keluar Dari App?");
            adb.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            adb.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            adb.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.keluar) {
            AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
            adb.setTitle("Konfirmasi");
            adb.setMessage("Yakin Ingin Keluar Dari App?");
            adb.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            adb.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            adb.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getFragmentManager().beginTransaction().replace(R.id.container, new Home_fragment()).commit();
            this.getSupportActionBar().setTitle("Dr Ari Fahrial Syam");
        } else if (id == R.id.nav_artikel) {
            getFragmentManager().beginTransaction().replace(R.id.container, new Artikel_fragment()).commit();
            this.getSupportActionBar().setTitle("Artikel");
        } else if (id == R.id.nav_profile) {
            getFragmentManager().beginTransaction().replace(R.id.container, new Profile_fragment()).commit();
            this.getSupportActionBar().setTitle("Profile");
        } else if (id == R.id.nav_gallery) {
            getFragmentManager().beginTransaction().replace(R.id.container, new Galery_fragment()).commit();
            this.getSupportActionBar().setTitle("Galeri");
        } else if (id == R.id.nav_video) {
            getFragmentManager().beginTransaction().replace(R.id.container, new Video_fragment()).commit();
            this.getSupportActionBar().setTitle("Video");
        } else if (id == R.id.nav_publikasi) {
            getFragmentManager().beginTransaction().replace(R.id.container, new Publish_fragment()).commit();
            this.getSupportActionBar().setTitle("Publikasi");
        } else if (id == R.id.nav_kirim) {
            //getFragmentManager().beginTransaction().replace(R.id.container, new Kirimpesan_fragment()).commit();
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + Config.emailprofile));
            startActivity(intent);
        } else if (id == R.id.nav_insta) {

            Uri uri = Uri.parse("http://instagram.com/_u/dokterari/");
            Intent insta = new Intent(Intent.ACTION_VIEW, uri);

            insta.setPackage("com.instagram.android");

            try {
                startActivity(insta);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/dokterari")));
            }
            /*
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return true;
            }
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+Config.nohpprofile));
            startActivity(callIntent);
            */

        }else if(id==R.id.nav_twitter){
            Uri uri = Uri.parse("twitter://user?screen_name=dokterari");
            Intent twitter = new Intent(Intent.ACTION_VIEW, uri);

            twitter.setPackage("com.twitter.android");

            try {
                startActivity(twitter);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://twitter.com/dokterari")));
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
