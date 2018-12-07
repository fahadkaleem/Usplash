package com.mohammedfahadkaleem.usplash.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mohammedfahadkaleem.usplash.Constants;
import com.mohammedfahadkaleem.usplash.R;
import com.mohammedfahadkaleem.usplash.Unsplash.Unsplash;
import com.mohammedfahadkaleem.usplash.Unsplash.Unsplash.OnPhotoLoadedListener;
import com.mohammedfahadkaleem.usplash.Unsplash.Unsplash.OnPhotoStatsLoadedListener;
import com.mohammedfahadkaleem.usplash.models.Photo;
import com.mohammedfahadkaleem.usplash.models.PhotoStats;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;

public class PhotoDetailsActivity extends AppCompatActivity {

  private static final String TAG = "PhotoDetailsActivity";
  ImageView photo_iv;
  TextView description, date, likes, downloads, views;
  ImageView userProfilePicture;
  //ImageView gear_info;
  TextView username;
  TextView tv_make, tv_model, tv_focalLength, tv_aperture, tv_iso, tv_exposure;
  String make, model, focalLength, aperture, iso, exposure;
  Photo currentPhoto;
  LinearLayout userLinearLayout;
  private String photoId;
  private Unsplash unsplash;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photo_details);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle(null);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    photo_iv = findViewById(R.id.photo);
    description = findViewById(R.id.description);
    date = findViewById(R.id.date);
    username = findViewById(R.id.username);
    userProfilePicture = findViewById(R.id.user_profile_picture);
    likes = findViewById(R.id.image_likes);
    downloads = findViewById(R.id.image_downloads);
    views = findViewById(R.id.image_views);
    userLinearLayout = findViewById(R.id.user_linear_layout);
    //gear_info = findViewById(R.id.gear_info);

    final Intent intent = getIntent();
    photoId = intent.getStringExtra("photo");
    Toast.makeText(PhotoDetailsActivity.this, photoId, Toast.LENGTH_SHORT).show();

    //photoId = photo.getId();
    unsplash = new Unsplash(Constants.CLIENT_ID);
    unsplash.getPhoto(photoId, new OnPhotoLoadedListener() {
      @Override
      public void onComplete(Photo photo) {
        currentPhoto = photo;
        Picasso.get().load(photo.getUrls().getRegular()).into(photo_iv);
        Picasso.get().load(photo.getUser().getProfileImage().getSmall()).into(userProfilePicture);
        if (photo.getDescription() != null) {
          description.setText(StringUtils.capitalize(photo.getDescription()));
        } else {
          description.setVisibility(View.GONE);
        }
        String timeAgo = setDate(photo.getCreatedAt());
        date.setText(timeAgo);
        username.setText(photo.getUser().getUsername());

        make = photo.getExif().getMake();
        if (make.contains(" ")) {
          int i = make.indexOf(' ');
          make = make.substring(0, i);
        }

        model = photo.getExif().getModel();
        if (model.contains(" ")) {
          int j = model.indexOf(' ');
          model = model.substring(j);
        }

        focalLength = photo.getExif().getFocalLength();
        aperture = photo.getExif().getAperture();
        iso = photo.getExif().getIso().toString();
        exposure = photo.getExif().getExposureTime();

        userLinearLayout.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent userIntent = new Intent(PhotoDetailsActivity.this, ProfileActivity.class);
            userIntent.putExtra("username", photo.getUser().getUsername());
            startActivity(userIntent);
          }
        });
      }

      @Override
      public void onError(String error) {

      }
    });

    unsplash.getPhotoStats(photoId, new OnPhotoStatsLoadedListener() {
      @Override
      public void onComplete(PhotoStats photoStats) {
        Toast.makeText(PhotoDetailsActivity.this, "Stats loaded" + photoStats.getLikes(),
            Toast.LENGTH_SHORT).show();
        likes.setText(photoStats.getLikes().toString());
        downloads.setText(photoStats.getDownloads().toString());
        views.setText(photoStats.getViews().toString());

      }

      @Override
      public void onError(String error) {

      }
    });

    photo_iv.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(PhotoDetailsActivity.this, "Photo Clicked", Toast.LENGTH_SHORT).show();
        Intent photoIntent = new Intent(PhotoDetailsActivity.this, PhotoActivity.class);
        photoIntent.putExtra("photo", photoId);
        startActivity(photoIntent);
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_photo_details, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.info:
        showGearInfoDialog();
        return true;
      case R.id.download:
        Toast.makeText(this, "Pressed", Toast.LENGTH_SHORT).show();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private String setDate(String date) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
      sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
      long time = sdf.parse(date).getTime();
      long now = System.currentTimeMillis();
      CharSequence ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
      return ago.toString();
    } catch (java.text.ParseException e) {
      e.printStackTrace();
      return "";
    }
  }

  private void showGearInfoDialog() {
    final Dialog dialog = new Dialog(PhotoDetailsActivity.this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
    dialog.setContentView(R.layout.gear_dialog);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    dialog.setCancelable(true);
    tv_make = dialog.findViewById(R.id.make);
    tv_model = dialog.findViewById(R.id.model);
    tv_focalLength = dialog.findViewById(R.id.focal_length);
    tv_aperture = dialog.findViewById(R.id.aperture);
    tv_iso = dialog.findViewById(R.id.iso);
    tv_exposure = dialog.findViewById(R.id.exposure);
    tv_make.setText(make);
    tv_model.setText(model);
    tv_focalLength.setText(focalLength);
    tv_aperture.setText(aperture);
    tv_iso.setText(iso);
    tv_exposure.setText(exposure);
    dialog.show();
  }
}
