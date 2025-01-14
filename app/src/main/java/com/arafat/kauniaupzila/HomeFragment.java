package com.arafat.kauniaupzila;

import static android.app.ProgressDialog.show;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    ImageSlider imageSlider;
    CardView banglalink, grameen, robi, airtel, teletalk, helpline, nagad, bkash, rocket,
            emargency, voktaOdhidoptor, magistrate, manDoctor, homioDoctor, narch, upzilaDoctor,
            posuDoctor, bloodDonar, postoffice, dekorator, kazi, restorant, fireService, stampWriter, thana_police,
            pollibiddut, allKaunia, upzilaVumiOffice, bank, hospital, hat_bajar, pharmecy, dis_internet, reporter, kuriarService,
            Ambulance;
    LinearLayout noticeBox;
    TextView noticeType, notice;
    ImageView imageAd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView= inflater.inflate(R.layout.fragment_home, container, false);

        imageAd= myView.findViewById(R.id.imageAd);
        noticeBox= myView.findViewById(R.id.noticeBox);
        noticeType= myView.findViewById(R.id.noticeType);
        notice= myView.findViewById(R.id.tv_notice);
        imageSlider=myView.findViewById(R.id.image_slider);
        banglalink= myView.findViewById(R.id.banglaLink);
        grameen= myView.findViewById(R.id.grameen);
        robi= myView.findViewById(R.id.robi);
        airtel= myView.findViewById(R.id.airtel);
        teletalk= myView.findViewById(R.id.teletalk);
        helpline= myView.findViewById(R.id.helpLine);
        nagad= myView.findViewById(R.id.nagad);
        bkash= myView.findViewById(R.id.bkash);
        rocket= myView.findViewById(R.id.rocket);
        emargency= myView.findViewById(R.id.emargency);
        voktaOdhidoptor= myView.findViewById(R.id.voktaOdhidoptor);
        magistrate= myView.findViewById(R.id.magistrate);
        manDoctor= myView.findViewById(R.id.manDoctor);
        homioDoctor= myView.findViewById(R.id.homioDoctor);
        narch= myView.findViewById(R.id.narch);
        upzilaDoctor= myView.findViewById(R.id.upzilaDoctor);
        posuDoctor= myView.findViewById(R.id.posuDoctor);
        bloodDonar= myView.findViewById(R.id.bloodDonar);
        postoffice= myView.findViewById(R.id.postoffice);
        dekorator= myView.findViewById(R.id.dekorator);
        kazi= myView.findViewById(R.id.kazi);
        restorant= myView.findViewById(R.id.restorant);
        fireService= myView.findViewById(R.id.fireService);
        stampWriter= myView.findViewById(R.id.stampWriter);
        thana_police= myView.findViewById(R.id.thana_police);
        pollibiddut= myView.findViewById(R.id.pollibiddut);
        allKaunia=myView.findViewById(R.id.allKaunia);
        upzilaVumiOffice=myView.findViewById(R.id.upzilaVumiOffice);
        bank=myView.findViewById(R.id.bank);
        hat_bajar=myView.findViewById(R.id.hat_bajar);
        pharmecy=myView.findViewById(R.id.pharmecy);
        dis_internet=myView.findViewById(R.id.dis_internet);
        reporter=myView.findViewById(R.id.reporter);
        kuriarService=myView.findViewById(R.id.kuriarService);
        hospital=myView.findViewById(R.id.hospital);
        Ambulance=myView.findViewById(R.id.Ambulance);




        collectingData(upzilaVumiOffice);
        collectingData(bank);
        collectingData(hospital);
        collectingData(hat_bajar);
        collectingData(pharmecy);
        collectingData(dis_internet);
        collectingData(reporter);
        collectingData(kuriarService);


        itemClick(bloodDonar, "blooddoner");
        itemClick(dekorator, "dekorator");

        doctorCardClick(manDoctor, "mandoctor");
        doctorCardClick(homioDoctor, "homioDoctor");
        doctorCardClick(upzilaDoctor, "upzilaDoctor");
        doctorCardClick(posuDoctor, "posuDoctor");

        cardClick(banglalink, "banglalink");
        cardClick(grameen, "grameen");
        cardClick(robi, "robi");
        cardClick(airtel, "airtel");
        cardClick(teletalk, "teletalk");
        cardClick(helpline, "helpline");
        cardClick(narch, "narch");
        cardClick(stampWriter, "stampWriter");


        MobileBank(nagad, "*167#");
        MobileBank(bkash, "*247#");
        MobileBank(rocket, "*322#");
        MobileBank(emargency, "999");
        MobileBank(voktaOdhidoptor, "০১৭৬১৪৯১৩২৪");
        MobileBank(magistrate, "০১৭৬১৪৯১৩২৪");

        allKaunia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(getActivity())){
                    Village_People.VILLAGE="ALLKAUNIA";
                    startActivity(new Intent(getActivity(), Village_People.class));
                }
                else {
                    Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pollibiddut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(getActivity())){
                    Thana_Police.OPERATOR="pollibiddut";
                    startActivity(new Intent(getActivity(), Thana_Police.class));
                }
                else {
                    Toast.makeText(getActivity(), "ইন্টারনেট সংযোগ দিন", Toast.LENGTH_SHORT).show();
                }
            }
        });

        thana_police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(getActivity())){
                    Thana_Police.OPERATOR="thanapolice";
                    startActivity(new Intent(getActivity(), Thana_Police.class));
                }
                else {
                    Toast.makeText(getActivity(), "ইন্টারনেট সংযোগ দিন", Toast.LENGTH_SHORT).show();
                }
            }
        });

        postoffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Item_View.class));
            }
        });

        kazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Village_People.VILLAGE="kazi";
                startActivity(new Intent(getActivity(), Village_People.class));
            }
        });
        fireService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Village_Police.UNION="fireService";
               startActivity(new Intent(getActivity(), Village_Police.class));
            }
        });
        Ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Village_Police.UNION="Ambulance";
                startActivity(new Intent(getActivity(), Village_Police.class));
            }
        });

        restorant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(getActivity())){
                    Doctors_View.DOCTOR="restorant";
                    startActivity(new Intent(getActivity(), Doctors_View.class));
                }
                else {
                    Toast.makeText(getActivity(), "ইন্টারনেট সংযোগ দিন", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ArrayList<SlideModel> slideModels= new ArrayList<>();
        slideModels.add(new SlideModel("https://www.daily-sun.com/_next/image?url=https%3A%2F%2Fcdn.daily-sun.com%2Fpublic%2Fnews_images%2F2017%2F10%2F04%2Fdaily-sun-2017-10-04-AK-29.jpg&w=828&q=100", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.tbsnews.net/sites/default/files/styles/big_3/public/images/2020/07/10/photo_4.png", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://tds-images.thedailystar.net/upload/gallery/image/arts/teesta-turns-furious.jpg","তিস্তার পাড়", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxi1ABCWR_n3mJLnZBZ6Wloy68R7YumjqeIdDAoNPi9KbhQnObOkx-IrSGwJxMwqVzh0w&usqp=CAU",ScaleTypes.FIT));

        ArrayList<SlideModel> slideModels2= new ArrayList<>();
        slideModels2.add(new SlideModel(R.drawable.tistapar,"তিস্তার পাড়", ScaleTypes.FIT));
        slideModels2.add(new SlideModel(R.drawable.kauniathana,"কাউনিয়া থানা", ScaleTypes.FIT));

        if (isNetworkAvailable(getActivity())){
            imageSlider.setImageList(slideModels);
        } else {
            imageSlider.setImageList(slideModels2);
        }

        NoticeRequest();
        imageAdRequest();
        imageAdClick();

        return myView;
    }
//=======================================================================================
    private void doctorCardClick (CardView cardView, String doctor){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(getActivity())){
                    Doctors_View.DOCTOR=doctor;
                    startActivity(new Intent(getActivity(), Doctors_View.class));
                }
                else {
                    Toast.makeText(getActivity(), "ইন্টারনেট সংযোগ দিন", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cardClick(CardView cardView, String operator){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(getActivity())){
                    SimCode_view.OPERATOR=operator;
                    startActivity(new Intent(getActivity(), SimCode_view.class));
                }
                else {
                    Toast.makeText(getActivity(), "ইন্টারনেট সংযোগ দিন", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void itemClick(CardView cardView, String item_name){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(getActivity())){
                    Blood_Doner.ITEM=item_name;
                    startActivity(new Intent(getActivity(), Blood_Doner.class));
                }
                else{
                    Toast.makeText(getActivity(), "আপনার ইন্টারনেট সংযোগটি চালু করুন", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void MobileBank(CardView cardView, String number){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri= Uri.parse("tel:"+number+"");
                Intent dial= new Intent(Intent.ACTION_DIAL, uri);
                startActivity(dial);
            }
        });
    }

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo() != null;
    }


    public void collectingData(CardView cardView){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog alertDialog;
                androidx.appcompat.app.AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
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
        });
    }

    private void NoticeRequest(){
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        String link="https://mdarafathossain.000webhostapp.com/apps/upzilaAppNotice.json";
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                noticeBox.setVisibility(View.VISIBLE);
                try {
                    noticeType.setText(response.getString("type"));
                    notice.setText(response.getString("comment"));
                    notice.setSelected(true);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                noticeBox.setVisibility(View.VISIBLE);
                noticeType.setText("নোটিশঃ ");
                notice.setText("প্রিয় উপজেলাবাসি, আমরা অনলাইন ভিত্তিক বিভিন্ন সেবা দেওয়ার জন্য প্রতিনিওত অ্যাপ আপডেট করে যাচ্ছি। দয়া করে আমাদের সাথেই থাকুন");
                notice.setSelected(true);
                if (isNetworkAvailable(getActivity())){
                    NoticeRequest();
                }
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void imageAdRequest(){
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        String imageUrl="https://mdarafathossain.000webhostapp.com/apps/adImage/adbannerimage.png";
        ImageRequest imageRequest= new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageAd.setVisibility(View.VISIBLE);
                imageAd.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isNetworkAvailable(getActivity())){
                    imageAdRequest();
                }
            }
        });
        requestQueue.add(imageRequest);
    }

    private void imageAdClick(){
        imageAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
                String link="https://mdarafathossain.000webhostapp.com/apps/adImage/adlink.php";
                StringRequest stringRequest= new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent view= new Intent(Intent.ACTION_VIEW);
                        view.setData(Uri.parse(response));
                        startActivity(view);
                    }
                }, null);
                requestQueue.add(stringRequest);
            }
        });
    }

}