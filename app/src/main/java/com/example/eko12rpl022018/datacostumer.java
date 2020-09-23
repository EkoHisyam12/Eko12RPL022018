package com.example.eko12rpl022018;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class datacostumer extends AppCompatActivity {

    private RecyclerView recyclerView;
    private adapter adapter;

    ArrayList<model> datalist;

    CardView cvInbox;

    TextView tvNama, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_costomer);

        tvNama = findViewById(R.id.tvNama);
        tvEmail = findViewById(R.id.tvEmail);
        cvInbox = findViewById(R.id.cvInbox);
        recyclerView = findViewById(R.id.listCustomer);

        getDataCostumer();
    }

    private void getDataCostumer() {
    datalist = new ArrayList<>();
    Log.d("geo", "onCreate: ");

    AndroidNetworking.post("http:///192.168.43.225/RentalSepeda/ViewData.php")
            .setTag("test")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray data = response.getJSONArray("result");

                        for (int i = 0; i < data.length(); i++) {

                            JSONObject object = data.getJSONObject(i);
                            model model = new model();
                            model.setAlamat(object.getString("alamat"));
                            model.setNama(object.getString("nama"));
                            model.setEmail(object.getString("email"));
                            model.setId(object.getString("id"));
                            model.setNohp(object.getString("nohp"));
                            model.setNoktp(object.getString("noktp"));
                            model.setRoleuser(object.getString("roleuser"));

                            datalist.add(model);

                        }
                        adapter = new adapter(datalist);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(datacostumer.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);

                        if (response.getJSONArray("result").length() == 0) {
                            recyclerView.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(ANError anError) {
                    Toast.makeText(datacostumer.this, anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                    Log.d("geo", "onResponse: " + anError.toString());
                    Log.d("geo", "onResponse: " + anError.getErrorBody());
                    Log.d("geo", "onResponse: " + anError.getErrorCode());
                    Log.d("geo", "onResponse: " + anError.getErrorDetail());
                }
            });
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 23 && data.getStringExtra("refresh") != null) {
            //refresh list
            getDataCostumer();
            Toast.makeText(this, "data's..", Toast.LENGTH_SHORT).show();

        }
    }

}