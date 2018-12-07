package com.mohammedfahadkaleem.usplash.api;

/**
 * Created by fahadkaleem on 12/4/18.
 */
public enum Orientation {

  LANDSCAPE("landscape"),
  PORTRAIT("portrait"),
  SQUARISH("squarish");

  String orientation;

  Orientation(String orientation) {
    this.orientation = orientation;
  }

  public String getOrientation() {
    return orientation;
  }
}
