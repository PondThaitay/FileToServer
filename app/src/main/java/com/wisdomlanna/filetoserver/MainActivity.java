package com.wisdomlanna.filetoserver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;


public class MainActivity extends Activity {
    AlertDialog alertDialog = new AlertDialog();

    private int counter;
    private static final int REQUEST_CODE = 1;

    private ProgressDialog progressDialog;

    private Context context = this;
    private ArrayList<String> photos;
    private Bitmap imageSelected;
    private File imageFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // btnUpload
        Button btnUpload = (Button) findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage = data.getData();
        InputStream imageStream = null;
        try {
            imageStream = getContentResolver().openInputStream(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Uri selectedImageURI = data.getData();
        //imageSelected = BitmapFactory.decodeStream(imageStream);
        imageFile = new File(getRealPathFromURI(selectedImageURI));
        String path = getRealPathFromURI(selectedImageURI);
        upToServer(path);
        Log.i("POND", "path : " + getRealPathFromURI(selectedImageURI) + "");
    }

    private void upToServer(String imPath) {
        TypedFile typedFile = new TypedFile("image/jpeg", new File(imPath));
        TypedString typedString = new TypedString("1");
        Retrofit.getUpLoadFile().upLoad(typedFile, typedString, new Callback<Retrofit.UpLoadResult>() {
            @Override
            public void success(Retrofit.UpLoadResult upLoadResult, Response response) {
                if (upLoadResult.success) {
                    Toast.makeText(context, "OK...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "NO...", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(context, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}