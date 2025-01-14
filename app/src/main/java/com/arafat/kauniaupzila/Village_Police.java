package com.arafat.kauniaupzila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Village_Police extends AppCompatActivity {
    public static String UNION="";
    ListView listView2;
    MaterialToolbar toolbar;

    ArrayList<HashMap<String, String>>arrayList;
    HashMap<String, String>hashMap;
    ProjectHelper projectHelper= new ProjectHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_police);
        getWindow().setStatusBarColor(ContextCompat.getColor(Village_Police.this, R.color.statusbar));
        getWindow().setNavigationBarColor(ContextCompat.getColor(Village_Police.this, R.color.statusbar));

        listView2= findViewById(R.id.listView2);
        toolbar =findViewById(R.id.toolbar6);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (UNION.contains("হারাগাছ ইউনিয়ন")){
            getSupportActionBar().setTitle("গ্রাম পুলিশ");
            haragashUp();
            listView2.setAdapter(new MyAdapter());
        } else if (UNION.contains("সারাই ইউনিয়ন")) {
            getSupportActionBar().setTitle("গ্রাম পুলিশ");
            saraiUP();
            listView2.setAdapter(new MyAdapter());
        } else if (UNION.contains("কুর্শা ইউনিয়ন")) {
            getSupportActionBar().setTitle("গ্রাম পুলিশ");
            kurshaUp();
            listView2.setAdapter(new MyAdapter());
        } else if (UNION.contains("শহীদবাগ ইউনিয়ন")) {
            getSupportActionBar().setTitle("গ্রাম পুলিশ");
            shohidbagUp();
            listView2.setAdapter(new MyAdapter());
        } else if (UNION.contains("বালাপাড়া ইউনিয়ন")) {
            getSupportActionBar().setTitle("গ্রাম পুলিশ");
            balaparaUp();
            listView2.setAdapter(new MyAdapter());
        } else if (UNION.contains("টেপামধুপুর ইউনিয়ন")) {
            getSupportActionBar().setTitle("গ্রাম পুলিশ");
            modhupurUp();
            listView2.setAdapter(new MyAdapter());
        } else if (UNION.contains("fireService")) {
            getSupportActionBar().setTitle("ফায়ার সার্ভিস");
            fireService();
            listView2.setAdapter(new MyAdapter());
        } else if (UNION.contains("Ambulance")) {
            getSupportActionBar().setTitle("অ্যাম্বুলেন্স");
            Ambulance();
            listView2.setAdapter(new MyAdapter());
        }

    }

    private class MyAdapter extends BaseAdapter{
        int ITEM=0;
        int AD=1;
        TextView name, phone;
        CircleImageView civView;
        ImageView call;

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

            if (getItemViewType(position)==ITEM){
                //==============================
                LayoutInflater layoutInflater= getLayoutInflater();
                View myView= layoutInflater.inflate(R.layout.simcode_item, parent, false);

                name= myView.findViewById(R.id.work);
                phone= myView.findViewById(R.id.code);
                civView= myView.findViewById(R.id.simCard_image);
                call= myView.findViewById(R.id.image_dial);

                hashMap= arrayList.get(position);
                name.setText(hashMap.get("name"));
                phone.setText(hashMap.get("number"));

                if (UNION.contains("হারাগাছ ইউনিয়ন")){
                    civView.setImageResource(R.drawable.policeman_icon);
                } else if (UNION.contains("বালাপাড়া ইউনিয়ন")) {
                    civView.setImageResource(R.drawable.policeman_icon);
                } else if (UNION.contains("fireService")) {
                    civView.setImageResource(R.drawable.fire_icon);
                } else if (UNION.contains("Ambulance")) {
                    civView.setImageResource(R.drawable.embulance_icon);
                } else {
                    Picasso.get()
                            .load(hashMap.get("imageLink"))
                            .fit()
                            .placeholder(getDrawable(R.drawable.policeman_icon))
                            .into(civView);
                }


                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hashMap= arrayList.get(position);
                        Uri uri= Uri.parse("tel:"+hashMap.get("number")+"");
                        Intent intent= new Intent(Intent.ACTION_DIAL, uri);
                        startActivity(intent);
                    }
                });

                return myView;
                //===============================
            }
            else{
                LayoutInflater layoutInflater= getLayoutInflater();
                View myView= layoutInflater.inflate(R.layout.native_ad, parent, false);
                TemplateView templateView= myView.findViewById(R.id.nativeAd_template);
                templateView.setVisibility(View.GONE);
                projectHelper.MobileAD(Village_Police.this);
                AdLoader adLoader = new AdLoader.Builder(Village_Police.this, "ca-app-pub-6621199192974495/5026848704")
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                templateView.setVisibility(View.VISIBLE);
                                templateView.setNativeAd(nativeAd);
                            }
                        })
                        .build();
                adLoader.loadAd(new AdRequest.Builder().build());
                return myView;
            }


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

    //==================================

    private void Ambulance(){
        arrayList= new ArrayList<>();

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "শহীদবাগ ইউপি অ্যাম্বুলেন্স");
        hashMap.put("number", "01908666687");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "বালাপাড়া ইউপি অ্যাম্বুলেন্স");
        hashMap.put("number", "01743404343");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "AD");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "কুর্শা ইউপি অ্যাম্বুলেন্স");
        hashMap.put("number", "01761724142");
        arrayList.add(hashMap);
    }

    private void fireService(){
        arrayList=new ArrayList<>();

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "কাউনিয়া ফায়ার স্টেশন");
        hashMap.put("number", "০১৯০১০২৩২৩৯");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "হারাগাছ ফায়ার স্টেশন");
        hashMap.put("number", "০১৯০১০২৩২৪১");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "রংপুর ফায়ার স্টেশন(সদর)");
        hashMap.put("number", "০১৯০১০২৩২৩১");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "বদরগঞ্জ ফায়ার স্টেশন");
        hashMap.put("number", "০১৯০১০২৩২৩৫");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "AD");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মিঠাপুকুর ফায়ার স্টেশন");
        hashMap.put("number", "০১৯০১০২৩২৪৩");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "পীরগাছা ফায়ার স্টেশন");
        hashMap.put("number", "০১৯০১০২৩২৪৫");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "পীরগঞ্জ ফায়ার স্টেশন");
        hashMap.put("number", "০১৯০১০২৩২৪৭");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "তারাগঞ্জ ফায়ার স্টেশন");
        hashMap.put("number", "০১৯০১০২৩২৪৯");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "AD");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "গঙ্গাচড়া ফায়ার স্টেশন");
        hashMap.put("number", "০১৯০১০২৩২৩৭");
        arrayList.add(hashMap);



    }




    private void haragashUp(){
        arrayList= new ArrayList<>();

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আব্দুল লতিব");
        hashMap.put("number", "01793871890");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ জেনারুল ইসলাম");
        hashMap.put("number", "01929699136");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আকরামুল");
        hashMap.put("number", "01706709678");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ সবুজ মিয়া");
        hashMap.put("number", "01791842091");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "AD");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আব্দুল হামিদ");
        hashMap.put("number", "01728149626");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ নুরন নবী মিঞা");
        hashMap.put("number", "01878849664");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আব্দুল ওহাব");
        hashMap.put("number", "01722119664");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ টিটু মিয়া");
        hashMap.put("number", "01784993461");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "AD");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আমজাদ হোসেন");
        hashMap.put("number", "01762918514");
        arrayList.add(hashMap);


        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ মঞ্জিল হোসেন");
        hashMap.put("number", "01725021901");
        arrayList.add(hashMap);

    }

    private void saraiUP(){
        arrayList= new ArrayList<>();

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আনোয়ার হোসেন");
        hashMap.put("number", "01833617063");
        hashMap.put("imageLink", "https://shorturl.at/FGMY1");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ সাইফুল ইসলাম");
        hashMap.put("number", "01747034828");
        hashMap.put("imageLink", "https://shorturl.at/fkvIP");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ মাছুম খান-২");
        hashMap.put("number", "01739426505");
        hashMap.put("imageLink", "https://shorturl.at/jknqJ");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ মাছুম খান-১");
        hashMap.put("number", "01736020309");
        hashMap.put("imageLink", "https://shorturl.at/hFH16");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "AD");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ জবেদ আলী");
        hashMap.put("number", "01735317124");
        hashMap.put("imageLink", "https://shorturl.at/gqQ02");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "শ্রী দিনো চন্দ্র");
        hashMap.put("number", "01724672135");
        hashMap.put("imageLink", "http://surl.li/tjfee");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ মিজানুর রহমান");
        hashMap.put("number", "01723257071");
        hashMap.put("imageLink", "http://surl.li/tjfeq");
        arrayList.add(hashMap);


        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ হাসান আলী");
        hashMap.put("number", "01755145358");
        hashMap.put("imageLink", "http://surl.li/tjfex");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "AD");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আজিজুল ইসলাম");
        hashMap.put("number", "01773101729");
        hashMap.put("imageLink", "http://surl.li/tjffi");
        arrayList.add(hashMap);


        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আতাউর রহমান");
        hashMap.put("number", "01740640835");
        hashMap.put("imageLink", "http://surl.li/tjffp");
        arrayList.add(hashMap);

    }
    private void kurshaUp(){
        arrayList= new ArrayList<>();

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "ইউছুব আলী");
        hashMap.put("number", "০১৭০৫৭২৪৩৪০");
        hashMap.put("imageLink", "https://shorturl.at/glMXY");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মো: মমিনুল ইসলাম");
        hashMap.put("number", "০১৯৪৯০৩২২৮৫");
        hashMap.put("imageLink", "https://shorturl.at/gpCOP");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মো: আব্দুল হালীম");
        hashMap.put("number", "০১৭৭৪৬১২৯১৭");
        hashMap.put("imageLink", "https://shorturl.at/hqGMZ");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মো: হুমায়ুন রশীদ");
        hashMap.put("number", "০১৩১৪২১৪১৮৪");
        hashMap.put("imageLink", "https://shorturl.at/bcpNU");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "AD");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মো: বিপুল মিয়া");
        hashMap.put("number", "০১৭৪৪৭৪৩৬৮৮");
        hashMap.put("imageLink", "https://rb.gy/jhy1ka");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মো: কাশেদ আলী");
        hashMap.put("number", "০১৭৫১১৪৪৭৬৪");
        hashMap.put("imageLink", "https://rb.gy/05amjh");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মো: মমিনুল ইসলাম");
        hashMap.put("number", "০১৭৩৫৯৮০১৬৪");
        hashMap.put("imageLink", "https://rb.gy/manfoh");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "পবিত্র চন্দ্র রায়");
        hashMap.put("number", "০১৭৬৪৯৫০৪১৫");
        hashMap.put("imageLink", "https://rb.gy/gneuo8");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "AD");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মো: রফিকুল ইসলাম");
        hashMap.put("number", "০১৭৮৫৪০১৮৮২");
        hashMap.put("imageLink", "https://rb.gy/gurecq");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মো: আইয়ুব আলী");
        hashMap.put("number", "০১৭৫১৭১৯১৭০");
        hashMap.put("imageLink", "https://shorter.me/cX2Yp");
        arrayList.add(hashMap);


    }
    private void shohidbagUp(){
        arrayList= new ArrayList<>();

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ জবের আলী");
        hashMap.put("number", "০১৭৩৯০১২৬৭২");
        hashMap.put("imageLink", "https://shorturl.at/lyS19");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ নবীর উদ্দিন");
        hashMap.put("number", "০১৭৭৩২৬৮৪৪৬");
        hashMap.put("imageLink", "https://rb.gy/ccjnd3");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আব্দুল আউয়াল");
        hashMap.put("number", "০১৯৮৪৮০৫৬২২");
        hashMap.put("imageLink", "https://rb.gy/ukrzwt");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আব্দুল মান্নান");
        hashMap.put("number", "০১৭৫০৭৯৬৪৭১");
        hashMap.put("imageLink", "https://rb.gy/3m4jsr");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "AD");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আবজাল হোসেন");
        hashMap.put("number", "০১৭২৩-৬৭২৬৫৩");
        hashMap.put("imageLink", "https://rb.gy/1640ad");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মো: নুরুন্নবী মিয়া");
        hashMap.put("number", "০১১৯০৮৯১৮৯৩");
        hashMap.put("imageLink", "https://shorturl.at/InNBQ");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মো: নুর আলম");
        hashMap.put("number", "০১৭৩৬০৬৪৩৭৭");
        hashMap.put("imageLink", "https://shorter.me/h3PAP");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মো: হায়দার আলী");
        hashMap.put("number", "০১৭২১৩৫৪৪৩৫");
        hashMap.put("imageLink", "https://shorter.me/Wizu9");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "AD");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মো: বাবুল মিয়া");
        hashMap.put("number", "০১৯৫১৭২৬৯৩১");
        hashMap.put("imageLink", "https://shorter.me/D1cNk");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মো: সায়েদ আলী(দফাদার)");
        hashMap.put("number", "০১৯৬৪৫১৫০০৮");
        hashMap.put("imageLink", "https://shorter.me/dFuUI");
        arrayList.add(hashMap);


    }
    private void balaparaUp(){
        arrayList= new ArrayList<>();

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "শ্রী সুবোধ চন্দ্র বর্মন");
        hashMap.put("number", "০১৭৫১০৮৫২০০");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ ফকরুল ইসলাম");
        hashMap.put("number", "০১৯৮৪৬৯১১১৮");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ বদি উজ্জামান");
        hashMap.put("number", "০১৭২০৬৮৭৮৩২");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ জামাল উদ্দিন");
        hashMap.put("number", "০১৯৮৭৩২৭১২৯");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "AD");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ নৈছতুল্লাহ");
        hashMap.put("number", "০১৯১৮৩৪৪০৩১");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "শ্রী গোলাপ চন্দ্র");
        hashMap.put("number", "০১৯১১৯৮০২৬১");
        arrayList.add(hashMap);


        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আমজাদ হোসেন");
        hashMap.put("number", "০১৯৬২৭৪২৯৬৩");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "শ্রী লক্ষি কান্ত রায়");
        hashMap.put("number", "০১৯১৮০৭৬৩২১");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "AD");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "শ্রী বাদল চন্দ্র বর্মন");
        hashMap.put("number", "০১৭৩৮৬২১২৮৪");
        arrayList.add(hashMap);


    }
    private void modhupurUp(){
        arrayList= new ArrayList<>();

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ নুরুল ইসলাম");
        hashMap.put("number", "০১৯৬৬০৫৪১৯০");
        hashMap.put("imageLink", "https://shorter.me/iqh8E");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আব্দুল কাদের");
        hashMap.put("number", "০১৭৩১২৯৪৮৯৫");
        hashMap.put("imageLink", "https://shorter.me/kjDIH");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ জাহেদুর রহমান");
        hashMap.put("number", "০১৭২০৫৯৪৩২৩");
        hashMap.put("imageLink", "https://shorter.me/9G-kY");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আব্দুল বাতেন");
        hashMap.put("number", "০১৭৪৬১০০৪৫৩");
        hashMap.put("imageLink", "https://shorter.me/qdQLx");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "AD");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আব্দুল ছামাদ");
        hashMap.put("number", "০১৭৮০৯৮০৬২২");
        hashMap.put("imageLink", "https://shorter.me/y0UqH");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ আবু ছিদ্দিক");
        hashMap.put("number", "০১৭৯২৮১১৭২৭");
        hashMap.put("imageLink", "https://shorter.me/8fzy6");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ হাছান শেখ");
        hashMap.put("number", "০১৭৯৯১৫৯১৫৬");
        hashMap.put("imageLink", "https://shorter.me/jBZW4");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "শ্রী সুশান্ত চন্দ্র বর্মন");
        hashMap.put("number", "০১৭৩৮৬৯৫১৮০");
        hashMap.put("imageLink", "https://shorter.me/3aZVB");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "AD");
        arrayList.add(hashMap);

        hashMap= new HashMap<>();
        hashMap.put("TYPE", "ITEM");
        hashMap.put("name", "মোঃ সাজু মিয়া");
        hashMap.put("number", "০১৩১৯১৬৫২৭৩");
        hashMap.put("imageLink", "https://shorter.me/sM5Vy");
        arrayList.add(hashMap);


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}