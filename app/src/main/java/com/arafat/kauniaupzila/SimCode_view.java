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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class SimCode_view extends AppCompatActivity {
    public static String OPERATOR= "";
    MaterialToolbar toolbar;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView textView;
    ArrayList<HashMap<String, String>>arrayList;
    HashMap<String, String>hashMap;
    ProjectHelper projectHelper= new ProjectHelper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim_code_view);

        toolbar= findViewById(R.id.toolbar2);
        recyclerView= findViewById(R.id.recyclerView);
        progressBar= findViewById(R.id.progressBar1);
        textView= findViewById(R.id.noInternet1);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setNavigationBarColor(ContextCompat.getColor(SimCode_view.this, R.color.statusbar));
        getWindow().setStatusBarColor(ContextCompat.getColor(SimCode_view.this, R.color.statusbar));

        if (OPERATOR.contains("banglalink")){
            getSupportActionBar().setTitle("বাংলালিংক সিমের কোড সমূহ");
            getData("https://samsunnaher.000webhostapp.com/simcode/banglaLink.php");
        } else if (OPERATOR.contains("grameen")) {
            getSupportActionBar().setTitle("গ্রামীণ সিমের কোড সমূহ");
            getData("https://samsunnaher.000webhostapp.com/simcode/grameen.php");
        }
        else if (OPERATOR.contains("robi")) {
            getSupportActionBar().setTitle("রবি সিমের কোড সমূহ");
            getData("https://samsunnaher.000webhostapp.com/simcode/robi.php");
        }
        else if (OPERATOR.contains("airtel")) {
            getSupportActionBar().setTitle("এয়ারটেল সিমের কোড সমূহ");
            getData("https://samsunnaher.000webhostapp.com/simcode/airtel.php");
        }
        else if (OPERATOR.contains("teletalk")) {
            getSupportActionBar().setTitle("টেলিটক সিমের কোড সমূহ");
            getData("https://samsunnaher.000webhostapp.com/simcode/teletok.php");
        }
        else if (OPERATOR.contains("helpline")) {
            getSupportActionBar().setTitle("হেল্প সেন্টার");
            getData("https://samsunnaher.000webhostapp.com/simcode/helpline.php");
        }
        else if (OPERATOR.contains("narch")) {
            getSupportActionBar().setTitle("নার্স");
            getData("https://samsunnaher.000webhostapp.com/simcode/narch.php");
        } else if (OPERATOR.contains("stampWriter")) {
            getSupportActionBar().setTitle("দলিল লেখক");
            getData("https://samsunnaher.000webhostapp.com/simcode/stampwriter.php");
        }


    }

    private class My_Adapter extends RecyclerView.Adapter{
        int ITEM=0;
        int AD=1;

        private class ItemViewHolder extends RecyclerView.ViewHolder{
            CircleImageView imageView;
            TextView work, code;
            ImageView image_dial;
            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView= itemView.findViewById(R.id.simCard_image);
                work= itemView.findViewById(R.id.work);
                code= itemView.findViewById(R.id.code);
                image_dial= itemView.findViewById(R.id.image_dial);
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
                View myView= layoutInflater.inflate(R.layout.simcode_item, parent, false);
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
                String work= hashMap.get("work");
                String code= hashMap.get("code");

                if (OPERATOR.contains("banglalink"))itemViewHolder.imageView.setImageResource(R.drawable.banglalink_icon);
                if (OPERATOR.contains("grameen"))itemViewHolder.imageView.setImageResource(R.drawable.grameen_icon);
                if (OPERATOR.contains("robi"))itemViewHolder.imageView.setImageResource(R.drawable.robi_icon);
                if (OPERATOR.contains("airtel"))itemViewHolder.imageView.setImageResource(R.drawable.aritel_icon);
                if (OPERATOR.contains("teletalk"))itemViewHolder.imageView.setImageResource(R.drawable.teletok_icon);
                if (OPERATOR.contains("helpline"))itemViewHolder.imageView.setImageResource(R.drawable.help_icon);
                if (OPERATOR.contains("stampWriter"))itemViewHolder.imageView.setImageResource(R.drawable.writer_icon);
                if (OPERATOR.contains("narch"))itemViewHolder.imageView.setImageResource(R.drawable.nurse_icon);
                itemViewHolder.work.setText(work);
                itemViewHolder.code.setText(code);

                itemViewHolder.image_dial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hashMap= arrayList.get(position);
                        Uri uri= Uri.parse("tel:"+hashMap.get("code")+"");
                        Intent intent= new Intent(Intent.ACTION_DIAL,uri);
                        startActivity(intent);
                    }
                });
            }
            else{
                AdViewHolder adViewHolder= (AdViewHolder) holder;
                projectHelper.MobileAD(SimCode_view.this);
                AdLoader adLoader = new AdLoader.Builder(SimCode_view.this, "ca-app-pub-6621199192974495/5026848704")
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
            hashMap = arrayList.get(position);
            if (hashMap.get("TYPE").contains("ITEM")){
                return ITEM;
            }
            else {
                return AD;
            }
        }
    }

    private void getData(String link){

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, link, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                arrayList= new ArrayList<>();
                try {
                    for (int x= 0; x<response.length(); x++){
                        JSONObject jsonObject= response.getJSONObject(x);

                        String work= jsonObject.getString("work");
                        String code= jsonObject.getString("code");
                        hashMap= new HashMap<>();
                        hashMap.put("TYPE", "ITEM");
                        hashMap.put("work", work);
                        hashMap.put("code", code);
                        arrayList.add(hashMap);

                        if (x%5==0){
                            hashMap= new HashMap<>();
                            hashMap.put("TYPE", "AD");
                            arrayList.add(hashMap);
                        }
                    }
                    recyclerView.setAdapter(new My_Adapter());
                    recyclerView.setLayoutManager(new LinearLayoutManager(SimCode_view.this));

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
        RequestQueue requestQueue= Volley.newRequestQueue(SimCode_view.this);
        requestQueue.add(jsonArrayRequest);

    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {

        if (OPERATOR.contains("stampWriter")){
            getMenuInflater().inflate(R.menu.search_view, menu);
            MenuItem menuItem = menu.findItem(R.id.searchView);
            SearchView searchView =(SearchView) menuItem.getActionView();
            searchView.setQueryHint("নাম দিয়ে সার্চ করুন");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    getData("https://samsunnaher.000webhostapp.com/simcode/searchStampWriter.php?key="+newText+"");
                    return true;
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