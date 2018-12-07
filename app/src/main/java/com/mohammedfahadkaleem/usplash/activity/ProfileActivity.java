package com.mohammedfahadkaleem.usplash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.mohammedfahadkaleem.usplash.Constants;
import com.mohammedfahadkaleem.usplash.R;
import com.mohammedfahadkaleem.usplash.Unsplash.Unsplash;
import com.mohammedfahadkaleem.usplash.Unsplash.Unsplash.OnUserLoadedListener;
import com.mohammedfahadkaleem.usplash.models.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

  String username;
  private Unsplash unsplash;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);
    initToolbar();
    initComponent();
    final Intent intent = getIntent();
    username = intent.getStringExtra("username");
    Toast.makeText(ProfileActivity.this, username, Toast.LENGTH_SHORT).show();
    Log.d("THIS NEEDS ATTENTION", username);
    unsplash = new Unsplash(Constants.CLIENT_ID);
    unsplash.getUser(username, new OnUserLoadedListener() {
      @Override
      public void onComplete(User user) {
        Toast.makeText(ProfileActivity.this, "User " + username, Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onError(String error) {

      }
    });
  }

  private void initToolbar() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_menu);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("Profile");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void initComponent() {
    final CircleImageView image = findViewById(R.id.image);
    final CollapsingToolbarLayout collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
    ((AppBarLayout) findViewById(R.id.app_bar_layout))
        .addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
          @Override
          public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            int min_height = ViewCompat.getMinimumHeight(collapsing_toolbar) * 2;
            float scale = (float) (min_height + verticalOffset) / min_height;
            image.setScaleX(scale >= 0 ? scale : 0);
            image.setScaleY(scale >= 0 ? scale : 0);
          }
        });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_profile, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
    } else {
      Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
    }
    return super.onOptionsItemSelected(item);
  }
}
