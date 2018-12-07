package com.mohammedfahadkaleem.usplash.Unsplash;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.mohammedfahadkaleem.usplash.Constants;
import com.mohammedfahadkaleem.usplash.api.HeaderInterceptor;
import com.mohammedfahadkaleem.usplash.api.Order;
import com.mohammedfahadkaleem.usplash.endpoints.CollectionsEndpointInterface;
import com.mohammedfahadkaleem.usplash.endpoints.PhotosEndpointInterface;
import com.mohammedfahadkaleem.usplash.endpoints.StatsEndpointInterface;
import com.mohammedfahadkaleem.usplash.endpoints.UsersEndpointInterface;
import com.mohammedfahadkaleem.usplash.models.Collection;
import com.mohammedfahadkaleem.usplash.models.Download;
import com.mohammedfahadkaleem.usplash.models.Photo;
import com.mohammedfahadkaleem.usplash.models.PhotoStats;
import com.mohammedfahadkaleem.usplash.models.SearchResults;
import com.mohammedfahadkaleem.usplash.models.Stats;
import com.mohammedfahadkaleem.usplash.models.User;
import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fahadkaleem on 12/4/18.
 */
public class Unsplash {

  private PhotosEndpointInterface photosApiService;
  private CollectionsEndpointInterface collectionsApiService;
  private StatsEndpointInterface statsApiService;
  private UsersEndpointInterface usersEndpointService;
  private String TAG = "Unsplash";

  public Unsplash(String clientId) {
    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(new HeaderInterceptor(clientId)).build();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    photosApiService = retrofit.create(PhotosEndpointInterface.class);
    collectionsApiService = retrofit.create(CollectionsEndpointInterface.class);
    statsApiService = retrofit.create(StatsEndpointInterface.class);
    usersEndpointService = retrofit.create(UsersEndpointInterface.class);
  }

  public void getPhotos(Integer page, Integer perPage, Order order,
      final OnPhotosLoadedListener listener) {
    Call<List<Photo>> call = photosApiService.getPhotos(page, perPage, order.getOrder());
    call.enqueue(getMultiplePhotoCallback(listener));
  }

  @Deprecated
  public void getCuratedPhotos(Integer page, Integer perPage, Order order,
      final OnPhotosLoadedListener listener) {
    Call<List<Photo>> call = photosApiService.getCuratedPhotos(page, perPage, order.getOrder());
    call.enqueue(getMultiplePhotoCallback(listener));
  }

  public void getPhoto(@NonNull String id, final OnPhotoLoadedListener listener) {
    getPhoto(id, null, null, listener);
  }

  public void getPhotoStats(@NonNull String id, final OnPhotoStatsLoadedListener listener) {
    Call<PhotoStats> call = photosApiService.getPhotoStats(id);
    call.enqueue(getSinglePhotoStatsCallback(listener));
  }

  public void getRandomPhoto(@Nullable String collections,
      @Nullable Boolean featured, @Nullable String username,
      @Nullable String query, @Nullable Integer width,
      @Nullable Integer height, @Nullable String orientation, OnPhotoLoadedListener listener) {
    Call<Photo> call = photosApiService
        .getRandomPhoto(collections, featured, username, query, width, height, orientation);
    call.enqueue(getSinglePhotoCallback(listener));
  }

  public void getRandomPhotos(@Nullable String collections,
      @Nullable Boolean featured, @Nullable String username,
      @Nullable String query, @Nullable Integer width,
      @Nullable Integer height, @Nullable String orientation,
      @Nullable Integer count, OnPhotosLoadedListener listener) {
    Call<List<Photo>> call = photosApiService
        .getRandomPhotos(collections, featured, username, query, width, height, orientation, count);
    call.enqueue(getMultiplePhotoCallback(listener));
  }

  public void searchPhotos(@NonNull String query, OnSearchCompleteListener listener) {
    searchPhotos(query, null, null, null, listener);
  }

  public void searchPhotos(@NonNull String query, @Nullable Integer page, @Nullable Integer perPage,
      @Nullable String orientation, OnSearchCompleteListener listener) {
    Call<SearchResults> call = photosApiService.searchPhotos(query, page, perPage, orientation);
    call.enqueue(getSearchResultsCallback(listener));
  }

  public void getPhotoDownloadLink(@NonNull String id, final OnLinkLoadedListener listener) {
    Call<Download> call = photosApiService.getPhotoDownloadLink(id);
    call.enqueue(new Callback<Download>() {
                   @Override
                   public void onResponse(Call<Download> call, Response<Download> response) {
                     int statusCode = response.code();
                     if (statusCode == 200) {
                       Log.d(TAG, response.body().getUrl());
                       listener.onComplete(response.body());
                     } else if (statusCode == 401) {
                       Log.d(TAG, "Unauthorized, Check your client Id");
                     }
                   }

                   @Override
                   public void onFailure(Call<Download> call, Throwable t) {
                     listener.onError(t.getMessage());
                   }
                 }
    );
  }

  public void getPhoto(@NonNull String id, @Nullable Integer width, @Nullable Integer height,
      final OnPhotoLoadedListener listener) {
    Call<Photo> call = photosApiService.getPhoto(id, width, height);
    call.enqueue(getSinglePhotoCallback(listener));
  }


  public void getCollections(Integer page, Integer perPage,
      final OnCollectionsLoadedListener listener) {
    Call<List<Collection>> call = collectionsApiService.getCollections(page, perPage);
    call.enqueue(getMultipleCollectionsCallback(listener));
  }

  public void getFeaturedCollections(Integer page, Integer perPage,
      final OnCollectionsLoadedListener listener) {
    Call<List<Collection>> call = collectionsApiService.getFeaturedCollections(page, perPage);
    call.enqueue(getMultipleCollectionsCallback(listener));
  }

  @Deprecated
  public void getCuratedCollections(Integer page, Integer perPage,
      final OnCollectionsLoadedListener listener) {
    Call<List<Collection>> call = collectionsApiService.getCuratedCollections(page, perPage);
    call.enqueue(getMultipleCollectionsCallback(listener));
  }

  public void getRelatedCollections(String id, final OnCollectionsLoadedListener listener) {
    Call<List<Collection>> call = collectionsApiService.getRelatedCollections(id);
    call.enqueue(getMultipleCollectionsCallback(listener));
  }

  public void getCollection(String id, final OnCollectionLoadedListener listener) {
    Call<Collection> call = collectionsApiService.getCollection(id);
    call.enqueue(getSingleCollectionCallback(listener));
  }

  @Deprecated
  public void getCuratedCollection(String id, final OnCollectionLoadedListener listener) {
    Call<Collection> call = collectionsApiService.getCuratedCollection(id);
    call.enqueue(getSingleCollectionCallback(listener));
  }

  public void getCollectionPhotos(String id, Integer page, Integer perPage,
      final OnPhotosLoadedListener listener) {
    Call<List<Photo>> call = collectionsApiService.getCollectionPhotos(id, page, perPage);
    call.enqueue(getMultiplePhotoCallback(listener));
  }

  @Deprecated
  public void getCuratedCollectionPhotos(String id, Integer page, Integer perPage,
      final OnPhotosLoadedListener listener) {
    Call<List<Photo>> call = collectionsApiService.getCuratedCollectionPhotos(id, page, perPage);
    call.enqueue(getMultiplePhotoCallback(listener));
  }

  public void getStats(final OnStatsLoadedListener listener) {
    Call<Stats> call = statsApiService.getStats();
    call.enqueue(new Callback<Stats>() {
                   @Override
                   public void onResponse(Call<Stats> call, Response<Stats> response) {
                     int statusCode = response.code();
                     Log.d(TAG, "Status Code = " + statusCode);
                     if (statusCode == 200) {
                       listener.onComplete(response.body());
                     } else if (statusCode == 401) {
                       Log.d(TAG, "Unauthorized, Check your client Id");
                     }
                   }

                   @Override
                   public void onFailure(Call<Stats> call, Throwable t) {
                     listener.onError(t.getMessage());
                   }
                 }
    );
  }

  public void getUser(@NonNull String username, final OnUserLoadedListener listener) {
    Call<User> call = usersEndpointService.getUser(username);
    call.enqueue(getSingleUserCallback(listener));
  }

  // CALLBACKS

  private Callback<Photo> getSinglePhotoCallback(final OnPhotoLoadedListener listener) {
    return new Callback<Photo>() {
      @Override
      public void onResponse(Call<Photo> call, Response<Photo> response) {
        int statusCode = response.code();
        Log.d(TAG, "Status Code = " + statusCode);
        if (statusCode == 200) {
          Photo photo = response.body();
          listener.onComplete(photo);
        } else if (statusCode == 401) {
          Log.d(TAG, "Unauthorized, Check your client Id");
        }
      }

      @Override
      public void onFailure(Call<Photo> call, Throwable t) {
        listener.onError(t.getMessage());
      }
    };
  }

  private Callback<PhotoStats> getSinglePhotoStatsCallback(
      final OnPhotoStatsLoadedListener listener) {
    return new Callback<PhotoStats>() {
      @Override
      public void onResponse(Call<PhotoStats> call, Response<PhotoStats> response) {
        int statusCode = response.code();
        Log.d(TAG, "Status Code = " + statusCode);
        if (statusCode == 200) {
          PhotoStats photoStats = response.body();
          listener.onComplete(photoStats);
        } else if (statusCode == 401) {
          Log.d(TAG, "Unauthorized, Check your client Id");
        }
      }

      @Override
      public void onFailure(Call<PhotoStats> call, Throwable t) {
        listener.onError(t.getMessage());
      }
    };
  }

  private Callback<List<Photo>> getMultiplePhotoCallback(final OnPhotosLoadedListener listener) {
    return new Callback<List<Photo>>() {
      @Override
      public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
        int statusCode = response.code();
        Log.d(TAG, "Url = " + call.request().url());
        Log.d(TAG, "Status Code = " + statusCode);
        if (statusCode == 200) {
          List<Photo> photos = response.body();
          listener.onComplete(photos);
        } else if (statusCode == 401) {
          Log.d(TAG, "Unauthorized, Check your client Id");
        }
      }

      @Override
      public void onFailure(Call<List<Photo>> call, Throwable t) {
        Log.d(TAG, "Url = " + call.request().url());
        listener.onError(t.getMessage());
      }
    };
  }

  private Callback<Collection> getSingleCollectionCallback(
      final OnCollectionLoadedListener listener) {
    return new Callback<Collection>() {
      @Override
      public void onResponse(Call<Collection> call, Response<Collection> response) {
        int statusCode = response.code();
        Log.d(TAG, "Status Code = " + statusCode);
        if (statusCode == 200) {
          Collection collections = response.body();
          listener.onComplete(collections);
        } else if (statusCode == 401) {
          Log.d(TAG, "Unauthorized, Check your client Id");
        }
      }

      @Override
      public void onFailure(Call<Collection> call, Throwable t) {
        listener.onError(t.getMessage());
      }
    };
  }

  private Callback<SearchResults> getSearchResultsCallback(
      final OnSearchCompleteListener listener) {
    return new Callback<SearchResults>() {
      @Override
      public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
        int statusCode = response.code();
        Log.d(TAG, "Status Code = " + statusCode);
        if (statusCode == 200) {
          SearchResults results = response.body();
          listener.onComplete(results);
        } else if (statusCode == 401) {
          Log.d(TAG, "Unauthorized, Check your client Id");
        }
      }

      @Override
      public void onFailure(Call<SearchResults> call, Throwable t) {
        listener.onError(t.getMessage());
      }
    };
  }

  private Callback<List<Collection>> getMultipleCollectionsCallback(
      final OnCollectionsLoadedListener listener) {
    return new Callback<List<Collection>>() {
      @Override
      public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
        int statusCode = response.code();
        Log.d(TAG, "Status Code = " + statusCode);
        if (statusCode == 200) {
          List<Collection> collections = response.body();
          listener.onComplete(collections);
        } else if (statusCode == 401) {
          Log.d(TAG, "Unauthorized, Check your client Id");
        }
      }

      @Override
      public void onFailure(Call<List<Collection>> call, Throwable t) {
        listener.onError(t.getMessage());
      }
    };
  }

  private Callback<User> getSingleUserCallback(final OnUserLoadedListener listener) {
    return new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        int statusCode = response.code();
        Log.d(TAG, "Status Code = " + statusCode);
        if (statusCode == 200) {
          User user = response.body();
          listener.onComplete(user);
        } else if (statusCode == 401) {
          Log.d(TAG, "Unauthorized, Check your client Id");
        }
      }

      @Override
      public void onFailure(Call<User> call, Throwable t) {
        listener.onError(t.getMessage());
      }
    };
  }

  public interface OnPhotosLoadedListener {

    void onComplete(List<Photo> photos);

    void onError(String error);
  }

  public interface OnSearchCompleteListener {

    void onComplete(SearchResults results);

    void onError(String error);
  }

  public interface OnPhotoLoadedListener {

    void onComplete(Photo photo);

    void onError(String error);
  }

  public interface OnPhotoStatsLoadedListener {

    void onComplete(PhotoStats photoStats);

    void onError(String error);
  }

  public interface OnUserLoadedListener {

    void onComplete(User user);

    void onError(String error);
  }

  public interface OnLinkLoadedListener {

    void onComplete(Download downloadLink);

    void onError(String error);
  }

  public interface OnCollectionsLoadedListener {

    void onComplete(List<Collection> collections);

    void onError(String error);
  }

  public interface OnCollectionLoadedListener {

    void onComplete(Collection photos);

    void onError(String error);
  }

  public interface OnStatsLoadedListener {

    void onComplete(Stats stats);

    void onError(String error);
  }
}