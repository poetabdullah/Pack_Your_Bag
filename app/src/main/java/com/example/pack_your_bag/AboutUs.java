package com.example.pack_your_bag;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class AboutUs extends AppCompatActivity {

    ImageView imgYoutube, imgInstagarm, imgTwitter;
    TextView txtEmail, txtWebsiteUrl;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About us");

        imgYoutube = findViewById(R.id.imgYoutube);
        txtEmail = findViewById(R.id.txtWebsiteUrl);
        txtWebsiteUrl = findViewById(R.id.txtWebsiteUrl);
        imgInstagarm = findViewById(R.id.imgInstagram);
        imgTwitter = findViewById(R.id.imgTwitter);

        imgYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToUrl("https://www.youtube.com/channel/UCxR4RgW9bABY4M8dxZhHwbw");
            }
        });

        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mail to: " + "abdullahimranarshad@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "From 'Pack Your Bag'");
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e){
                    System.out.println(e);
                }
            }
        });

        txtWebsiteUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToUrl("https://www.threads.net/@poet.abdullah");
            }
        });

        imgInstagarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToUrl("https://www.instagram.com/poet.abdullah/");
            }
        });

        imgTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToUrl("https://twitter.com/AbdulaImran");
            }
        });
    }

    private void navigateToUrl(String url){

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}