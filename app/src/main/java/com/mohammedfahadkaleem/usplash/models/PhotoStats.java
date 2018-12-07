package com.mohammedfahadkaleem.usplash.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fahadkaleem on 12/6/18.
 */
public class PhotoStats {

  public final static Creator<PhotoStats> CREATOR = new Creator<PhotoStats>() {
    public PhotoStats createFromParcel(Parcel in) {
      PhotoStats instance = new PhotoStats();
      instance.downloads = ((Integer) in.readValue((Integer.class.getClassLoader())));
      instance.views = ((Integer) in.readValue((Integer.class.getClassLoader())));
      instance.likes = ((Integer) in.readValue((Integer.class.getClassLoader())));
      instance.links = ((Links) in.readValue(String.class.getClassLoader()));
      return instance;
    }

    public PhotoStats[] newArray(int size) {
      return (new PhotoStats[size]);
    }

  };
  @SerializedName("downloads")
  @Expose
  public Integer downloads;
  @SerializedName("views")
  @Expose
  public Integer views;
  @SerializedName("likes")
  @Expose
  public Integer likes;
  @SerializedName("links")
  @Expose
  public Links links;

  public PhotoStats() {
  }

  public PhotoStats(int downloads, int views, int likes, Links links) {
    this.downloads = downloads;
    this.views = views;
    this.likes = likes;
    this.links = links;
  }

  public Integer getDownloads() {
    return downloads;
  }

  public void setDownloads(int downloads) {
    this.downloads = downloads;
  }

  public Integer getViews() {
    return views;
  }

  public void setViews(int views) {
    this.views = views;
  }

  public Integer getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public Links getLinks() {
    return links;
  }

  public void setLinks(Links links) {
    this.links = links;
  }

  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(downloads);
    dest.writeValue(views);
    dest.writeValue(likes);
    dest.writeValue(links);
  }

  public int describeContents() {
    return 0;
  }
}
