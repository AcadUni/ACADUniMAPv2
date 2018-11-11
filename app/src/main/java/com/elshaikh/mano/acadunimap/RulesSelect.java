package com.elshaikh.mano.acadunimap;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class RulesSelect extends AppCompatActivity {
    public static final String SELECTED_FILE = "selected_file";
    ArrayList<ListItem> schools = new ArrayList<ListItem>();
    ArrayList<ListItem> cohorts = new ArrayList<ListItem>();
    String selectedSchool;
    String selectedCohort;
    String lang = "eng";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_select);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Button btn_cover = (Button) findViewById(R.id.cover);
        Button btn_akad_system = (Button) findViewById(R.id.system);

        btn_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(RulesSelect.this,
                        CoverBook.class);
                myIntent.putExtra("type", "cover");
                myIntent.putExtra("lang",lang);
                //putCohortFile(selectedCohort);
                //putSchoolFile(selectedSchool);
                startActivity(myIntent);

            }
        });
        btn_akad_system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(RulesSelect.this,
                        RulesItemList.class);
                myIntent.putExtra("type", "system");
                myIntent.putExtra("lang",lang);
                //putCohortFile(selectedCohort);
                //putSchoolFile(selectedSchool);
                startActivity(myIntent);

            }
        });
    }
}
