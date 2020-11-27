package com.example.eko12rpl022018;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class adapter_datasepeda extends RecyclerView.Adapter<adapter_datasepeda.UserViewHolder> {

    private ArrayList<model_sepeda> dataList;
    View viewku;

    public adapter_datasepeda(ArrayList<model_sepeda> dataList) {
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        viewku = layoutInflater.inflate(R.layout.activity_adapter_datasepeda, parent, false);
        return new UserViewHolder(viewku);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder holder, final int position) {
        holder.snama.setText(dataList.get(position).getJudul());
        holder.sharga.setText(dataList.get(position).getHarga());
        Picasso.get().load(Uri.parse(dataList.get(position).getLink())).into(holder.image);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(holder.itemView.getContext(), editsepeda.class);
                in.putExtra("id", dataList.get(position).getId());
                in.putExtra("kode", dataList.get(position).getKode());
                in.putExtra("merk", dataList.get(position).getMerk());
                in.putExtra("jenis", dataList.get(position).getJenis());
                in.putExtra("link", dataList.get(position).getLink());
                in.putExtra("harga", dataList.get(position).getHarga());
                in.putExtra("judul", dataList.get(position).getJudul());
                holder.itemView.getContext().startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView snama, sharga;
        ImageView image;
        CardView cv;

        UserViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            snama = itemView.findViewById(R.id.snama);
            sharga = itemView.findViewById(R.id.sharga);
            cv = itemView.findViewById(R.id.cvSepeda);

        }
    }
}