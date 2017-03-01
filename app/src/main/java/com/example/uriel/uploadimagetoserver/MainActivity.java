package com.example.uriel.uploadimagetoserver;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    ImageView photo;
    Button button;
    Bitmap photobmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=(Button)findViewById(R.id.btnUpload);
        photo=(ImageView)findViewById(R.id.photo);

        photo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Compile la accion usando. . ."), 1);
                    }
                }
        );

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] imageBytes = baos.toByteArray();
                        String encodeImage= Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        new MyAsincTask(MainActivity.this).execute(encodeImage);
                    }
                }
        );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode ==1 && resultCode == RESULT_OK){
            Uri selectedImageUri=data.getData();
            String aaa=getRealPathFromURI(selectedImageUri);
            photobmp= BitmapFactory.decodeFile(aaa);
            photo.setImageBitmap(photobmp);
        }
    }
    public String getRealPathFromURI(Uri contentUri){
        Cursor cursor = null;
        try{
            String[] proj= {MediaStore.Images.Media.DATA};
            cursor =getApplicationContext().getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor!= null){
                cursor.close();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
