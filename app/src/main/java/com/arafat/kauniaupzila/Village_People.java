package com.arafat.kauniaupzila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.HashMap;

public class Village_People extends AppCompatActivity {

    public static String VILLAGE="";
    MaterialToolbar toolbar;

    AdView adView;
    ProgressBar progressBar;
    LinearLayout shahidbag_villageList, modhupur_villageList, sarai_villageList, haragash_villageList, kursha_villageList,
            balapara_villageList;
    ListView listView;
    WebView webView;
    ArrayList<HashMap<String, String>>arrayList;
    HashMap<String, String>hashMap;
    ProjectHelper projectHelper= new ProjectHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_people);
        getWindow().setStatusBarColor(ContextCompat.getColor(Village_People.this, R.color.statusbar));
        getWindow().setNavigationBarColor(ContextCompat.getColor(Village_People.this, R.color.statusbar));
        projectHelper.MobileAD(this);


        toolbar= findViewById(R.id.toolbar3);
        adView= findViewById(R.id.bannerAd2);
        shahidbag_villageList= findViewById(R.id.shahidbag_villageList);
        modhupur_villageList= findViewById(R.id.modhupur_villageList);
        sarai_villageList= findViewById(R.id.sarai_villageList);
        haragash_villageList= findViewById(R.id.haragash_villageList);
        kursha_villageList= findViewById(R.id.kursha_villageList);
        balapara_villageList= findViewById(R.id.balapara_villageList);
        listView= findViewById(R.id.listView);
        webView=findViewById(R.id.webView);
        progressBar=findViewById(R.id.progressBar5);

        AdRequest adRequest= new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (VILLAGE.contains("শহীদবাগ ইউনিয়ন")){
            shahidbag_villageList.setVisibility(View.VISIBLE);
        } else if (VILLAGE.contains("সারাই ইউনিয়ন")) {
            sarai_villageList.setVisibility(View.VISIBLE);
        }else if (VILLAGE.contains("হারাগাছ ইউনিয়ন")) {
            haragash_villageList.setVisibility(View.VISIBLE);
        }else if (VILLAGE.contains("কুর্শা ইউনিয়ন")) {
            kursha_villageList.setVisibility(View.VISIBLE);
        }else if (VILLAGE.contains("বালাপাড়া ইউনিয়ন")) {
            balapara_villageList.setVisibility(View.VISIBLE);
        } else if (VILLAGE.contains("টেপামধুপুর ইউনিয়ন")) {
            modhupur_villageList.setVisibility(View.VISIBLE);
        }

        else if (VILLAGE.contains("gramAdalot")) {
            getSupportActionBar().setTitle("গ্রাম আদালত");
            progressBar.setVisibility(View.VISIBLE);
            webView.loadUrl("https://sites.google.com/view/gramadalotbidhimala/home");
            webView.getSettings().setJavaScriptEnabled(true);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }
            }, 4000);

        } else if (VILLAGE.contains("privacyPolicy")) {
            getSupportActionBar().setTitle("প্রাইভেসি পলিসি");
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl("https://sites.google.com/view/kauniaupzilaonlineservice/home");
            webView.getSettings().setJavaScriptEnabled(true);

        } else if (VILLAGE.contains("ALLKAUNIA")) {
            getSupportActionBar().setTitle("এক নজরে কাউনিয়া");
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl("https://sites.google.com/view/eknojorekaunia/home");
            webView.getSettings().setJavaScriptEnabled(true);

        } else{
            getSupportActionBar().setTitle("কাজী");
            listView.setVisibility(View.VISIBLE);
            itemKazi();
            listView.setAdapter(new My_Adapter());
        }


    }

    private class My_Adapter extends BaseAdapter{
        TextView kaziName, kaziAddress, kaziNumber;
        ImageView callKazi;
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater= getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.item_kazi, parent, false);

            kaziName= myView.findViewById(R.id.kaziName);
            kaziAddress= myView.findViewById(R.id.kaziAddress);
            kaziNumber= myView.findViewById(R.id.kaziNumber);
            callKazi= myView.findViewById(R.id.callKazi);


            hashMap= arrayList.get(position);
            kaziName.setText(hashMap.get("kaziName"));
            kaziAddress.setText(hashMap.get("kaziAddress"));
            kaziNumber.setText(hashMap.get("kaziPhone"));

            callKazi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hashMap= arrayList.get(position);
                    Uri uri= Uri.parse("tel:"+hashMap.get("kaziPhone")+"");
                    Intent intent= new Intent(Intent.ACTION_DIAL, uri);
                    startActivity(intent);
                }
            });

            return myView;
        }
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void itemKazi(){
        arrayList= new ArrayList<>();

        hashMap= new HashMap<>();
        hashMap.put("kaziName", "মোঃ আজহারুল ইসলাম");
        hashMap.put("kaziAddress", "হারাগাছ ইউপি");
        hashMap.put("kaziPhone", "+8801716011676");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("kaziName", "মোঃ সাইফুল ইসলাম");
        hashMap.put("kaziAddress", "সারাই ইউপি");
        hashMap.put("kaziPhone", "+8801723544471");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("kaziName", "মোঃ কাজিমদ্দীন");
        hashMap.put("kaziAddress", "কুর্শা ইউপি");
        hashMap.put("kaziPhone", "+8801716393738");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("kaziName", "মোঃ মনিরুজ্জামান");
        hashMap.put("kaziAddress", "বালাপাড়া ইউপি");
        hashMap.put("kaziPhone", "+8801713634444");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("kaziName", "মোঃ আঃ আজিজ");
        hashMap.put("kaziAddress", "টেপামধুপুর ইউপি");
        hashMap.put("kaziPhone", "+8801719548069");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("kaziName", "মোঃ তাজুল ইসলাম");
        hashMap.put("kaziAddress", "শহীদবাগ ইউপি");
        hashMap.put("kaziPhone", "+8801712437407");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("kaziName", "মোঃ আব্দুল খালেক");
        hashMap.put("kaziAddress", "হারাগাছ পৌরসভা");
        hashMap.put("kaziPhone", "+8801718582705");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("kaziName", "মোঃ মোনায়েম হোসেন");
        hashMap.put("kaziAddress", "হারাগাছ পৌরসভা");
        hashMap.put("kaziPhone", "+8801724671698");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("kaziName", "মোঃ সিরাজুল ইসলাম");
        hashMap.put("kaziAddress", "হারাগাছ পৌরসভা");
        hashMap.put("kaziPhone", "+8801719464032");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("kaziName", "পরিমল চন্দ্র(হিন্দু রেজিঃ)");
        hashMap.put("kaziAddress", "শহীদবাগ উইপি");
        hashMap.put("kaziPhone", "+8801761145513");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("kaziName", "কমল চন্দ্র টিপু(হিন্দু রেজিঃ)");
        hashMap.put("kaziAddress", "বালাপাড়া  উইপি");
        hashMap.put("kaziPhone", "+8801717292295");
        arrayList.add(hashMap);

    }

}