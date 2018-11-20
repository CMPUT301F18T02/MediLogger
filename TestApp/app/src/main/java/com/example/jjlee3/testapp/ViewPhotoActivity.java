package com.example.jjlee3.testapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.FileNotFoundException;

public class ViewPhotoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);

        ImageView img;
        Uri photo = (Uri) getIntent().getParcelableExtra("sampleObject");
        img = (ImageView)findViewById(R.id.ENLARGEDIMAGE);

        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(photo));
            img.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //Set image as an error image
            img.setImageBitmap(null);
        }

    }
}
