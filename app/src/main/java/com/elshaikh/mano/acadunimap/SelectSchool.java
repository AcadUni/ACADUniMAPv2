package com.elshaikh.mano.acadunimap;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SelectSchool extends AppCompatActivity {
    ArrayList<ListItem> schools = new ArrayList<ListItem>();
    public static final String SCHOOL_FILE = "school_file";
    String selectedSchool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_school);
        putdata();
        final String[] sch = new String[schools.size()];// { "Chai Latte", "Green Tea", "Black Tea" };
        for(int i=0;i<schools.size();i++)
            sch[i] = schools.get(i).getTitle();
        Spinner schoolSpinner = (Spinner) findViewById(R.id.school);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sch);
        schoolSpinner.setAdapter(adapter);
        schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedSchool = getScoolid(position);
                selectedSchool = schools.get(position).getId();
                //Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putSchoolFile(selectedSchool);
                Intent it = new Intent(SelectSchool.this,AcademicBookSections.class);
                startActivity(it);

            }
        });
    }
    private void writeToFile(String filename, String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }

    }
    private void putSchoolFile(String School_index)
    {
        String jsonstring = readFromFile(LoginActivity.LOGIN_FILE);

        if (jsonstring != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonstring);
                JSONArray contacts = jsonObj.getJSONArray("schools");
                System.out.println("IDDDD:"+School_index+"ZZZZNNNNNZZZZZZZ"+jsonstring);
                for(int i=0;i< contacts.length();i++)
                    if(contacts.getJSONObject(i).getString("id").equalsIgnoreCase(School_index)) {
                        JSONObject c = contacts.getJSONObject(i);
                        writeToFile(SCHOOL_FILE, c.toString());
                        System.out.println("ZZZZNNNNNZZZZZZZ"+c.toString());
                    }


            } catch (final JSONException e) {
                Toast.makeText(getApplicationContext(),"Json Parsing2",Toast.LENGTH_LONG).show();


            }

        } else {
            Toast.makeText(getApplicationContext(),"Json object Null",Toast.LENGTH_LONG).show();
        }
    }
    private String getScoolid(int school)
    {
        for(int i=0;i<schools.size();i++)
            if(schools.get(i).getTitle().equals(school))
                return schools.get(i).getId();
        return "-1";
    }
    private void putdata()
    {
        String jsonstring = readFromFile(Configs.SCHOOLFILE);
        System.out.println("KKKKKKKKKKKKK:"+jsonstring);

        if (jsonstring != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonstring);
                JSONArray contacts  = jsonObj.getJSONArray("schools");
                for (int i = 0; i < contacts.length(); i++) {
                    ListItem pi = new ListItem();
                    JSONObject c = contacts.getJSONObject(i);
                    pi.setId(c.getString("id"));
                    pi.setTitle(c.getString("name_e"));
                    pi.setLink("");
                    schools.add(pi);
                }

            } catch (final JSONException e) {
                Toast.makeText(getApplicationContext(),"Json Parsing2",Toast.LENGTH_LONG).show();


            }

        } else {
            Toast.makeText(getApplicationContext(),"Json object Null",Toast.LENGTH_LONG).show();
        }
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
}
