package com.elshaikh.mano.acadunimap;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AcademicBookSections extends AppCompatActivity {
    public static final String SELECTED_FILE = "selected_file";
    ArrayList<ListItem> schools = new ArrayList<ListItem>();
    ArrayList<ListItem> cohorts = new ArrayList<ListItem>();
    String selectedSchool;
    String selectedCohort;
    String school_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_book_sections);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        selectedSchool = getScoolName();
        selectedCohort = getCohortName();
        Button btn_school = (Button) findViewById(R.id.school);
        Button btn_rules = (Button) findViewById(R.id.rules);
        btn_school.setText(getScoolName());
        btn_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AcademicBookSections.this,
                        SchooItemList.class);
                myIntent.putExtra("type","staff");
                startActivity(myIntent);

            }
        });
        btn_rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AcademicBookSections.this,
                        RulesSelect.class);
                myIntent.putExtra("type","staff");
                startActivity(myIntent);

            }
        });
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
                System.out.println("ZZZZZZZZZZZZZZZ"+jsonstring);
                school_level = jsonObj.getString("level");
                return jsonObj.getString("name_e");
            } catch (final JSONException e) {
                Toast.makeText(getApplicationContext(),"Json Parsing school name at SelecttoShow",Toast.LENGTH_LONG).show();


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
                return jsonObj.getString("name");
            } catch (final JSONException e) {
                Toast.makeText(getApplicationContext(),"Json Parsing Cohort Name at SelectToShow",Toast.LENGTH_LONG).show();


            }

        } else {
            Toast.makeText(getApplicationContext(),"Json object Null",Toast.LENGTH_LONG).show();

        }
        return null;
    }
}
