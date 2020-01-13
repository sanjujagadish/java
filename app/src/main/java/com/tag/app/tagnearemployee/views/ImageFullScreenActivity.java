package com.tag.app.tagnearemployee.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tag.app.tagnearemployee.R;
import com.tag.app.tagnearemployee.appUtils.Constants;

public class ImageFullScreenActivity extends AppCompatActivity {

    private String url;
    private ImageView myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_image_full_screen );

        url = getIntent().getStringExtra("image_url");

        myImage = findViewById(R.id.imageView);
        Glide.with(this).load( Constants.CATIMAGE_URL+url)
                .into(myImage); }}
