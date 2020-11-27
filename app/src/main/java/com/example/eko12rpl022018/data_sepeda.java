package com.example.eko12rpl022018;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class data_sepeda extends Fragment {

    TextView snama, sharga;
    RecyclerView rv;
    SwipeRefreshLayout refresh;
    ArrayList<model_sepeda> datalist;
    adapter_datasepeda adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_data_sepeda, container, false);

        snama = view.findViewById(R.id.snama);
        sharga = view.findViewById(R.id.sharga);
        rv = view.findViewById(R.id.listsepeda);
        refresh = view.findViewById(R.id.refresh);

        getDataCostumer();

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDataCostumer();
                        refresh.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        view.findViewById(R.id.iadd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Add Data", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), addsepeda.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void getDataCostumer() {
        datalist = new ArrayList<>();
        Log.d("geo", "onCreate: ");

        AndroidNetworking.post("https://animendo.000webhostapp.com/APIsepeda/sepeda/getsepeda.php")
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
                                model_sepeda model = new model_sepeda();
                                model.setKode(object.getString("kode"));
                                model.setMerk(object.getString("merk"));
                                model.setJenis(object.getString("jenis"));
                                model.setLink(object.getString("linkimg"));
                                model.setId(object.getString("id"));
                                model.setHarga(object.getString("harga"));
                                model.setJudul(object.getString("judul"));

                                datalist.add(model);

                            }
                            adapter = new adapter_datasepeda(datalist);
                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                            rv.setLayoutManager(layoutManager);
                            rv.setAdapter(adapter);

                            if (response.getJSONArray("result").length() == 0) {
                                rv.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getActivity(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        Log.d("geo", "onResponse: " + anError.toString());
                        Log.d("geo", "onResponse: " + anError.getErrorBody());
                        Log.d("geo", "onResponse: " + anError.getErrorCode());
                        Log.d("geo", "onResponse: " + anError.getErrorDetail());
                    }
                });
    }

}