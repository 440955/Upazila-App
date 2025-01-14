package com.arafat.kauniaupzila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class Union_Porishod extends AppCompatActivity {
    public static String UNION="";
    MaterialToolbar toolbar;
    ImageSlider imageSlider;
    TextView tv_unionName;
    CardView village_people, village_police, gramAdaoot, unionEmbulance;
    AdView adView;
    ProjectHelper projectHelper= new ProjectHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_union_porishod);
        getWindow().setStatusBarColor(ContextCompat.getColor(Union_Porishod.this, R.color.statusbar));
        getWindow().setNavigationBarColor(ContextCompat.getColor(Union_Porishod.this, R.color.statusbar));
        projectHelper.MobileAD(this);

        toolbar= findViewById(R.id.toolbar_union);
        adView= findViewById(R.id.bannerAd3);
        imageSlider= findViewById(R.id.imageSlider_union);
        tv_unionName= findViewById(R.id.tv_unionName);
        village_people= findViewById(R.id.village_people);
        village_police= findViewById(R.id.village_police);
        gramAdaoot= findViewById(R.id.gramAdalot);
        unionEmbulance= findViewById(R.id.unionEmbulance);

        AdRequest adRequest= new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(UNION);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_unionName.setText(UNION+" সম্পর্কে");

        clickListener(village_people, "village_people");
        clickListener(village_police, "village_police");
        clickListener(gramAdaoot, "gramAdalot");
        clickListener(unionEmbulance, "unionEmbulance");

        ArrayList<SlideModel>slideModels= new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.tistapar,"তিস্তার পাড়", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.kauniathana,"কাউনিয়া থানা", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);

    }

    private void clickListener(CardView card, String item){
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.contains("village_people")){
                    Village_People .VILLAGE= UNION;
                    startActivity(new Intent(Union_Porishod.this, Village_People.class));
                } else if (item.contains("village_police")) {
                    Village_Police.UNION=UNION;
                    startActivity(new Intent(Union_Porishod.this, Village_Police.class));
                } else if (item.contains("gramAdalot")) {
                    if (HomeFragment.isNetworkAvailable(Union_Porishod.this)){
                        Village_People.VILLAGE="gramAdalot";
                        startActivity(new Intent(Union_Porishod.this, Village_People.class));
                    }
                }
                else if (item.contains("unionEmbulance")) {

                    if (UNION.contains("কুর্শা ইউনিয়ন")){
                        Uri number = Uri.parse("tel:01761724142");
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                        startActivity(callIntent);

                    } else if (UNION.contains("বালাপাড়া ইউনিয়ন")) {

                        Uri number = Uri.parse("tel:01743404343");
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                        startActivity(callIntent);


                    } else if (UNION.contains("শহীদবাগ ইউনিয়ন")) {
                        Uri number = Uri.parse("tel:01908666687");
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                        startActivity(callIntent);

                    }
                    else {
                        Toast.makeText(Union_Porishod.this, "আপডেট চলছে",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}