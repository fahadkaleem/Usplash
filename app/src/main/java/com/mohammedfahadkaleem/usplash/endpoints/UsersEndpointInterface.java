package com.mohammedfahadkaleem.usplash.endpoints;

import com.mohammedfahadkaleem.usplash.models.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by fahadkaleem on 12/6/18.
 */
public interface UsersEndpointInterface {

  @GET("users/{username}")
  Call<User> getUser(@Path("username") String username);
}
