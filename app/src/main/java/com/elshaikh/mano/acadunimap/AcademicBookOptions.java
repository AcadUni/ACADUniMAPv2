package com.elshaikh.mano.acadunimap;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AcademicBookOptions extends AppCompatActivity {
    ArrayList<ListItem> schools = new ArrayList<ListItem>();
    ArrayList<ListItem> cohorts = new ArrayList<ListItem>();
    public static final String SCHOOL_FILE = "school_file";
    public static final String COHORT_FILE = "cohort_file";

    int selectedSchool;
    int selectedCohort;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_book_options);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        putdata();
        final String[] sch = new String[schools.size()];// { "Chai Latte", "Green Tea", "Black Tea" };
        for(int i=0;i<schools.size();i++)
            sch[i] = schools.get(i).getTitle();
        final String[] coh = new String[cohorts.size()];// { "Chai Latte", "Green Tea", "Black Tea" };
        for(int i=0;i<cohorts.size();i++)
            coh[i] = cohorts.get(i).getTitle();
        ///school list
        //Spinner schoolSpinner = (Spinner) findViewById(R.id.school);
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        //        android.R.layout.simple_spinner_item, sch);
        //schoolSpinner.setAdapter(adapter);
        /*schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedSchool = getScoolid(sch[position]);
                //Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });*/
        ///cohorts list
        Spinner cohortsSpinner = (Spinner) findViewById(R.id.cohort);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, coh);
        cohortsSpinner.setAdapter(adapter2);
        cohortsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedCohort = getCohortid(coh[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putCohortFile(selectedCohort);
                UserLoginTask ut = new UserLoginTask();
                ut.execute();

            }
        });
    }
    private int getScoolid(String school)
    {
        for(int i=0;i<schools.size();i++)
            if(schools.get(i).getTitle().equals(school))
                return i;
        return -1;
    }
    private int getCohortid(String school)
    {
        for(int i=0;i<cohorts.size();i++)
            if(cohorts.get(i).getTitle().equals(school))
                return i;
        return -1;
    }
    private void putSchoolFile(int School_index)
    {
        String jsonstring = readFromFile(LoginActivity.LOGIN_FILE);

        if (jsonstring != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonstring);
                JSONArray contacts = jsonObj.getJSONArray("schools");
                JSONObject c = contacts.getJSONObject(School_index);
                writeToFile(SCHOOL_FILE,c.toString());

            } catch (final JSONException e) {
                Toast.makeText(getApplicationContext(),"Json Parsing2",Toast.LENGTH_LONG).show();


            }

        } else {
            Toast.makeText(getApplicationContext(),"Json object Null",Toast.LENGTH_LONG).show();
        }
    }
    private void putCohortFile(int Cohort_index)
    {
        String jsonstring = readFromFile(LoginActivity.LOGIN_FILE);

        if (jsonstring != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonstring);
                JSONArray contacts = jsonObj.getJSONArray("promos");
                JSONObject c = contacts.getJSONObject(Cohort_index);
                writeToFile(COHORT_FILE,c.toString());


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
    private void putdata()
    {
        String jsonstring = readFromFile(LoginActivity.LOGIN_FILE);

        if (jsonstring != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonstring);
                JSONArray contacts = jsonObj.getJSONArray("promos");
                for (int i = 0; i < contacts.length(); i++) {
                    ListItem pi = new ListItem();
                    JSONObject c = contacts.getJSONObject(i);
                    pi.setId(c.getString("id"));
                    pi.setTitle(c.getString("name"));
                    pi.setLink(c.getString("infos"));
                    cohorts.add(pi);
                }
                contacts = jsonObj.getJSONArray("schools");
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
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        String jsonStr = "";

        UserLoginTask() {
            jsonStr = "";
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.


            // Log.e(TAG, "Response from url: " + jsonStr);

            // do login returns JSONObject that contains loginresult and list of active cohorts
            try {
                String urlParameters  = "cohort_id="+cohorts.get(selectedCohort).getId();
                byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
                int    postDataLength = postData.length;
                URL url = new URL(Configs.web_base_api+"getschoolsbycohort.php");
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                conn.setDoOutput( true );
                conn.setInstanceFollowRedirects( false );
                conn.setRequestMethod( "POST" );
                conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty( "charset", "utf-8");
                conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
                conn.setUseCaches( false );
                try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                    wr.write( postData );
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    jsonStr = convertStreamToString(in);
                    System.out.println("GGGGGGGG:"+jsonStr+"\n LINK:"+url+"?cohort_id="+cohorts.get(selectedCohort).getId());
                    return true;
                }
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }

            // TODO: register the new account here.
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(!jsonStr.equals("")) {
                writeToFile(Configs.SCHOOLFILE, jsonStr);
                Intent it = new Intent(AcademicBookOptions.this,SelectSchool.class);
                startActivity(it);
            }else {

            }
        }


    }
}
