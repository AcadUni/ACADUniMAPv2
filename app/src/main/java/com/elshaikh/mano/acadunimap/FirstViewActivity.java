package com.elshaikh.mano.acadunimap;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FirstViewActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private int[] layouts;
    public int slidenews,slidecover,sliderules,slideelearning,slideomr,slidetimetable,slidelocations,slidebus;
    public TextView PageTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstviewlayout);

        slidenews = R.layout.slidenews;
        slidecover = R.layout.slidecover;
        sliderules = R.layout.sliderules;
        slideelearning = R.layout.slideelearning;
        slidetimetable = R.layout.slidetimetable;
        slideomr = R.layout.slideomr;
        slidebus = R.layout.slidebus;
        PageTitle  = (TextView)  findViewById(R.id.page_title);

        slidelocations = R.layout.slidelocations;

        layouts = new int[]{
                slidenews,
                slidecover,
                sliderules,
                slideelearning,
                slideomr,
                slidebus,
                slidetimetable,
                slidelocations};
        doview();
    }
    public void doview()
    {
        viewPager = (ViewPager) findViewById(R.id.pages);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        private View doSlideNews(ViewGroup container, int z){
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[z], container, false);
            return v;
        }
        private View doSlideCover(ViewGroup container, int z){
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[z], container, false);
            return v;
        }
        private View doSlideRules(ViewGroup container, int z){
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[z], container, false);
            return v;
        }
        private View doSlideElearning(ViewGroup container, int z){
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[z], container, false);
            return v;
        }
        private View doSlideOmr(ViewGroup container, int z){
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[z], container, false);
            return v;
        }
        private View doSlideBus(ViewGroup container, int z){
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[z], container, false);
            return v;
        }
        private View doSlideTimetable(ViewGroup container, int z){
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[z], container, false);
            return v;
        }
        private View doSlideLocation(ViewGroup container, int z){
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[z], container, false);
            return v;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view;// = new View();// = layoutInflater.inflate(layouts[position], container, false);
            switch (position){
                case 0: view = doSlideNews(container,position); PageTitle.setText("News and Events");break;
                case 1: view = doSlideCover(container,position); PageTitle.setText("Book Cover");break;
                case 2: view = doSlideRules(container,position); PageTitle.setText("Academic System");break;
                case 3: view = doSlideElearning(container,position); PageTitle.setText("E-Learning");break;
                case 4: view = doSlideOmr(container,position); PageTitle.setText("Student Evaluation Report");break;
                case 5: view = doSlideBus(container,position); PageTitle.setText("Bus tracking");break;
                case 6: view = doSlideTimetable(container,position); PageTitle.setText("Time Table");break;
                case 7: view = doSlideLocation(container,position); PageTitle.setText("Location in UniMAP");break;
                default: view = doSlideNews(container,position);PageTitle.setText("News and Events");
            }
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
}
