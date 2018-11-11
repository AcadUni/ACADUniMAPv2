package com.elshaikh.mano.acadunimap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Blank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        Bundle args = new Bundle();
        String runningurl = (String)getIntent().getExtras().get("runningurl");
        Fragment fragment = new NewsChannel();
        args.putString("url",runningurl);
        args.putString("tit","Lokasi di UniMAP");
        fragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_fragment, fragment);
        ft.commit();
    }
}
