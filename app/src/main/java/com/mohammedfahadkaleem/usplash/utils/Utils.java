package com.mohammedfahadkaleem.usplash.utils;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.mohammedfahadkaleem.usplash.R;

/**
 * Created by fahadkaleem on 12/5/18.
 */
public class Utils {

  public static void setSystemBarLight(Activity act) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      View view = act.findViewById(android.R.id.content);
      int flags = view.getSystemUiVisibility();
      flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
      view.setSystemUiVisibility(flags);
    }
  }

  public static void setSystemBarColor(Activity act) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = act.getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimaryDark));
    }
  }

  public static void setSystemBarColor(Activity act, @ColorRes int color) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = act.getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.setStatusBarColor(act.getResources().getColor(color));
    }
  }

}
