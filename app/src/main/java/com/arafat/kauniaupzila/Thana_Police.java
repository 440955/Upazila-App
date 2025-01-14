package com.arafat.kauniaupzila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
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

public class Thana_Police extends AppCompatActivity {
    public static String OPERATOR="";

    RecyclerView recyclerView_police;
    MaterialToolbar toolbar;
    ProgressBar progressBar;
    TextView textView;
    ArrayList<HashMap<String, String>>arrayList;
    HashMap<String, String>hashMap;
    ProjectHelper projectHelper= new ProjectHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thana_police);
        getWindow().setStatusBarColor(ContextCompat.getColor(Thana_Police.this, R.color.statusbar));
        getWindow().setNavigationBarColor(ContextCompat.getColor(Thana_Police.this, R.color.statusbar));

        recyclerView_police= findViewById(R.id.recyclerView_police);
        toolbar= findViewById(R.id.toolbar7);
        progressBar= findViewById(R.id.progressBar4);
        textView= findViewById(R.id.noInternet4);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (OPERATOR.contains("thanapolice")){
            getSupportActionBar().setTitle("পুলিশ");
            getData("https://samsunnaher.000webhostapp.com/simcode/thanapolice.php");
        } else if (OPERATOR.contains("pollibiddut")) {
            getSupportActionBar().setTitle("পল্লী বিদ্যুৎ");
            getData("https://samsunnaher.000webhostapp.com/simcode/pollibiddut.php");
        }

    }

    private void getData(String link){

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, link, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                recyclerView_police.setVisibility(View.VISIBLE);
                arrayList= new ArrayList<>();
                try {
                    for (int x=0; x<response.length(); x++){
                        JSONObject jsonObject= response.getJSONObject(x);
                        hashMap= new HashMap<>();
                        hashMap.put("TYPE", "ITEM");
                        hashMap.put("name", jsonObject.getString("name"));
                        hashMap.put("number", jsonObject.getString("number"));
                        hashMap.put("thana", jsonObject.getString("thana"));
                        hashMap.put("image", jsonObject.getString("image"));
                        arrayList.add(hashMap);

                        if (x%5==0){
                            hashMap= new HashMap<>();
                            hashMap.put("TYPE", "AD");
                            arrayList.add(hashMap);
                        }

                    }

                    recyclerView_police.setAdapter(new MY_Adapter());
                    recyclerView_police.setLayoutManager(new LinearLayoutManager(Thana_Police.this));

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
        RequestQueue requestQueue= Volley.newRequestQueue(Thana_Police.this);
        requestQueue.add(jsonArrayRequest);


    }



    private class MY_Adapter extends RecyclerView.Adapter{
        int ITEM=0;
        int AD=1;

        private class ItemViewHolder extends RecyclerView.ViewHolder{
            CircleImageView image;
            TextView name, phone, thana;
            Button callButton;
            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                image=itemView.findViewById(R.id.police_image);
                name=itemView.findViewById(R.id.police_name);
                phone=itemView.findViewById(R.id.police_number);
                thana=itemView.findViewById(R.id.thana);
                callButton=itemView.findViewById(R.id.call_police);
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
                View myView= layoutInflater.inflate(R.layout.item_police, parent, false);
                return  new ItemViewHolder(myView);
            }
            else {
                View myView= layoutInflater.inflate(R.layout.native_ad, parent, false);
                return  new AdViewHolder(myView);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (getItemViewType(position)==ITEM){
                ItemViewHolder itemViewHolder= (MY_Adapter.ItemViewHolder) holder;
                hashMap= arrayList.get(position);
                Picasso.get().load(hashMap.get("image"))
                        .fit()
                        .into(itemViewHolder.image);
                itemViewHolder.name.setText(hashMap.get("name"));
                itemViewHolder.phone.setText(hashMap.get("number"));
                itemViewHolder.thana.setText(hashMap.get("thana"));

                itemViewHolder.callButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hashMap= arrayList.get(position);
                        Uri uri= Uri.parse("tel:"+hashMap.get("number")+"");
                        startActivity(new Intent(Intent.ACTION_DIAL, uri));
                    }
                });

            }
            else {
                AdViewHolder adViewHolder= (MY_Adapter.AdViewHolder) holder;
                projectHelper.MobileAD(Thana_Police.this);
                AdLoader adLoader = new AdLoader.Builder(Thana_Police.this, "ca-app-pub-6621199192974495/5026848704")
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
            else {
                return AD;
            }
        }
    }





    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        if (OPERATOR.contains("thanapolice")){
            getMenuInflater().inflate(R.menu.search_view, menu);
            MenuItem menuItem = menu.findItem(R.id.searchView);
            SearchView searchView =(SearchView) menuItem.getActionView();
            searchView.setQueryHint("থানার নাম দিয়ে সার্চ করুন");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    getData("https://samsunnaher.000webhostapp.com/simcode/searchThanapolice.php?key="+newText+"");
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}