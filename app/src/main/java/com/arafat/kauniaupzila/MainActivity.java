package com.arafat.kauniaupzila;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.BuildConfig;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    public static String notify="";
    MaterialToolbar materialToolbar;
    NavigationView navigationView;
    MeowBottomNavigation bottomNavigation;
    ProjectHelper projectHelper= new ProjectHelper();

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        projectHelper.onSignal(MainActivity.this);

        drawerLayout= findViewById(R.id.drawerLayout);
        materialToolbar= findViewById(R.id.toolbar1);
        navigationView= findViewById(R.id.navigationView);
        bottomNavigation= findViewById(R.id.meowBottom);


        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle((Activity) MainActivity.this, drawerLayout, materialToolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);

        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.add(R.id.frameLayout, new HomeFragment());
        ft.commit();

        getWindow().setStatusBarColor(ContextCompat.getColor((Context) MainActivity.this, R.color.statusbar));
        getWindow().setNavigationBarColor(ContextCompat.getColor((Context) MainActivity.this, R.color.statusbar));

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.developer_icon));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.home_icon));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.news_icon));
        bottomNavigation.show(2, true);


        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                if (model.getId()==2){
                    FragmentManager fm= getSupportFragmentManager();
                    FragmentTransaction ft= fm.beginTransaction();
                    ft.add(R.id.frameLayout, new HomeFragment());
                    ft.commit();
                }
                if (model.getId()==1){
                    adDialog();
                }
                if (model.getId()==3){
                    FragmentManager fm= getSupportFragmentManager();
                    FragmentTransaction ft= fm.beginTransaction();
                    ft.add(R.id.frameLayout, new NotificationFragment());
                    ft.commit();
                    bottomNavigation.clearCount(3);
                }
                return null;
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId()== R.id.nav_home){
                    FragmentManager fm= getSupportFragmentManager();
                    FragmentTransaction ft= fm.beginTransaction();
                    ft.add(R.id.frameLayout, new HomeFragment());
                    ft.commit();
                    bottomNavigation.show(2, true);
                }
                else if (item.getItemId()== R.id.nav_notification) {
                    FragmentManager fm= getSupportFragmentManager();
                    FragmentTransaction ft= fm.beginTransaction();
                    ft.add(R.id.frameLayout, new NotificationFragment());
                    ft.commit();
                    bottomNavigation.clearCount(3);
                    bottomNavigation.show(3, true);
                }
                else if (item.getItemId()== R.id.nav_developer){
                    adDialog();
                }
                else if (item.getItemId()==R.id.nav_email){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/html");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mdarafathossainsozib@gmail.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(Intent.createChooser(intent, "Send Email"));
                }

                else if (item.getItemId()==R.id.nav_biggapon){
                    Uri number = Uri.parse("tel:01885881138");
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(callIntent);
                }

                else if (item.getItemId()==R.id.nav_shahidbagUnion){
                    Union_Porishod.UNION="শহীদবাগ ইউনিয়ন";
                    startActivity(new Intent((Context) MainActivity.this, Union_Porishod.class));
                }

                else if (item.getItemId()==R.id.nav_saraiUnion){
                    Union_Porishod.UNION="সারাই ইউনিয়ন";
                    startActivity(new Intent((Context) MainActivity.this, Union_Porishod.class));
                }
                else if (item.getItemId()==R.id.nav_haragashUnion){
                    Union_Porishod.UNION="হারাগাছ ইউনিয়ন";
                    startActivity(new Intent((Context) MainActivity.this, Union_Porishod.class));
                }
                else if (item.getItemId()==R.id.nav_kurshaUnion){
                    Union_Porishod.UNION="কুর্শা ইউনিয়ন";
                    startActivity(new Intent((Context) MainActivity.this, Union_Porishod.class));
                }
                else if (item.getItemId()==R.id.nav_balaparaUnion){
                    Union_Porishod.UNION="বালাপাড়া ইউনিয়ন";
                    startActivity(new Intent((Context) MainActivity.this, Union_Porishod.class));
                }
                else if (item.getItemId()==R.id.nav_modhupurUnion){
                    Union_Porishod.UNION="টেপামধুপুর ইউনিয়ন";
                    startActivity(new Intent((Context) MainActivity.this, Union_Porishod.class));
                }
                else if (item.getItemId()==R.id.nav_bloodDonar) {
                    if (HomeFragment.isNetworkAvailable(MainActivity.this)){
                        Blood_Doner.ITEM="blooddoner";
                        startActivity(new Intent((Context) MainActivity.this, Blood_Doner.class));
                    }
                    else {
                        Toast.makeText((Context) MainActivity.this, "আপনার ইন্টারনেট সংযোগটি চালু করুন", Toast.LENGTH_SHORT).show();
                    }
                } else if (item.getItemId()==R.id.nav_privacyPolicy) {
                    if (HomeFragment.isNetworkAvailable(MainActivity.this)){
                        Village_People.VILLAGE="privacyPolicy";
                        startActivity(new Intent((Context) MainActivity.this, Village_People.class));
                    }
                } else if (item.getItemId()==R.id.nav_shareApp) {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/html");
                    share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.arafat.kauniaupzila");
                    startActivity(share);
                } else if (item.getItemId()==R.id.nav_rating) {
                    Intent rating= new Intent(Intent.ACTION_VIEW);
                    rating.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.arafat.kauniaupzila"));
                    startActivity(rating);
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });

        String NoticeNumberLink="https://mdarafathossain.000webhostapp.com/apps/Upzila%20app%20news.json";
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, NoticeNumberLink, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String number= response.getString("notice number");

                    if (number.contains("0")){
                    }
                    else {
                        bottomNavigation.setCount(3, ""+number);
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue((Context) MainActivity.this);
        requestQueue.add(jsonObjectRequest);

        askNotificationPermission();

    }

    //code for pushNotification
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
    //code for pushNotification



    public void adDialog(){
        AlertDialog alertDialog;
        AlertDialog.Builder builder= new AlertDialog.Builder((Context) MainActivity.this);
        final  View layoutView= getLayoutInflater().inflate(R.layout.developer_dialog, null);
        builder.setView(layoutView);
        alertDialog= builder.create();
        alertDialog.show();

        Button message, phone, email;
        message= layoutView.findViewById(R.id.dvp_message);
        phone= layoutView.findViewById(R.id.dvp_phone);
        email= layoutView.findViewById(R.id.dvp_email);

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Uri uri = Uri.parse("smsto:01885881138");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", "Hello Developer...");
                startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Uri number = Uri.parse("tel:01885881138");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mdarafathossainsozib@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });


    }

}