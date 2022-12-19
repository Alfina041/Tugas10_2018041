package com.example.pert10_sharedpreferences;

import static com.example.pert10_sharedpreferences.DBmain.TABLENAME;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pert10_sharedpreferences.databinding.ActivityDisplayDataBinding;

import java.util.ArrayList;

public class DisplayData extends AppCompatActivity {
    com.example.pert10_sharedpreferences.DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    com.example.pert10_sharedpreferences.MyAdapter myAdapter;
    private ActivityDisplayDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        findId();
        dBmain = new com.example.pert10_sharedpreferences.DBmain(this);
        displayData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(DisplayData.this, com.example.pert10_sharedpreferences.MainActivity5.class);
                startActivity(a);
            }
        });
    }

    private void displayData() {
        sqLiteDatabase = dBmain.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLENAME,null);
        ArrayList <com.example.pert10_sharedpreferences.Model> models = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String judul_lagu = cursor.getString(1);
            byte[] avatar = cursor.getBlob(4);
            String artis = cursor.getString(2);
            String tahun = cursor.getString(3);
            models.add(new com.example.pert10_sharedpreferences.Model(id, avatar, judul_lagu, artis, tahun));
        }
        cursor.close();
        myAdapter = new com.example.pert10_sharedpreferences.MyAdapter(this, R.layout.singledata, models, sqLiteDatabase);
        recyclerView.setAdapter(myAdapter);
    }

    private void findId() {
        recyclerView = findViewById(R.id.rv);
    }
}