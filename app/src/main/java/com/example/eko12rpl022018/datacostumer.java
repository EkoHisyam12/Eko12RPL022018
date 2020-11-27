package com.example.eko12rpl022018;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.ToLongBiFunction;

public class datacostumer extends Fragment {

    private RecyclerView recyclerView;
    private adapter adapter;

    Toolbar tolbar;
    ArrayList<model> datalist;
    CardView cvInbox;
    TextView tvNama, tvEmail;
    SwipeRefreshLayout refresh;
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_data_costomer, container, false);

       tvNama = view.findViewById(R.id.tvNama);
       tvEmail = view.findViewById(R.id.tvEmail);
       cvInbox = view.findViewById(R.id.cvInbox);
       recyclerView = view.findViewById(R.id.listCustomer);
       refresh = view.findViewById(R.id.refresh);
       tolbar = (Toolbar) view.findViewById(R.id.toolbar);
       rv = view.findViewById(R.id.listCustomer);
       getDataCostumer();

       //((AppCompatActivity) getActivity()).setSupportActionBar(tolbar);
        //((AppCompatActivity) getActivity()).onCreateOptionsMenu();

        rv.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));

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
                Intent intent = new Intent(getActivity(), admin_adduser.class);
                startActivity(intent);
            }
        });


       return view;
    }

    public void getDataCostumer() {
    datalist = new ArrayList<>();
    Log.d("geo", "onCreate: ");

    AndroidNetworking.post("https://animendo.000webhostapp.com/APIsepeda/user/getuser.php")
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
                            model.setPassword(object.getString("password"));
                            model.setId(object.getString("id"));
                            model.setNohp(object.getString("nohp"));
                            model.setNoktp(object.getString("noktp"));
                            model.setRoleuser(object.getString("roleuser"));

                            datalist.add(model);

                        }
                        adapter = new adapter(datalist);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
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
                    Toast.makeText(getActivity(), anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                    Log.d("geo", "onResponse: " + anError.toString());
                    Log.d("geo", "onResponse: " + anError.getErrorBody());
                    Log.d("geo", "onResponse: " + anError.getErrorCode());
                    Log.d("geo", "onResponse: " + anError.getErrorDetail());
                }
            });
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 23 && data.getStringExtra("refresh") != null) {
            //refresh list
            getDataCostumer();
            Toast.makeText(getActivity(), "data's..", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ((AppCompatActivity) getActivity()).setSupportActionBar(tolbar);
       //((AppCompatActivity) getActivity()).getMenuInflater().inflate(R.menu.deletedata, menu);
    }

}