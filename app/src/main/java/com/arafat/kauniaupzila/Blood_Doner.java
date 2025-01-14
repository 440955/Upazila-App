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
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

public class Blood_Doner extends AppCompatActivity {
    public static String ITEM="";
    MaterialToolbar toolbar;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView tv_noInternet;
    ArrayList<HashMap<String, String>>arrayList;
    HashMap<String, String>hashMap;
    ProjectHelper projectHelper= new ProjectHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_doner);
        getWindow().setStatusBarColor(ContextCompat.getColor(Blood_Doner.this, R.color.statusbar));
        getWindow().setNavigationBarColor(ContextCompat.getColor(Blood_Doner.this, R.color.statusbar));

        recyclerView= findViewById(R.id.recyclerView_bloodDoner);
        progressBar= findViewById(R.id.progressBar3);
        tv_noInternet= findViewById(R.id.noInternet3);
        toolbar= findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (ITEM.contains("blooddoner")){
            getData("https://samsunnaher.000webhostapp.com/simcode/blooddoner.php");
            getSupportActionBar().setTitle("ব্লাড ডোনার");
        } else if (ITEM.contains("dekorator")) {
            getData("https://samsunnaher.000webhostapp.com/simcode/dekorator.php");
            getSupportActionBar().setTitle("ডেকোরেটর");
        }

    }

    private void getData(String url){

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                tv_noInternet.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                arrayList= new ArrayList<>();
                try {
                    for (int x=0; x<response.length(); x++){
                        JSONObject jsonObject= response.getJSONObject(x);
                        hashMap=new HashMap<>();
                        hashMap.put("TYPE", "ITEM");
                        hashMap.put("name", jsonObject.getString("name"));
                        hashMap.put("bloodgroup", jsonObject.getString("bloodgroup"));
                        hashMap.put("address", jsonObject.getString("address"));
                        hashMap.put("phone", jsonObject.getString("phone"));
                        arrayList.add(hashMap);

                        if (x%5==0){
                            hashMap= new HashMap<>();
                            hashMap.put("TYPE", "AD");
                            arrayList.add(hashMap);
                        }

                    }
                    recyclerView.setAdapter(new My_Adapter());
                    recyclerView.setLayoutManager(new LinearLayoutManager(Blood_Doner.this));

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getData(url);
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(Blood_Doner.this);
        requestQueue.add(jsonArrayRequest);


    }


    private class My_Adapter extends RecyclerView.Adapter{
        int ITEM=0;
        int AD=1;
        private class ItemViewHolder extends RecyclerView.ViewHolder{
            CircleImageView donerImage;
            TextView donerName, bloodGroup, donerAddress;
            ImageView callDoner;
            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                donerImage= itemView.findViewById(R.id.image_doner);
                donerName= itemView.findViewById(R.id.donerName);
                bloodGroup= itemView.findViewById(R.id.bloodGroup);
                donerAddress= itemView.findViewById(R.id.donerAddress);
                callDoner= itemView.findViewById(R.id.callDoner);
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
                View myView= layoutInflater.inflate(R.layout.item_blood_doner, parent, false);
                return new ItemViewHolder(myView);
            }
            else {
                View myView= layoutInflater.inflate(R.layout.native_ad, parent, false);
                return new AdViewHolder(myView);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            if (getItemViewType(position)==ITEM){
                ItemViewHolder itemViewHolder= (ItemViewHolder) holder;
                hashMap= arrayList.get(position);
                itemViewHolder.donerName.setText(hashMap.get("name"));
                itemViewHolder.bloodGroup.setText(hashMap.get("bloodgroup"));
                itemViewHolder.donerAddress.setText(hashMap.get("address"));

                itemViewHolder.callDoner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hashMap= arrayList.get(position);
                        Uri uri= Uri.parse("tel:"+hashMap.get("phone")+"");
                        Intent intent= new Intent(Intent.ACTION_DIAL, uri);
                        startActivity(intent);
                    }
                });
            }
            else {
                AdViewHolder adViewHolder= (AdViewHolder) holder;
                projectHelper.MobileAD(Blood_Doner.this);
                AdLoader adLoader = new AdLoader.Builder(Blood_Doner.this, "ca-app-pub-6621199192974495/5026848704")
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        if (ITEM.contains("blooddoner")){
            getMenuInflater().inflate(R.menu.search_view, menu);
            MenuItem menuItem = menu.findItem(R.id.searchView);
            SearchView searchView =(SearchView) menuItem.getActionView();
            searchView.setQueryHint("example.A");


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    getData("https://samsunnaher.000webhostapp.com/simcode/searchBloodgroup.php?key="+newText+"");
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }
}