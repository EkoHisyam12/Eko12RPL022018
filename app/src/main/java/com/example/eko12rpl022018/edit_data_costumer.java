package com.example.eko12rpl022018;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class edit_data_costumer extends AppCompatActivity {
    TextView tvId;
    EditText etNama, etEmail, etNohp, etAlamat, etNoktp;
    Button eNama, eEmail, eNohp, eAlamat, eNoktp;
    Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_costumer);

        tvId = findViewById(R.id.tvId);
        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etNohp = findViewById(R.id.etNohp);
        etAlamat = findViewById(R.id.etAlamat);
        etNoktp = findViewById(R.id.etNoktp);
        btnEdit = findViewById(R.id.btnEdit);

        eNama = findViewById(R.id.eNama);
        eEmail = findViewById(R.id.eEmail);
        eNohp = findViewById(R.id.eNohp);
        eAlamat = findViewById(R.id.eAlamat);
        eNoktp = findViewById(R.id.eNoktp);

        eNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNama.setEnabled(true);
                ShowEditButton();            }
        });
        eNoktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNoktp.setEnabled(true);
                ShowEditButton();            }
        });
        eAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etAlamat.setEnabled(true);
                ShowEditButton();            }
        });
        eNohp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNohp.setEnabled(true);
                ShowEditButton();            }
        });
        eEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEmail.setEnabled(true);
                ShowEditButton();           }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Customer");

        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("id");
        final String nama = extras.getString("nama");
        final String email = extras.getString("email");
        final String nohp = extras.getString("nohp");
        final String alamat = extras.getString("alamat");
        final String noktp = extras.getString("noktp");

        tvId.setText("Id :" + id);
        etNama.setText(nama);
        etEmail.setText(email);
        etNohp.setText(nohp);
        etAlamat.setText(alamat);
        etNoktp.setText(noktp);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.post("http://192.168.43.225/RentalSepeda/UpdateData.php")
                        .addBodyParameter("id",id)
                        .addBodyParameter("nama", etNama.getText().toString())
                        .addBodyParameter("email", etEmail.getText().toString())
                        .addBodyParameter("nohp", etNohp.getText().toString())
                        .addBodyParameter("alamat", etAlamat.getText().toString())
                        .addBodyParameter("noktp", etNoktp.getText().toString())
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean sukses = hasil.getBoolean("respon");
                                    if (sukses) {
                                        Intent returnIntent = new Intent(edit_data_costumer.this, datacostumer.class);
                                        returnIntent.putExtra("refresh", "refresh");
                                        startActivityForResult(returnIntent, 23);
                                        finish();
                                        Toast.makeText(edit_data_costumer.this, "Edit Sukses", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(edit_data_costumer.this, "Edit gagal", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(edit_data_costumer.this, "Edit gagal", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void ShowEditButton(){
        btnEdit.setVisibility(View.VISIBLE);
    }
}