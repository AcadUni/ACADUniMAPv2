package com.elshaikh.mano.acadunimap;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainOptions extends AppCompatActivity {
    ArrayList<Link> links = new ArrayList<Link>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_options);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        readLinks();
        Button acad = (Button) findViewById(R.id.acadbook);
        Button bus = (Button) findViewById(R.id.businfo);
        Button el = (Button) findViewById(R.id.elearning);
        Button tt = (Button) findViewById(R.id.tt);
        Button omr = (Button) findViewById(R.id.omr);
        Button locs = (Button) findViewById(R.id.locations);
        acad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainOptions.this,
                        AcademicBookOptions.class);
                myIntent.putExtra("type","staff");
                startActivity(myIntent);

            }
        });
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getLink("bustracking")));
                startActivity(browserIntent);
                //Intent myIntent = new Intent(MainOptions.this,
                 //       Webing.class);
                //myIntent.putExtra("runningurl","http://vehicle.ceastech.com/client");
                //startActivity(myIntent);

            }
        });
        el.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainOptions.this, Webing.class);
                intent.putExtra("runningurl",getLink("elearning"));
                startActivity(intent);

            }
        });
        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainOptions.this, TimeTables.class);
                intent.putExtra("runningurl",getLink("ttdegree"));
                startActivity(intent);

            }
        });
        omr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainOptions.this, Webing.class);
                intent.putExtra("runningurl",getLink("omr"));
                startActivity(intent);

            }
        });
        locs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fragment fragment = null;
                Intent intent = new Intent(MainOptions.this, Blank.class);
                intent.putExtra("runningurl",getLink("location"));
                startActivity(intent);
            }
        });
    }
    private String getLink(String name)
    {
        String lk = "www.unimap.edu.my";
        for(int i =0;i<links.size();i++)
            if(name.equalsIgnoreCase(links.get(i).name))
                return links.get(i).link;
        return lk;
    }
    private void readLinks()
    {
        String jstring = readFromFile(LoginActivity.LOGIN_FILE);
        try {
            JSONObject jsonObj = new JSONObject(jstring);
            JSONArray lks = jsonObj.getJSONArray("links");
            for(int i=0;i<lks.length();i++)
            {
                JSONObject oo = lks.getJSONObject(i);
                Link l = new Link();
                l.name = oo.getString("name");
                l.link = oo.getString("link");
                links.add(l);
            }
            System.out.println("OOOOOOOOOOOOOOOOOO SIZE="+links.size());
        } catch (JSONException e) {
            System.out.println("OOOOOOOOOOOOOOOOOO");
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
