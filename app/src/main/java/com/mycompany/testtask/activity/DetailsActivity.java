package com.mycompany.testtask.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycompany.testtask.R;

import static com.mycompany.testtask.activity.MainActivity.EXTRA_ADDRESS;
import static com.mycompany.testtask.activity.MainActivity.EXTRA_EMAIL;
import static com.mycompany.testtask.activity.MainActivity.EXTRA_LAT;
import static com.mycompany.testtask.activity.MainActivity.EXTRA_LNG;
import static com.mycompany.testtask.activity.MainActivity.EXTRA_NAME;
import static com.mycompany.testtask.activity.MainActivity.EXTRA_PHONE_NUMBER;
import static com.mycompany.testtask.activity.MainActivity.EXTRA_WEBSITE;


public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TextView txtName, txtEmail, txtPhoneNum;
    private WebView webView;
    private String email;
    private String phoneNumber;
    private String strWebsite;
    private String street;
    private String lat;
    private String lng;
    public static final int REQUEST_CALL = 1;
    private GoogleMap mMap;
    private float defaultZoom = 10f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("User Details");
        setSupportActionBar(toolbar);
        txtName = findViewById(R.id.txt_details_name);
        txtEmail = findViewById(R.id.txt_details_email);
        txtPhoneNum = findViewById(R.id.txt_user_num);
        webView = findViewById(R.id.web_view);

        Intent intent = getIntent();
        String name = intent.getStringExtra(EXTRA_NAME);
        email = intent.getStringExtra(EXTRA_EMAIL);
        phoneNumber = intent.getStringExtra(EXTRA_PHONE_NUMBER);
        strWebsite = intent.getStringExtra(EXTRA_WEBSITE);
        street = intent.getStringExtra(EXTRA_ADDRESS);
        lat = intent.getStringExtra(EXTRA_LAT);
        lng = intent.getStringExtra(EXTRA_LNG);
        txtName.setText(name);
        txtEmail.setText(email);
        txtPhoneNum.setText(phoneNumber);
        setWebView(strWebsite);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(DetailsActivity.this);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_details_email:
                sendMail();
                break;
            case R.id.txt_user_num:
                makePhoneCall();
                break;
        }
    }

    private void makePhoneCall() {
        String number = phoneNumber;
        if (ContextCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));


        }
    }

    private void sendMail() {
        String recipient = email;
        String[] recipients = recipient.split(",");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }

    private void setWebView(String url) {
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("http://" + url + "/");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        initMap(googleMap);
    }

    private void initMap(GoogleMap googleMap) {
        mMap = googleMap;
        double myLat = Double.parseDouble(lat);
        double myLng = Double.parseDouble(lng);
        LatLng userLatLng = new LatLng(myLat, myLng);
        mMap.addMarker(new MarkerOptions().position(userLatLng).title(street + " St"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, defaultZoom), 5000, null);
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        mMap.clear();
        webView.stopLoading();
        webView.clearFormData();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        webView.loadUrl("about:blank");
        webView.onPause();
        webView.pauseTimers();
        super.onPause();
    }

    @Override
    protected void onResume() {
        webView.resumeTimers();
        webView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        mMap.clear();
        super.onDestroy();
    }
}
