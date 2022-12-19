package com.example.pert10_sharedpreferences;

import static com.example.pert10_sharedpreferences.DBmain.TABLENAME;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.pert10_sharedpreferences.databinding.ActivityMain5Binding;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;

public class MainActivity5 extends AppCompatActivity {
    private ActivityMain5Binding binding;
    com.example.pert10_sharedpreferences.DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    int id = 0;
    public static final int MY_CAMERA_REQUEST_CODE = 100;
    public static final int MY_STORAGE_REQUEST_CODE = 101;
    String cameraPermission[];
    String storagePermission[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMain5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dBmain = new com.example.pert10_sharedpreferences.DBmain(this);
//findid();
        insertData();
        editData();
        binding.edtimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int avatar = 0;
                if (avatar == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        pickFromGallery();
                    }
                } else if (avatar == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        pickFromGallery();
                    }
                }
            }
        });
    }
    private void editData() {
        if (getIntent().getBundleExtra("userdata")!= null){Bundle bundle = getIntent().getBundleExtra("userdata");
            id = bundle.getInt("id");
//for set name
            binding.edtjudullagu.setText(bundle.getString("judul_lagu"));
            binding.edtartis.setText(bundle.getString("artis"));
            binding.edttahun.setText(bundle.getString("tahun"));
//for image
            byte[]bytes = bundle.getByteArray("avatar");
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            binding.edtimage.setImageBitmap(bitmap);
//visible edit button and hide submit button
            binding.btnSubmit.setVisibility(View.GONE);
            binding.btnEdit.setVisibility(View.VISIBLE);
        }
    }
    private void requestStoragePermission() {
        requestPermissions(storagePermission, MY_STORAGE_REQUEST_CODE);
    }
    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }
    private void pickFromGallery() {
        CropImage.activity().start(this);
    }
    private void requestCameraPermission() {
        requestPermissions(cameraPermission, MY_CAMERA_REQUEST_CODE);
    }
    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }
    private void insertData() {
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("judul_lagu", binding.edtjudullagu.getText().toString());
                cv.put("artis", binding.edtartis.getText().toString());
                cv.put("avatar", imageViewToBy(binding.edtimage));
                cv.put("tahun", binding.edttahun.getText().toString());
                sqLiteDatabase = dBmain.getWritableDatabase();
                Long rec = sqLiteDatabase.insert("daftar_lagu", null, cv);
                if (rec != null) {
                    Toast.makeText(MainActivity5.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    binding.edtjudullagu.setText("");
                    binding.edtimage.setImageResource(R.drawable.ic_baseline_library_music_24);
                    binding.edtartis.setText("");
                    binding.edttahun.setText("");
                } else {
                    Toast.makeText(MainActivity5.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
//for view display
        binding.btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity5.this, DisplayData.class));
            }
        });
//for storing new data or update data
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("judul_lagu", binding.edtjudullagu.getText().toString());
                cv.put("artis", binding.edtartis.getText().toString());
                cv.put("tahun", binding.edttahun.getText().toString());
                cv.put("avatar", imageViewToBy(binding.edtimage));
                sqLiteDatabase = dBmain.getWritableDatabase();
                long recedit = sqLiteDatabase.update(TABLENAME, cv, "id=" + id, null);
                if (recedit != -1) {
                    Toast.makeText(MainActivity5.this, "Update Succesfully", Toast.LENGTH_SHORT).show();
//clear data adfte submit
                    binding.edtjudullagu.setText("");
                    binding.edtartis.setText("");
                    binding.edttahun.setText("");
                    binding.edtimage.setImageResource(R.drawable.ic_baseline_library_music_24);
//edit hide and submit visible
                    binding.btnEdit.setVisibility(View.GONE);
                    binding.btnSubmit.setVisibility(View.VISIBLE);
                    Intent a = new Intent(MainActivity5.this, DisplayData.class);
                    startActivity(a);
                }
            }
        });
    }
    public static byte[] imageViewToBy(ImageView avatar) {
        Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean camera_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storage_accepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (camera_accepted && storage_accepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "enable camera and storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }break;
            case MY_STORAGE_REQUEST_CODE: {
                boolean storage_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (storage_accepted) {
                    pickFromGallery();
                } else {
                    Toast.makeText(this, "please enable storage permission", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int
            resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                Picasso.with(this).load(resultUri).into(binding.edtimage);
            }
        }
    }}
