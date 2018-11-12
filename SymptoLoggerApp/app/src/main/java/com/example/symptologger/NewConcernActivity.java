package com.example.symptologger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewConcernActivity extends AppCompatActivity {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_concern);
    }

    public void saveConcern(View view) throws ParseException {

        ConcernListController clc = new ConcernListController();

        Concern newConcern; //New concern variable declared ...
        Date blankDate;

        Toast.makeText(this,"Saving ...", Toast.LENGTH_SHORT).show();

        EditText editTitle = (EditText) findViewById(R.id.concernTitle);
        EditText editDescription = (EditText) findViewById(R.id.concernDescription);
        EditText editDate = (EditText) findViewById(R.id.concernDate);

        String concernTitle = editTitle.getText().toString();
        String concernDescription = editDescription.getText().toString();
        String concernDate = editDate.getText().toString();

        /*
        That EditText.getText() does not return null, but empty string ...
        https://stackoverflow.com/questions/19590472/does-edittext-gettext-ever-returns-null
        User: https://stackoverflow.com/users/603742/stinepike
        Date: 2018-11-11
        */

        if (concernDate.equals("")){
            blankDate = new Date();
        } else {
            blankDate = sdf.parse(concernDate);
        }

        newConcern = new Concern (concernTitle,blankDate,concernDescription);

        clc.addConcern(newConcern);

        Intent doneIntent = new Intent(NewConcernActivity.this, ListConcernActivity.class);
        startActivity(doneIntent);
    }

    public void cancel(View view){
        Toast.makeText(this,"Cancel ...", Toast.LENGTH_SHORT).show();
        Intent cancelIntent = new Intent(NewConcernActivity.this,ListConcernActivity.class);
        startActivity(cancelIntent);
    }
}
