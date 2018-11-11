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

public class SchooItemList extends AppCompatActivity {
    private boolean mTwoPane;
    String school_name;
    String cohort_name;
    String cohort_id;
    //String baselink = "http://mydoa.net/akad/diplayschool.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoo_item_list);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        school_name = getScoolName();
        cohort_name = getCohortName();
        View recyclerView = findViewById(R.id.item_list);


        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        //if (findViewById(R.id.item_detail_container) != null) {
       //     mTwoPane = true;
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
                cohort_id = jsonObj.getString("id");
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

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(ITEMS));
    }
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<SchoolItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<SchoolItem> items) {
            //mValues.clear();
            mValues = items;
            mValues.clear();

            String jsonstring = readFromFile(AcademicBookOptions.SCHOOL_FILE);

            if (jsonstring != null) {
                try {
                    ImageView xx;
                    JSONObject jsonObj = new JSONObject(jsonstring);
                    if(jsonObj.getString("intro").equals("yes")) {
                        int icon = R.drawable.btn_introduction;
                        SchoolItem di = new SchoolItem("INTRODUCTION", Configs.web_base_webbook+"s_interoduction.php?apps=1&cohort_id="+cohort_id+"&school_id="+getScoolId(), "introduction",icon);
                        mValues.add(di);
                    }
                    if(jsonObj.getString("exco").equals("yes")) {
                        int icon = R.drawable.btn_exco;
                        SchoolItem di = new SchoolItem("EXCO MEMBERS", Configs.web_base_webbook+"s_exco.php?apps=1&cohort_id="+cohort_id+"&school_id="+getScoolId(), "exco",icon);
                        mValues.add(di);
                    }
                    if(jsonObj.getString("lecturer").equals("yes")) {
                        int icon = R.drawable.btn_lecturer;
                        SchoolItem di = new SchoolItem("ACADEMIC STAFF", Configs.web_base_webbook+"s_lecturer.php?apps=1&cohort_id="+cohort_id+"&school_id="+getScoolId(), "acdemic",icon);
                        mValues.add(di);
                    }
                    if(jsonObj.getString("study_leave").equals("yes")) {
                        int icon = R.drawable.btn_sleave;
                        SchoolItem di = new SchoolItem("STUDY LEAVE", Configs.web_base_webbook+"study_leave.php?apps=1&cohort_id="+cohort_id+"&school_id="+getScoolId(), "sleave",icon);
                        mValues.add(di);
                    }
                    if(jsonObj.getString("slab").equals("yes")) {
                        int icon = R.drawable.btn_slab;
                        SchoolItem di = new SchoolItem("SKIM LATIHAN TENAGA PENGAJAR AKADEMIK (SLAB)", Configs.web_base_webbook+"slab.php?apps=1&cohort_id="+cohort_id+"&school_id="+getScoolId(), "slab",icon);
                        mValues.add(di);
                    }
                    if(jsonObj.getString("plv").equals("yes")) {
                        int icon = R.drawable.btn_plv;
                        SchoolItem di = new SchoolItem("VOCATIONAL TRAINING OFFICER", Configs.web_base_webbook+"plv.php?apps=1&cohort_id="+cohort_id+"&school_id="+getScoolId(), "plv",icon);
                        mValues.add(di);
                    }
                    if(jsonObj.getString("tech").equals("yes")) {
                        int icon = R.drawable.btn_tech;
                        SchoolItem di = new SchoolItem("ASSISTANT ENGINEERS", Configs.web_base_webbook+"tech.php?apps=1&cohort_id="+cohort_id+"&school_id="+getScoolId(), "tech",icon);
                        mValues.add(di);
                    }
                    if(jsonObj.getString("adminstrative").equals("yes")) {
                        int icon = R.drawable.btn_admin;
                        SchoolItem di = new SchoolItem("ADMINISTRATIVE STAFF", Configs.web_base_webbook+"adminstrative.php?apps=1&cohort_id="+cohort_id+"&school_id="+getScoolId(), "admins",icon);
                        mValues.add(di);
                    }
                    if(jsonObj.getString("subject").equals("yes")) {
                        int icon = R.drawable.btn_subjects;
                        SchoolItem di = new SchoolItem("LIST OF COURSES", Configs.web_base_webbook+"subject.php?apps=1&cohort_id="+cohort_id+"&school_id="+getScoolId(), "subjects",icon);
                        mValues.add(di);
                    }
                    if(jsonObj.getString("programmes").equals("yes")) {
                        int icon = R.drawable.btn_programme;
                        SchoolItem di = new SchoolItem("PROGRAMMES", Configs.web_base_webbook+"programmes.php?apps=1&cohort_id="+cohort_id+"&school_id="+getScoolId(), "progs",icon);
                        mValues.add(di);
                    }

                }  catch (final JSONException e) {
                    Toast.makeText(getApplicationContext(),"Json Parsing2",Toast.LENGTH_LONG).show();


                }

            } else {
                Toast.makeText(getApplicationContext(),"Json object Null",Toast.LENGTH_LONG).show();

            }

        }

        @Override
        public SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mTitleView.setText(mValues.get(position).name);
            holder.imgIcon.setImageResource(mValues.get(position).icon_id);
            //holder.mDetaileView.setText(mValues.get(position).tag);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        //String u = baselink+"?cohort_id="+getCohortId()+"&school_id="+getScoolId()+"#"+holder.mItem.tag;
                       // arguments.putString("runningurl",u);
                        //ItemDetailFragment fragment = new ItemDetailFragment();
                        //Fwebing fragment = new Fwebing();
                        //fragment.setArguments(arguments);
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

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mTitleView;
            public ImageView imgIcon;
            public SchoolItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTitleView = (TextView) view.findViewById(R.id.txtTitle);
                imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
                //mDetaileView = (TextView) view.findViewById(R.id.infos);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + "'";
            }
        }
    }
}
