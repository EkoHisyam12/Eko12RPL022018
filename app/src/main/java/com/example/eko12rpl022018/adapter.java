package com.example.eko12rpl022018;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.UserViewHolder> {

    private ArrayList<model> dataList;
    View viewku;

    public adapter(ArrayList<model> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        viewku = layoutInflater.inflate(R.layout.activity_adapter, parent, false);
        return new UserViewHolder(viewku);

    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder holder, final int position) {
        holder.tvNama.setText(dataList.get(position).getNama());
        holder.tvEmail.setText(dataList.get(position).getEmail());
        holder.cvInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(holder.itemView.getContext(), admin_edituser.class);
                in.putExtra("id", dataList.get(position).getId());
                in.putExtra("nama", dataList.get(position).getNama());
                in.putExtra("email", dataList.get(position).getEmail());
                in.putExtra("nohp", dataList.get(position).getNohp());
                in.putExtra("alamat", dataList.get(position).getAlamat());
                in.putExtra("noktp", dataList.get(position).getNoktp());
                in.putExtra("password", dataList.get(position).getPassword());
                in.putExtra("roleuser", dataList.get(position).getRoleuser());
                holder.itemView.getContext().startActivity(in);
            }
        });

        //Long Click
        holder.cvInbox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("LONG CLICK","BISAAAAAAAA");
                holder.background.setBackgroundColor(Color.WHITE);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNama, tvEmail;

        LinearLayout background;
        CardView cvInbox;
        Toolbar toolbar;

        UserViewHolder(View itemView) {
            super(itemView);
            cvInbox = itemView.findViewById(R.id.cvInbox);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            background = itemView.findViewById(R.id.background);
            toolbar = itemView.findViewById(R.id.toolbar);
        }
    }

}
