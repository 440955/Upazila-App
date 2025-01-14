package com.arafat.kauniaupzila;

import android.content.Context;

import com.google.android.gms.ads.MobileAds;
import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

public class ProjectHelper {


    public void onSignal(Context context){
        String ONESIGNAL_APP_ID = "41641249-1409-49de-8fbe-c075e0fc8b29";
        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);

        OneSignal.initWithContext(context, ONESIGNAL_APP_ID);

        OneSignal.getNotifications().requestPermission(false, Continue.none());
    }



    public void MobileAD(Context context){
        new Thread(
                () -> {
                    // Initialize the Google Mobile Ads SDK on a background thread.
                    MobileAds.initialize(context, initializationStatus -> {});
                })
                .start();
    }

}
