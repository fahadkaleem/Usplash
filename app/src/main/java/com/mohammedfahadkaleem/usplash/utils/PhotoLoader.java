package com.mohammedfahadkaleem.usplash.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by fahadkaleem on 12/6/18.
 */
public class PhotoLoader implements Target {

  private final String name;
  private ImageView imageView;

  public PhotoLoader(String name, ImageView imageView) {
    this.name = name;
    this.imageView = imageView;
  }

  @Override
  public void onPrepareLoad(Drawable arg0) {
  }

  @Override
  public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {
    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + name);
    try {
      file.createNewFile();
      FileOutputStream ostream = new FileOutputStream(file);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ostream);
      ostream.close();

      imageView.setImageBitmap(bitmap);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onBitmapFailed(Exception e, Drawable errorDrawable) {

  }
}
