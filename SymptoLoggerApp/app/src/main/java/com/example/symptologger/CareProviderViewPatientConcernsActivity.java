package com.example.symptologger;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

/*
 *  Copyright 2018 Remi Arshad, Noni Hua, Jason Lee, Patrick Tamm, Kaiwen Zhang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at

 *     http://www.apache.org/licenses/LICENSE-2.0

 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
 * This is the activity that allows care providers to see the list of concerns that a particular
 * patient has. The functionality here is the same as the ListConcernsActivity for patients, however
 * some of the functionality (adding concerns, deleting concerns, etc.) has been removed.
 *
 * @author Patrick Tamm
 */
public class CareProviderViewPatientConcernsActivity extends AppCompatActivity {

    //https://developer.android.com/training/basics/firstapp/starting-activity#java
    //2018-11-12
//    public static final String EXTRA_MESSAGE = "com.example.symptologger.MESSAGE";

    ListView patientConcernsView;
    ArrayList<Concern> patientConcernList;
    ArrayAdapter<Concern> patientConcernListAdapter;
    Collection<Concern> patientConcerns;

    String pUserName;
    String cpName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider_view_patient_concerns);

        Intent fromPatientList = getIntent();
        Bundle extras = fromPatientList.getExtras();
        pUserName = extras.getString("pUserName");
        cpName = extras.getString("cpName");

        TextView userTextView = findViewById(R.id.patientUserNameTextView);
        userTextView.setText("Viewing "+pUserName);

        FloatingActionButton backToPatientListFAB = findViewById(R.id.backToPatientListFAB);
        backToPatientListFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CareProviderViewPatientConcernsActivity.this,"Back",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CareProviderViewPatientConcernsActivity.this,CareProviderListPatientsActivity.class);
                intent.putExtra("cpUserName",cpName);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        patientConcernsView = (ListView) findViewById(R.id.cpListConcernsView);
        patientConcerns = ConcernListController.getConcernList(pUserName).getConcernsList();
        patientConcernList = new ArrayList<Concern>(patientConcerns);
        patientConcernListAdapter = new ArrayAdapter<Concern>(this, android.R.layout.simple_list_item_1, patientConcernList);
        patientConcernsView.setAdapter(patientConcernListAdapter);


        patientConcernsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                final int pos = position;

                AlertDialog.Builder modifyAlert = new AlertDialog.Builder(CareProviderViewPatientConcernsActivity.this);

                modifyAlert.setTitle(patientConcernList.get(pos).getTitle());
                modifyAlert.setCancelable(true);

                CharSequence[] options = {"View",
                        "Cancel"};

                modifyAlert.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent viewIntent = new Intent(CareProviderViewPatientConcernsActivity.this, CareProviderViewConcernActivity.class);
                            Bundle viewBundle = new Bundle();
                            viewBundle.putInt("pos", pos);
                            viewBundle.putString("pUserName", pUserName);
                            viewIntent.putExtras(viewBundle);
                            startActivity(viewIntent);
                        } else {
                            Toast.makeText(CareProviderViewPatientConcernsActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                modifyAlert.show();
                return false;
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        patientConcerns = ConcernListController.getConcernList(pUserName).getConcernsList();
        patientConcernList = new ArrayList<Concern>(patientConcerns);
        patientConcernListAdapter.notifyDataSetChanged();
    }
}