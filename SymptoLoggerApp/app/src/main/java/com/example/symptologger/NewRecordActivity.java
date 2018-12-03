package com.example.symptologger;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

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
 * <p>
 *     Activity to create a new record.
 *     App user can set title, choose date and time, add geo location and add pictures for body parts
 * </p>
 */
public class NewRecordActivity extends AppCompatActivity {

    private static DateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd", Locale.CANADA);
    private static DateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.CANADA);
    private static DateFormat stringDateFormat = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss:SSS", Locale.CANADA);

    private int pos;
    private String userName;
    private int record_pos;
    private boolean modifying = false;
    Collection<Concern> concerns;
    ArrayList<Concern> concernList;
    Concern concernToModify;
    ArrayList<Record> recordList;
    Record recordToModify;

    Calendar c;

    int year;
    int month;
    int day;
    int hour;
    int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record_activity);

        Intent intent = getIntent();
        Bundle fromModconcern = intent.getExtras();
        pos = fromModconcern.getInt("CONCERN_POS",0);
        userName = fromModconcern.getString("USERNAME");

        concerns = ConcernListController.getConcernList(userName).getConcernsList();
        concernList = new ArrayList<Concern>(concerns);

        try {
            record_pos = fromModconcern.getInt("RECORD_POS");
            modifying = true;
            concernToModify = concernList.get(pos);
            recordList = new ArrayList<>(concernToModify.getRecords());
            recordToModify = recordList.get(record_pos);
        } catch (Exception e) {
            record_pos = 0;
        }

        final Button editDateButton = findViewById(R.id.dateField);
        final Button editTimeButton = findViewById(R.id.timeField);
        Button addLocationButton = findViewById(R.id.addLocationButton);
        Button addBodyPartsButton = findViewById(R.id.addBodyPartsButton);

        if (!modifying) {
            getCalendarInfo();
            Date now = c.getTime();
            editDateButton.setText(dateFormat.format(now));
            editTimeButton.setText(timeFormat.format(now));

        } else {
            try {
                TextView titleView = findViewById(R.id.recordTitleText);
                titleView.setText(recordToModify.getTitle());
                Date recordTime = stringDateFormat.parse(recordToModify.getDate());
                editDateButton.setText(dateFormat.format(recordTime));
                editTimeButton.setText(timeFormat.format(recordTime));
            } catch (Exception e) {
                getCalendarInfo();
                Date now = c.getTime();
                editDateButton.setText(dateFormat.format(now));
                editTimeButton.setText(timeFormat.format(now));
            }
        }


        editDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalendarInfo();

                final DatePickerDialog datePickerDialog = new DatePickerDialog(NewRecordActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        updateCalendarDate(year, month, dayOfMonth);
                        editDateButton.setText(dateFormat.format(c.getTime()));
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        editTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalendarInfo();

                final TimePickerDialog timePickerDialog = new TimePickerDialog(NewRecordActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        updateCalendarTime(hourOfDay, minute);
                        editTimeButton.setText(timeFormat.format(c.getTime()));
                    }
                }, hour, minute, false);

                timePickerDialog.show();
            }
        });

        addLocationButton.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         Intent intent;
                                                         PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                                                         try {
                                                             intent = builder.build(NewRecordActivity.this);
                                                             startActivityForResult(intent, 1);
                                                         } catch (GooglePlayServicesRepairableException e) {
                                                             e.printStackTrace();
                                                         } catch (GooglePlayServicesNotAvailableException e) {
                                                             e.printStackTrace();
                                                         }
                                                     }
        });

        addBodyPartsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bodyPartsIntent = new Intent(NewRecordActivity.this, PhotoRecordActivity.class);
                startActivity(bodyPartsIntent);
            }
        });

        FloatingActionButton newRecordFAB = findViewById(R.id.newRecordFAB);
        newRecordFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: change for offline mode
                // TODO: change for modifying mode
                Toast.makeText(NewRecordActivity.this,"Saving Record", Toast.LENGTH_SHORT).show();

                EditText recordTitle = (EditText) findViewById(R.id.recordTitleText);
                String title = recordTitle.getText().toString();

                Concern thisConcern = concernList.get(pos);
                Record newRecord = new Record(stringDateFormat.format(c.getTime()),title,userName,thisConcern.getTitle());
                thisConcern.addRecord(newRecord);

                Intent doneIntent = new Intent(NewRecordActivity.this, ViewConcernActivity.class);
                Bundle doneBundle = new Bundle();
                doneBundle.putInt("pos",pos);
                doneBundle.putString("userName",userName);
                doneIntent.putExtras(doneBundle);
                startActivity(doneIntent);
            }
        });

        FloatingActionButton cancelNewRecordFAB = findViewById(R.id.cancelNewRecordFAB);
        cancelNewRecordFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(NewRecordActivity.this, ViewConcernActivity.class);
                Bundle cancelBundle = new Bundle();
                cancelBundle.putInt("pos",pos);
                cancelBundle.putString("userName",userName);
                cancelIntent.putExtras(cancelBundle);
                startActivity(cancelIntent);
            }
        });
    }

    /**
     * Get calendar information and update global variables
     */
    private void getCalendarInfo() {
        c = Calendar.getInstance(Locale.CANADA);
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
    }

    /**
     * Update calendar information after user chooses a date
     * @param year
     * @param month
     * @param dayOfMonth
     */
    private void updateCalendarDate(int year, int month, int dayOfMonth) {
        c.set(year, month, dayOfMonth);
    }

    /**
     * Update time after user selects a time
     * @param hour
     * @param minute
     */
    private void updateCalendarTime(int hour, int minute) {
        c.set(year, month, day, hour, minute);
    }

    // called when location is selected
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int PLACE_PICKER_REQUEST = 1;
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                Button addLocationButton = findViewById(R.id.addLocationButton);
                addLocationButton.setText(place.getAddress());
            }
        }
    }

}