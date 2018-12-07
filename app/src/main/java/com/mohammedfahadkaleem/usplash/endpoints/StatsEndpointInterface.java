package com.mohammedfahadkaleem.usplash.endpoints;

import com.mohammedfahadkaleem.usplash.models.Stats;
import retrofit2.Call;
import retrofit2.http.GET;

public interface StatsEndpointInterface {

  @GET("stats/total")
  Call<Stats> getStats();

}
