package com.arafat.kauniaupzila;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toolbar;


public class NotificationFragment extends Fragment {

    WebView webView;
    ProgressBar progressBar;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View MyView= inflater.inflate(R.layout.fragment_notification, container, false);
        webView= MyView.findViewById(R.id.webViewNotification);
        progressBar= MyView.findViewById(R.id.progressBar6);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        }, 2000);

        webView.loadUrl("https://sites.google.com/view/kauniaupzillanews/home");
        webView.getSettings().setJavaScriptEnabled(true);

        return MyView;
    }
}