package com.example.symptologger;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ModifyConcernActivity extends AppCompatActivity {

    private int pos;
    Collection<Concern> concerns;
    ArrayList<Concern> concernList;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_concern);

        Intent intent = getIntent();
        pos = intent.getIntExtra("pos",0);

        concerns = ConcernListController.getConcernList().getConcerns();
        concernList = new ArrayList<Concern>(concerns);

        FloatingActionButton modifyFAB = findViewById(R.id.modifyConcernFAB);
        modifyFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    modifyConcern();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        FloatingActionButton cancelFAB = findViewById(R.id.cancelFAB);
        cancelFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ModifyConcernActivity.this,"Cancel ...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ModifyConcernActivity.this, ViewConcernActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    public void modifyConcern() throws ParseException {
        Toast.makeText(this,"Modifying ...",Toast.LENGTH_SHORT).show();

        EditText newTitle = (EditText) findViewById(R.id.modifyConcernTitle);
        EditText newDescription = (EditText) findViewById(R.id.modifyConcernDesc);
        EditText newDate = (EditText) findViewById(R.id.modifyConcernDate);

        String title = newTitle.getText().toString();
        String description = newDescription.getText().toString();
        String date = newDate.getText().toString();

        if (!title.equals("")){
            try {
                concernList.get(pos).setTitle(title);
            } catch (TitleTooLongException e) {
                e.printStackTrace();
                Toast.makeText(this,"Title too long: 30 characters maximum",Toast.LENGTH_LONG).show();
            }
        }

        if (!description.equals("")){
            try {
                concernList.get(pos).setDescription(description);
            } catch (DescriptionTooLongException e){
                e.printStackTrace();
                Toast.makeText(this,"Description too long: 300 characters maximum",Toast.LENGTH_SHORT).show();
            }
        }

        if (!date.equals("")){
            Date blankDate = sdf.parse(date);
            concernList.get(pos).setDate(blankDate);
        }

        Intent intent = new Intent(ModifyConcernActivity.this,ViewConcernActivity.class);
        intent.putExtra("pos",pos);
        startActivity(intent);
    }

    public void addRecord(View view){
        Toast.makeText(this,"Add record ...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ModifyConcernActivity.this, NewRecordActivity.class);
        startActivity(intent);
    }

//    public void cancel(View view){
//        Toast.makeText(this,"Cancel ...", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(ModifyConcernActivity.this, ViewConcernActivity.class);
//        startActivity(intent);
//    }
}
