package com.elshaikh.mano.acadunimap;

import android.content.Intent;
import android.net.Uri;
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
import java.sql.Time;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class TimeTables extends AppCompatActivity {
    ArrayList<Link> links = new ArrayList<Link>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_tables);
        Button degree = (Button) findViewById(R.id.degree);
        Button diploma = (Button) findViewById(R.id.diploma);
        readLinks();
        degree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimeTables.this, Webing.class);
                intent.putExtra("runningurl",getLink("ttdegree"));
                startActivity(intent);

            }
        });
        diploma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimeTables.this, Webing.class);
                intent.putExtra("runningurl",getLink("ttdiploma"));
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
                System.out.println("OOOOOOOOOOOOOOOOOO Name="+l.name+" || LINK: "+l.link);
            }

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
