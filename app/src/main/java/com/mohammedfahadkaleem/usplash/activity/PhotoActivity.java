package com.mohammedfahadkaleem.usplash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.mohammedfahadkaleem.usplash.Constants;
import com.mohammedfahadkaleem.usplash.R;
import com.mohammedfahadkaleem.usplash.Unsplash.Unsplash;
import com.mohammedfahadkaleem.usplash.Unsplash.Unsplash.OnPhotoLoadedListener;
import com.mohammedfahadkaleem.usplash.models.Photo;
import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {

  String photoId;
  ImageView photo_iv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photo);
    photo_iv = findViewById(R.id.photo);
    final Intent intent = getIntent();
    photoId = intent.getStringExtra("photo");

    Unsplash unsplash = new Unsplash(Constants.CLIENT_ID);
    unsplash.getPhoto(photoId, new OnPhotoLoadedListener() {
      @Override
      public void onComplete(Photo photo) {
        Picasso.get().load(photo.getUrls().getRegular()).into(photo_iv);
      }

      @Override
      public void onError(String error) {

      }
    });
  }
}
