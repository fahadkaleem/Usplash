package com.mohammedfahadkaleem.usplash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.mohammedfahadkaleem.usplash.Constants;
import com.mohammedfahadkaleem.usplash.PhotoRecyclerAdapter;
import com.mohammedfahadkaleem.usplash.R;
import com.mohammedfahadkaleem.usplash.Unsplash.Unsplash;
import com.mohammedfahadkaleem.usplash.models.Photo;
import com.mohammedfahadkaleem.usplash.models.SearchResults;
import com.mohammedfahadkaleem.usplash.utils.Utils;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

  Unsplash unsplash;
  private Toolbar toolbar;
  private PhotoRecyclerAdapter adapter;
  private String TAG = "Search Activity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    unsplash = new Unsplash(Constants.CLIENT_ID);
    initToolbar();
    adapter = new PhotoRecyclerAdapter();
    RecyclerView recyclerView = findViewById(R.id.searchResults);
    recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

    recyclerView.setAdapter(adapter);

    adapter.setOnItemClickListener(new PhotoRecyclerAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, Photo photo, int position) {
        Toast.makeText(SearchActivity.this, "Photo " + photo.getId() + " Clicked",
            Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SearchActivity.this, PhotoDetailsActivity.class);
        Log.d(TAG, "Photo Id:" + photo.getId());
        intent.putExtra("photo", photo.getId());
        startActivity(intent);
      }
    });

  }

  private void initToolbar() {
    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    Utils.setSystemBarColor(this, R.color.grey_5);
    Utils.setSystemBarLight(this);
  }

  public void search(View view) {
    EditText editText = findViewById(R.id.et_search);
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
  }
}
