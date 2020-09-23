package com.example.eko12rpl022018;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btnNext, btnRegister, btnAkun;
    TextInputLayout tfNama, tfEmail, tfPassword,tfPasswordConfirm,  tfNoktp, tfAlamat, tfNohp;
    TextView tvSebelum, tvLogin;
    String nama, email, password, confirmPassword;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNext = findViewById(R.id.btnNext);
        btnRegister = findViewById(R.id.btnRegister);
        tfNama = findViewById(R.id.tfNama);
        btnAkun = findViewById(R.id.btnAkun);
        tfEmail = findViewById(R.id.tfEmail);
        tfPassword = findViewById(R.id.tfPassword);
        tfPasswordConfirm = findViewById(R.id.tfPasswordConfirm);
        tfNoktp = findViewById(R.id.tfNoktp);
        tfAlamat = findViewById(R.id.tfAlamat);
        tfNohp = findViewById(R.id.tfNohp);
        tvSebelum = findViewById(R.id.tvSebelum);
        tvLogin = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = tfNama.getEditText().getText().toString();
                email = tfEmail.getEditText().getText().toString();
                password= tfPassword.getEditText().getText().toString();
                confirmPassword = tfPasswordConfirm.getEditText().getText().toString();
                if(nama.length() < 1 || email.length() < 1 || password.length() < 1 ){
                    Toast.makeText(MainActivity.this, "isi semua data", Toast.LENGTH_SHORT).show();
                }
                else if (password.equals(confirmPassword)){
                    btnNext.setVisibility(View.GONE);
                    tfNama.setVisibility(View.GONE);
                    tfEmail.setVisibility(View.GONE);
                    tfPassword.setVisibility(View.GONE);
                    tfPasswordConfirm.setVisibility(View.GONE);
                    tfNoktp.setVisibility(View.VISIBLE);
                    tfAlamat.setVisibility(View.VISIBLE);
                    tfNohp.setVisibility(View.VISIBLE);
                    tvSebelum.setVisibility(View.VISIBLE);
                    btnRegister.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(MainActivity.this, "periksa password anda", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tfNohp.getEditText().getText().toString().length() < 1 || tfAlamat.getEditText().getText().toString().length() < 1 || tfNoktp.getEditText().getText().toString().length() < 1 ){
                    Toast.makeText(MainActivity.this, "isi semua data", Toast.LENGTH_SHORT).show();
                }else {
                    AndroidNetworking.post("http://192.168.43.225/RentalSepeda/Registrasi.php")
                            .addBodyParameter("email", email)
                            .addBodyParameter("nama", nama)
                            .addBodyParameter("password", password)
                            .addBodyParameter("nohp", tfNohp.getEditText().getText().toString())
                            .addBodyParameter("noktp", tfNoktp.getEditText().getText().toString())
                            .addBodyParameter("alamat", tfAlamat.getEditText().getText().toString())
                            .addBodyParameter("roleuser", "1")
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONObject hasil = response.getJSONObject("hasil");
                                        Log.d("RBA", "url: " + hasil.toString());
                                        Boolean respon = hasil.getBoolean("respon");
                                        if (respon) {
                                            Toast.makeText(MainActivity.this, "Sukses Register", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), login.class));

                                        } else {
                                            Toast.makeText(MainActivity.this, "Gagal Reister", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Toast.makeText(MainActivity.this, anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        tvSebelum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNext.setVisibility(View.VISIBLE);
                tfNama.setVisibility(View.VISIBLE);
                tfEmail.setVisibility(View.VISIBLE);
                tfPassword.setVisibility(View.VISIBLE);
                tfPasswordConfirm.setVisibility(View.VISIBLE);
                tfNoktp.setVisibility(View.GONE);
                tfAlamat.setVisibility(View.GONE);
                tfNohp.setVisibility(View.GONE);
                tvSebelum.setVisibility(View.GONE);
                btnRegister.setVisibility(View.GONE);
            }
        });

    }
}