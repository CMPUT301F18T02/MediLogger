package com.example.symptologger;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.concurrent.ExecutionException;

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

public class AddPatientActivity extends AppCompatActivity {

    String patientUName;
    String cpUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        Intent intent = getIntent();
        cpUserName = intent.getStringExtra("cpUserName");

        FloatingActionButton savePatientFAB = findViewById(R.id.savePatientFAB);
        savePatientFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText patientUserName = (EditText) findViewById(R.id.patientUserName);
                patientUName = patientUserName.getText().toString();


                if (patientUName.equals("")) {
                    Toast.makeText(AddPatientActivity.this, "Nothing entered. Please enter a valid username.", Toast.LENGTH_SHORT).show();
                }

                EditText shareCodeText = (EditText) findViewById(R.id.enterShareCodeText);
                String shareCode = shareCodeText.getText().toString();

                Boolean val = Boolean.FALSE;

                ElasticSearchClient.SearchUser searchUser = new ElasticSearchClient.SearchUser();
                searchUser.execute(patientUName);

                try {
                    val = searchUser.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!val){
                    Toast.makeText(AddPatientActivity.this,"Username does not exist. Please enter valid username.",Toast.LENGTH_SHORT).show();
                } else {
                    if (shareCode.equals("")){
                        Toast.makeText(AddPatientActivity.this,"Please enter the share code.",Toast.LENGTH_SHORT).show();
                    } else {
                        if (getShareCode().equals("")){
                            Toast.makeText(AddPatientActivity.this,"The share code was not found. There may be an issue with the server.",Toast.LENGTH_SHORT).show();
                        } else {
                            ElasticSearchClient.GetSinglePatient getSinglePatient = new ElasticSearchClient.GetSinglePatient();
                            getSinglePatient.execute(patientUName);
                            Patient patient = null;
                            try {
                                patient = getSinglePatient.get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            String pEmail = patient.getEmail();
                            String pCell = patient.getCell();

                            ElasticSearchClient.DeletePatient deletePatient = new ElasticSearchClient.DeletePatient();
                            deletePatient.execute(patientUName);

                            ElasticSearchClient.AddPatient addPatient = new ElasticSearchClient.AddPatient();
                            addPatient.execute(patientUName,pEmail,pCell,cpUserName, new Date().toString());
                        }
                    }

                    Toast.makeText(AddPatientActivity.this,"Saving "+patientUName, Toast.LENGTH_SHORT).show();

                    Intent savePatient = new Intent(AddPatientActivity.this,CareProviderListPatientsActivity.class);
                    savePatient.putExtra("UNAME",patientUName);
                    startActivity(savePatient);
                }


            }
        });
    }

    public String getShareCode(){
        ElasticSearchClient.GetShareCode getShareCode = new ElasticSearchClient.GetShareCode();
        getShareCode.execute(patientUName);

        String code = "";
        try {
            code = getShareCode.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return code;
    }
}
