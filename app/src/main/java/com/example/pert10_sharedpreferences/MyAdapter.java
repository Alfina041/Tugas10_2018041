package com.example.pert10_sharedpreferences;

import static com.example.pert10_sharedpreferences.DBmain.TABLENAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    int singledata;
    ArrayList<com.example.pert10_sharedpreferences.Model> modelArrayList;
    SQLiteDatabase sqLiteDatabase;

    //generate constructor
    public MyAdapter(Context context, int singledata, ArrayList<com.example.pert10_sharedpreferences.Model> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.singledata = singledata;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.singledata, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final com.example.pert10_sharedpreferences.Model model = modelArrayList.get(position);
        byte[] image = model.getProavatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.imageavatar.setImageBitmap(bitmap);
        holder.txtjudul_lagu.setText(model.getUserjudul_lagu());
        holder.txtartis.setText(model.getUserartis());
        holder.txttahun.setText(model.getUsertahun());
        //flow menu
        holder.flowmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.flowmenu);
                popupMenu.inflate(R.menu.flow_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_menu:
                                //edit operation
                                Bundle bundle = new Bundle();
                                bundle.putInt("id", model.getId());
                                bundle.putString("judul_lagu", model.getUserjudul_lagu());
                                bundle.putString("artis", model.getUserartis());
                                bundle.putString("tahun", model.getUsertahun());
                                bundle.putByteArray("avatar", model.getProavatar());
                                Intent intent = new Intent(context, com.example.pert10_sharedpreferences.MainActivity5.class);
                                intent.putExtra("userdata", bundle);
                                context.startActivity(intent);
                                break;
                            case R.id.delete_menu:
                                //delete operation
                                com.example.pert10_sharedpreferences.DBmain dBmain = new com.example.pert10_sharedpreferences.DBmain(context);
                                sqLiteDatabase = dBmain.getReadableDatabase();
                                long recdelete = sqLiteDatabase.delete(TABLENAME, "id=" + model.getId(), null);
                                if (recdelete != -1) {
                                    Toast.makeText(context, "DataDeleted",Toast.LENGTH_SHORT).show();
                                            //remove positon after deleted
                                            modelArrayList.remove(position);
                                    //update data
                                    notifyDataSetChanged();
                                }
                                break;
                            default:
                                return false;
                        }
                        return false;
                    }
                });
                //display menu
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageavatar;
        TextView txtjudul_lagu, txtartis, txttahun;
        ImageButton flowmenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageavatar = (ImageView) itemView.findViewById(R.id.viewavatar);
            txtjudul_lagu = (TextView) itemView.findViewById(R.id.txt_judullagu);
            txtartis = (TextView) itemView.findViewById(R.id.txt_artis);
            txttahun = (TextView) itemView.findViewById(R.id.txt_tahun);
            flowmenu = (ImageButton) itemView.findViewById(R.id.flowmenu);
        }
    }
}

