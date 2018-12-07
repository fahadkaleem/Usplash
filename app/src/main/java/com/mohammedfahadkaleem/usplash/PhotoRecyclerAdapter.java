package com.mohammedfahadkaleem.usplash;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.mohammedfahadkaleem.usplash.models.Photo;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.ViewHolder> {

  TextView username, likes;
  ImageView userProfilePicture;
  private List<Photo> photos;
  private OnItemClickListener mOnItemClickListener;

  public PhotoRecyclerAdapter() {
    photos = new ArrayList<>();
  }

  public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
    this.mOnItemClickListener = mItemClickListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.photo_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    if (holder instanceof ViewHolder) {
      ViewHolder viewHolder = holder;
      viewHolder.imageView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view, photos.get(position), position);
          }
        }
      });
    }

    Photo photo = photos.get(position);
    username.setText(photo.getUser().getUsername());
    //likes.setText(photo.getLikes().toString());
    Picasso.get().load(photo.getUser().getProfileImage().getLarge()).into(userProfilePicture);
    Picasso.get().load(photo.getUrls().getRegular()).into(holder.imageView);
  }

  public void setPhotos(List<Photo> photos) {
    this.photos = photos;
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    return photos.size();
  }

  public interface OnItemClickListener {

    void onItemClick(View view, Photo obj, int position);
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    public final ImageView imageView;

    public ViewHolder(View view) {
      super(view);
      imageView = view.findViewById(R.id.photo);
      username = view.findViewById(R.id.username);
      //likes = view.findViewById(R.id.likes);
      userProfilePicture = view.findViewById(R.id.user_profile_picture);
    }

  }
}
