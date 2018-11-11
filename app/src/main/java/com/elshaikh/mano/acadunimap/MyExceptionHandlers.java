package com.elshaikh.mano.acadunimap;

/**
 * Created by Mano on 1/30/2018.
 */
import android.app.Activity;
import android.content.Intent;

/**
 * Created by Mano on 27/07/2017.
 */

public class MyExceptionHandlers implements Thread.UncaughtExceptionHandler {

    private Activity activity;

    public MyExceptionHandlers(Activity a) {
        activity = a;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Intent intent = new Intent(activity, FirstActivity.class);
        intent.putExtra("EROR","INTERNET PROBLEM");
        activity.startActivity(intent);
        activity.finish();
        System.exit(2);
    }
}
