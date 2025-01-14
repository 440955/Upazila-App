package com.arafat.kauniaupzila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Doctors_View extends AppCompatActivity {
    public static String DOCTOR="";
    RecyclerView recyclerView;
    TextView tv_noInternet;
    ProgressBar progressBar;
    MaterialToolbar toolbar;
    ArrayList<HashMap<String, String>>arrayList;
    HashMap<String, String>hashMap;
    ProjectHelper projectHelper= new ProjectHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_view);
        getWindow().setStatusBarColor(ContextCompat.getColor(Doctors_View.this, R.color.statusbar));
        getWindow().setNavigationBarColor(ContextCompat.getColor(Doctors_View.this, R.color.statusbar));


        recyclerView= findViewById(R.id.recyclerView_doctor);
        tv_noInternet= findViewById(R.id.noInternet2);
        progressBar= findViewById(R.id.progressBar2);
        toolbar= findViewById(R.id.toolbar4);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (DOCTOR.contains("mandoctor")){
            getSupportActionBar().setTitle("বিশেষজ্ঞ ডাক্তার");
            getData("https://samsunnaher.000webhostapp.com/simcode/mandoctor.php");
        } else if (DOCTOR.contains("homioDoctor")) {
            getSupportActionBar().setTitle("হোমিও ডাক্তার");
            getData("https://samsunnaher.000webhostapp.com/simcode/homiodoctor.php");
        }
        else if (DOCTOR.contains("upzilaDoctor")) {
            getSupportActionBar().setTitle("উপজেলা ডাক্তার");
            getData("https://samsunnaher.000webhostapp.com/simcode/upziladoctor.php");
        }
        else if (DOCTOR.contains("posuDoctor")) {
            getSupportActionBar().setTitle("পশু ডাক্তার");
            getData("https://samsunnaher.000webhostapp.com/simcode/animaldoctor.php");
        } else if (DOCTOR.contains("restorant")) {
            getSupportActionBar().setTitle("রেস্টুরেন্ট");
            getData("https://samsunnaher.000webhostapp.com/simcode/hotelandrestorant.php");
        }
        else {
            onBackPressed();
        }

    }
    private class My_Adapter extends RecyclerView.Adapter{
        int ITEM=0;
        int AD=1;
        private class ItemViewHolder extends RecyclerView.ViewHolder{
            TextView doctor_name, doctor_degree, expert_doctor;
            CircleImageView item_image;
            Button doctor_message, doctor_email, doctor_phone;
            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                doctor_name= itemView.findViewById(R.id.doctor_name);
                doctor_degree= itemView.findViewById(R.id.doctor_degree);
                expert_doctor= itemView.findViewById(R.id.expert_doctor);
                doctor_message= itemView.findViewById(R.id.doctor_message);
                doctor_email= itemView.findViewById(R.id.doctor_email);
                doctor_phone= itemView.findViewById(R.id.doctor_phone);
                item_image= itemView.findViewById(R.id.item_image);
            }
        }
        private class AdViewHolder extends RecyclerView.ViewHolder{
            TemplateView templateView;
            public AdViewHolder(@NonNull View itemView) {
                super(itemView);
                templateView= itemView.findViewById(R.id.nativeAd_template);
                templateView.setVisibility(View.GONE);
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater= getLayoutInflater();
            if (getItemViewType(viewType)==ITEM){
                View myView= layoutInflater.inflate(R.layout.doctor_item, parent, false);
                return new ItemViewHolder(myView);
            }
            else {
                View myView= layoutInflater.inflate(R.layout.native_ad, parent, false);
                return new AdViewHolder(myView);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (getItemViewType(position)==ITEM){
                ItemViewHolder itemViewHolder= (ItemViewHolder) holder;

                hashMap= arrayList.get(position);
                itemViewHolder.doctor_name.setText(hashMap.get("name"));
                itemViewHolder.doctor_degree.setText(hashMap.get("degree"));
                itemViewHolder.expert_doctor.setText(hashMap.get("expert"));

                Picasso.get().load(hashMap.get("imageLink"))
                        .fit()
                        .into(itemViewHolder.item_image);

                itemViewHolder.doctor_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hashMap=arrayList.get(position);
                        Uri uri = Uri.parse("smsto:"+hashMap.get("phone")+"");
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        intent.putExtra("sms_body", "");
                        startActivity(intent);
                    }
                });

                itemViewHolder.doctor_email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hashMap=arrayList.get(position);
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/html");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""+hashMap.get("email")});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "");
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        startActivity(Intent.createChooser(intent, "Send Email"));
                    }
                });
                itemViewHolder.doctor_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hashMap=arrayList.get(position);
                        Uri number = Uri.parse("tel:"+hashMap.get("phone")+"");
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                        startActivity(callIntent);
                    }
                });

            }
            else if (getItemViewType(position)==AD) {
                AdViewHolder adViewHolder= (AdViewHolder) holder;
                projectHelper.MobileAD(Doctors_View.this);
                AdLoader adLoader = new AdLoader.Builder(Doctors_View.this, "ca-app-pub-6621199192974495/5026848704")
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                adViewHolder.templateView.setVisibility(View.VISIBLE);
                                adViewHolder.templateView.setNativeAd(nativeAd);
                            }
                        })
                        .build();
                adLoader.loadAd(new AdRequest.Builder().build());
            }

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        @Override
        public int getItemViewType(int position) {
            hashMap= arrayList.get(position);
            if (hashMap.get("TYPE").contains("ITEM")){
                return ITEM;
            }
            else{
                return AD;
            }
        }
    }


    private void getData (String link){

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, link, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                tv_noInternet.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                try {
                    arrayList= new ArrayList<>();
                    for (int x=0; x<response.length();x++){
                        JSONObject jsonObject= response.getJSONObject(x);

                        String name= jsonObject.getString("name");
                        String degree= jsonObject.getString("degree");
                        String expert= jsonObject.getString("expert");
                        String email= jsonObject.getString("email");
                        String phone= jsonObject.getString("phone");
                        String imageLink= jsonObject.getString("imageLink");

                        hashMap= new HashMap<>();
                        hashMap.put("TYPE", "ITEM");
                        hashMap.put("name", name);
                        hashMap.put("degree", degree);
                        hashMap.put("expert", expert);
                        hashMap.put("email", email);
                        hashMap.put("phone", phone);
                        hashMap.put("imageLink", imageLink);
                        arrayList.add(hashMap);

                        if (x%5==0){
                            hashMap= new HashMap<>();
                            hashMap.put("TYPE", "AD");
                            arrayList.add(hashMap);
                        }

                    }
                    recyclerView.setAdapter(new My_Adapter());
                    recyclerView.setLayoutManager(new LinearLayoutManager(Doctors_View.this));

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getData(link);
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(Doctors_View.this);
        requestQueue.add(jsonArrayRequest);

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}