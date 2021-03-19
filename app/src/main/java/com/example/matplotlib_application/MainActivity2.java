package com.example.matplotlib_application;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.io.File;

public class MainActivity2 extends AppCompatActivity {

    ImageView imageView;
    String image_path;
    int angle = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView= (ImageView) findViewById(R.id.iv);

        Bundle values = getIntent().getExtras();
        if (values != null) {
            image_path= values.getString("str");
        }
        File imgFile = new File(image_path);
        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
            /*imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    angle = angle + 90;
                    imageView.setRotation(angle);
                }
            });*/

        }
    }
}