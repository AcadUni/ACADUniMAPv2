package com.elshaikh.mano.acadunimap;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class CoverBook extends AppCompatActivity {
    String school_name;
    String cohort_name;
    String school_level;
    int cohort_id;
    private boolean mTwoPane;
    //String baselink = "http://mydoa.net/akad/diplayschool.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_book);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        school_name = getScoolName();
        cohort_name = getCohortName();
        setTitle(school_name);

        View recyclerView = findViewById(R.id.item_list);


        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        //if (findViewById(R.id.item_detail_container) != null) {
        //    mTwoPane = true;
        // }

    }
    private String readFromFile(String filename) {

        String ret = "";

        try {
            InputStream inputStream = this.openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        }

        return ret;
    }
    private String getScoolName()
    {
        String jsonstring = readFromFile(AcademicBookOptions.SCHOOL_FILE);

        if (jsonstring != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonstring);
                school_level = jsonObj.getString("level");
                return jsonObj.getString("name_e");

            } catch (final JSONException e) {
                Toast.makeText(getApplicationContext(),"Json Parsing School Name",Toast.LENGTH_LONG).show();


            }

        } else {
            Toast.makeText(getApplicationContext(),"Json object Null",Toast.LENGTH_LONG).show();

        }
        return null;
    }
    private String getCohortName()
    {
        String jsonstring = readFromFile(AcademicBookOptions.COHORT_FILE);

        if (jsonstring != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonstring);
                cohort_id = Integer.parseInt(jsonObj.getString("id"));
                return jsonObj.getString("name");
            } catch (final JSONException e) {
                Toast.makeText(getApplicationContext(),"Json Parsing Cohort Name",Toast.LENGTH_LONG).show();


            }

        } else {
            Toast.makeText(getApplicationContext(),"Json object Null",Toast.LENGTH_LONG).show();

        }
        return null;
    }
    private String getScoolId()
    {
        String jsonstring = readFromFile(AcademicBookOptions.SCHOOL_FILE);

        if (jsonstring != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonstring);
                return jsonObj.getString("id");
            } catch (final JSONException e) {
                Toast.makeText(getApplicationContext(),"Json Parsing Schhol ID",Toast.LENGTH_LONG).show();


            }

        } else {
            Toast.makeText(getApplicationContext(),"Json object Null",Toast.LENGTH_LONG).show();

        }
        return null;
    }
    private String getCohortId()
    {
        String jsonstring = readFromFile(AcademicBookOptions.COHORT_FILE);

        if (jsonstring != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonstring);
                return jsonObj.getString("id");
            } catch (final JSONException e) {
                Toast.makeText(getApplicationContext(),"Json Parsing Cohort Id",Toast.LENGTH_LONG).show();


            }

        } else {
            Toast.makeText(getApplicationContext(),"Json object Null",Toast.LENGTH_LONG).show();

        }
        return null;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        //ListItemAdapter ad = new ListItemAdapter(getApplicationContext(),R.layout.promo_row,data);
        List<SchoolItem> ITEMS = new ArrayList<SchoolItem>();

        recyclerView.setAdapter(new CoverBook.SimpleItemRecyclerViewAdapter(ITEMS));
    }
    private class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder2> {

        private final List<SchoolItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<SchoolItem> items) {
            //mValues.clear();
            int icon = R.drawable.btn_tech;
            mValues = items;
            mValues.clear();
           if(school_level.equalsIgnoreCase("diploma")){//DIPLOMA TITLES
            icon = R.drawable.bookcover;
            SchoolItem di = new SchoolItem("MUKA HADAPAN", Configs.web_base_webbook_dep+"cover.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.content;
            di = new SchoolItem("ISI KANDUNGAN", Configs.web_base_webbook_dep+"content.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.intro;
            di = new SchoolItem("PENDAHULUAN", Configs.web_base_webbook_dep+"introduction.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            di = new SchoolItem("LATARBELAKANG UniMAP", Configs.web_base_webbook_dep+"latarbelakang.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            di = new SchoolItem("OBJEKTIF PENDIDIKAN PROGRAM (PEO)", Configs.web_base_webbook_dep+"peo.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            di = new SchoolItem("HASIL PROGRAM(PO)", Configs.web_base_webbook_dep+"po.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.calendar;
            di = new SchoolItem("KALENDAR AKADEMIK", Configs.web_base_webbook_dep+"calendar.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            di = new SchoolItem("SYARAT KEMASUKAN", Configs.web_base_webbook_dep+"syarat.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            }else///DEGREE TITLES
                {
            icon = R.drawable.bookcover;
            SchoolItem di = new SchoolItem("MUKA HADAPAN/BOOK COVER", Configs.web_base_webbook+"cover.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.content;
            di = new SchoolItem("ISI KANDUNGAN/CONTENTS", Configs.web_base_webbook+"content.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.intro;
            di = new SchoolItem("PENGENALAN/ INTRODUCTION", Configs.web_base_webbook+"introduction.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.calendar;
            di = new SchoolItem("KALENDAR AKADEMIK/ACADEMIC CALENDAR", Configs.web_base_webbook+"calendar.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
           }



        }

        @Override
        public SimpleItemRecyclerViewAdapter.ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new SimpleItemRecyclerViewAdapter.ViewHolder2(view);
        }


        @Override
        public void onBindViewHolder(final SimpleItemRecyclerViewAdapter.ViewHolder2 holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mTitleView.setText(mValues.get(position).name);
            holder.imgIcon.setImageResource(mValues.get(position).icon_id);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        //String u = baselink+"?cohort_id="+getCohortId()+"&school_id="+getScoolId()+"#"+holder.mItem.tag;
                        //arguments.putString("runningurl",u);
                        //ItemDetailFragment fragment = new ItemDetailFragment();
                        // Fwebing fragment = new Fwebing();
                        // fragment.setArguments(arguments);
                        // getSupportFragmentManager().beginTransaction()
                        //        .replace(R.id.item_detail_container, fragment)
                        //       .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, Webing.class);
                        String u = holder.mItem.link;
                        intent.putExtra("runningurl",u);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder2 extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mTitleView;
            public ImageView imgIcon;
            public SchoolItem mItem;

            public ViewHolder2(View view) {
                super(view);
                mView = view;
                mTitleView = (TextView) view.findViewById(R.id.txtTitle);
                imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + "'";
            }
        }
    }

}
