package com.mohammedfahadkaleem.usplash.api;

/**
 * Created by fahadkaleem on 12/4/18.
 */
public enum Order {

  LATEST("latest"),
  OLDEST("oldest"),
  POPULAR("popular");

  private String order;

  Order(String order) {
    this.order = order;
  }

  public String getOrder() {
    return order;
  }
}

