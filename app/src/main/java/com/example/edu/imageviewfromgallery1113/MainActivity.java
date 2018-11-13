package com.example.edu.imageviewfromgallery1113;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int LOAD_IMAGE = 101; //구분자
    int IMAGE_CAPTURE = 102;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.fromGalleryButton)).setOnClickListener(this);
        ((Button)findViewById(R.id.imageCaptureButton)).setOnClickListener(this);
        imageView = findViewById(R.id.imageViewFromGallery);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.fromGalleryButton:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, LOAD_IMAGE);
                break;
            case R.id.imageCaptureButton:
                if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
                    Intent intent1 = new Intent();
                    intent1.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent1,IMAGE_CAPTURE);
                    break;
                }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(data!=null){
          if(requestCode == LOAD_IMAGE && resultCode == RESULT_OK){
              try {
                Uri selectedImage = data.getData();
                InputStream inputStream = this.getContentResolver().openInputStream(selectedImage);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap)extras.get("data");
            imageView.setImageBitmap(bitmap);
        }
    }
}
