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

public class RulesItemList extends AppCompatActivity {
    String school_name;
    String cohort_name;
    int cohort_id;
    String school_level;
    private boolean mTwoPane;
    //String baselink = "http://mydoa.net/akad/diplayschool.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_item_list);
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

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(ITEMS));
    }
    private class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<SchoolItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<SchoolItem> items) {
            //mValues.clear();
            String rulesUrl = "";
            if(school_level.equalsIgnoreCase("diploma"))
                rulesUrl = Configs.web_base_webbook_dep;
            else
                rulesUrl = Configs.web_base_webbook;
            mValues = items;
            int icon = R.drawable.btn_tech;
            mValues.clear();
            if(school_level.equalsIgnoreCase("diploma")){//DIPLOMA TITLES
            icon = R.drawable.intro;
            SchoolItem di = new SchoolItem("SISTEM AKADEMIK", rulesUrl+"academicsystem.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.prgorammestructure;
            di = new SchoolItem("STRUKTUR KURIKULUM", rulesUrl+"programmestructure.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.typecourse;
            di = new SchoolItem("JENIS-JENIS KURSUS", rulesUrl+"typeofcources.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.unitsystem;
            di = new SchoolItem("PENDEKATAN PEMBELAJARAN DAN PENGAJARAN", rulesUrl+"theunitsystem.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.incubator;
            di = new SchoolItem("TAKRIFAN KURSUS", rulesUrl+"takrifkursus.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.industrialtraning;
            di = new SchoolItem("LATIHAN INDUSTRI", rulesUrl+"industrialtraning.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.coursecode;
            di = new SchoolItem("KOD KURSUS", rulesUrl+"coursecode.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.registration;
            di = new SchoolItem("PENDAFTARAN KURSUS", rulesUrl+"courseregistration.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.adddrop;
            di = new SchoolItem("PENAMBAHAN / PENGGUGURAN / TARIK DIRI KURSUS", rulesUrl+"addcourses.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.changeprogramme;
            di = new SchoolItem("PERTUKARAN PROGRAM PENGAJIAN", rulesUrl+"changeofprogramme.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.postponement;
            di = new SchoolItem("PENANGGUHAN PENGAJIAN", rulesUrl+"postponementofstudy.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.grading;
            di = new SchoolItem("PENARAFAN PELAJAR", rulesUrl+"studentgrading.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.minmax;
            di = new SchoolItem("TEMPOH MINIMUM / MAKSIMUM PENGAJIAN PELAJAR", rulesUrl+"periodofstudy.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.curative;
            di = new SchoolItem("KURSUS KURATIF", rulesUrl+"curativecourses.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.examevaluation;
            di = new SchoolItem("SISTEM PEPERIKSAAN DAN PENILAIAN", rulesUrl+"examinationsystem.php", "introduction",icon);
            mValues.add(di);

            icon = R.drawable.apeal;
            di = new SchoolItem("RAYUAN PENYEMAKAN SEMULA KEPUTUSAN PEPERIKSAAN", rulesUrl+"appeal.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.englishlanguage;
            di = new SchoolItem("PENGGUNAAN BAHASA INGGERIS", rulesUrl+"englishlanguage.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.buddysystem;
            di = new SchoolItem("SISTEM RAKAN PENDAMPING SISWA (RPS)", rulesUrl+"buddysystem.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.supportcenter;
            di = new SchoolItem("PUSAT-PUSAT PEMANTAPAN AKADEMIK", rulesUrl+"academicsupport.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            icon = R.drawable.registratdepartment;
            di = new SchoolItem("BAHAGIAN PENGURUSAN AKADEMIK, JABATAN PENDAFTAR", rulesUrl+"academicmanagement.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);

            }else{//DEGREE TITLES
                icon = R.drawable.intro;
            SchoolItem di = new SchoolItem("THE ACADEMIC SYSTEM", rulesUrl+"academicsystem.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.prgorammestructure;
            di = new SchoolItem("PROGRAMME STRUCTURE", rulesUrl+"programmestructure.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.typecourse;
            di = new SchoolItem("TYPES OF COURSES", rulesUrl+"typeofcources.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.unitsystem;
            di = new SchoolItem("THE UNIT SYSTEM", rulesUrl+"theunitsystem.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.teachinglearning;
            di = new SchoolItem("TEACHING AND LEARNING", rulesUrl+"teachingandlearning.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.industrialtraning;
            di = new SchoolItem("INDUSTRIAL TRAINING", rulesUrl+"industrialtraning.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.incubator;
            di = new SchoolItem("BUSINESS INCUBATOR PROGRAMME", rulesUrl+"businessincubator.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.ibfieldtrip;
            di = new SchoolItem("INTERNATIONAL BUSINESS FIELD TRIP", rulesUrl+"internationalbusiness.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            //icon = R.drawable.ibprogrammetrip;//change to IB Programme trip
            //di = new SchoolItem("INTERNATIONAL BUSINESS PROGRAMME TRIP",web_base_webbook+"internationalbusiness.php", "introduction",icon);
           // mValues.add(di);
            icon = R.drawable.coursecode;
            di = new SchoolItem("COURSE CODE", rulesUrl+"coursecode.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.preregistration;
            di = new SchoolItem("PRE-REGISTRATION", rulesUrl+"preregistration.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.registration;
            di = new SchoolItem("COURSE REGISTRATION", rulesUrl+"courseregistration.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.adddrop;
            di = new SchoolItem("ADD/DROP/WITHDRAWAL COURSES", rulesUrl+"addcourses.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.changeprogramme;
            di = new SchoolItem("CHANGE OF PROGRAMME", rulesUrl+"changeofprogramme.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.postponement;
            di = new SchoolItem("POSTPONEMENT OF STUDY", rulesUrl+"postponementofstudy.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.grading;
            di = new SchoolItem("STUDENT GRADING", rulesUrl+"studentgrading.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.minmax;
            di = new SchoolItem("MINIMUM AND MAXIMUM PERIOD OF STUDY", rulesUrl+"periodofstudy.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.curative;
            di = new SchoolItem("CURATIVE COURSES", rulesUrl+"curativecourses.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.exemption;
            di = new SchoolItem("CREDIT EXEMPTION", rulesUrl+"creditexemption.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.examevaluation;
            di = new SchoolItem("EXAMINATION AND EVALUATION SYSTEM", rulesUrl+"examinationsystem.php", "introduction",icon);
            mValues.add(di);
            icon = R.drawable.apeal;
            di = new SchoolItem("APPEAL FOR EXAMINATION RESULTS REVISION", rulesUrl+"appeal.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.englishlanguage;
            di = new SchoolItem("ENGLISH LANGUAGE USE", rulesUrl+"englishlanguage.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.buddysystem;
            di = new SchoolItem("BUDDY SYSTEM (RAKAN PENDAMPING SISWA)", rulesUrl+"buddysystem.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.supportcenter;
            di = new SchoolItem("ACADEMIC SUPPORT CENTRES", rulesUrl+"academicsupport.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
            icon = R.drawable.registratdepartment;
            di = new SchoolItem("ACADEMIC MANAGEMENT DIVISION, REGISTRAR’S DEPARTMENT", rulesUrl+"academicmanagement.php?cohort_id="+cohort_id, "introduction",icon);
            mValues.add(di);
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
            }

            @Override
            public String toString() {
                return super.toString() + " '" + "'";
            }
        }
    }
}
