package com.mohammedfahadkaleem.usplash.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.mohammedfahadkaleem.usplash.Constants;
import com.mohammedfahadkaleem.usplash.PhotoRecyclerAdapter;
import com.mohammedfahadkaleem.usplash.R;
import com.mohammedfahadkaleem.usplash.Unsplash.Unsplash;
import com.mohammedfahadkaleem.usplash.api.Order;
import com.mohammedfahadkaleem.usplash.models.Photo;
import com.mohammedfahadkaleem.usplash.utils.Utils;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener {

  Toolbar toolbar;
  private Unsplash unsplash;
  private PhotoRecyclerAdapter adapter;
  private String TAG = "Main Activity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initToolbar();
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    unsplash = new Unsplash(Constants.CLIENT_ID);

    RecyclerView recyclerView = findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    adapter = new PhotoRecyclerAdapter();
    recyclerView.setAdapter(adapter);

    adapter.setOnItemClickListener(new PhotoRecyclerAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, Photo photo, int position) {
        Toast.makeText(MainActivity.this, "Photo " + photo.getId() + " Clicked", Toast.LENGTH_SHORT)
            .show();
        Intent intent = new Intent(MainActivity.this, PhotoDetailsActivity.class);
        Log.d(TAG, "Photo Id:" + photo.getId());
        intent.putExtra("photo", photo.getId());
        startActivity(intent);
      }
    });

    unsplash.getPhotos(1, 100, Order.POPULAR, new Unsplash.OnPhotosLoadedListener() {
      @Override
      public void onComplete(List<Photo> photos) {
        adapter.setPhotos(photos);
      }

      @Override
      public void onError(String error) {

      }
    });

  }

  private void initToolbar() {
    toolbar = findViewById(R.id.toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_menu);
    toolbar.getNavigationIcon()
        .setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
    setSupportActionBar(toolbar);
    //getSupportActionBar().setTitle("Usplash");
    getSupportActionBar().setTitle(null);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    Utils.setSystemBarColor(this, R.color.grey_5);
    Utils.setSystemBarLight(this);

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_search:
        Intent intent = new Intent(this, SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera) {
      // Handle the camera action
    } else if (id == R.id.nav_gallery) {

    } else if (id == R.id.nav_slideshow) {

    } else if (id == R.id.nav_manage) {

    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

    }

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }


/*    public void search(View view){
      EditText editText = findViewById(R.id.editText);
      String query = editText.getText().toString();

      unsplash.searchPhotos(query, new Unsplash.OnSearchCompleteListener() {
        @Override
        public void onComplete(SearchResults results) {
          Log.d("Photos", "Total Results Found " + results.getTotal());
          List<Photo> photos = results.getResults();
          adapter.setPhotos(photos);
        }

        @Override
        public void onError(String error) {
          Log.d("Unsplash", error);
        }
      });
    }*/

}
