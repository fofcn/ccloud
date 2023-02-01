package com.github.ccloud.util;

import android.content.Context;

public class ContextHolder {

   private static Context context;

   public static void setContext(final Context context) {
       ContextHolder.context = context;
   }

   public static Context getContext() {
       return ContextHolder.context;
   }
}
