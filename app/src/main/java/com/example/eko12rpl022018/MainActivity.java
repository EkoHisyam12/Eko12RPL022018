package com.example.eko12rpl022018;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    Button btnNext, btnRegister;
    TextInputLayout tfNama, tfEmail, tfPassword, tfNoktp, tfAlamat, tfNohp;
    TextView tvSebelum, tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNext = findViewById(R.id.btnNext);
        btnRegister = findViewById(R.id.btnRegister);
        tfNama = findViewById(R.id.tfNama);
        tfEmail = findViewById(R.id.tfEmail);
        tfPassword = findViewById(R.id.tfPassword);
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

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNext.setVisibility(View.GONE);
                tfNama.setVisibility(View.GONE);
                tfEmail.setVisibility(View.GONE);
                tfPassword.setVisibility(View.GONE);
                tfNoktp.setVisibility(View.VISIBLE);
                tfAlamat.setVisibility(View.VISIBLE);
                tfNohp.setVisibility(View.VISIBLE);
                tvSebelum.setVisibility(View.VISIBLE);
                btnRegister.setVisibility(View.VISIBLE);
            }
        });

        tvSebelum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNext.setVisibility(View.VISIBLE);
                tfNama.setVisibility(View.VISIBLE);
                tfEmail.setVisibility(View.VISIBLE);
                tfPassword.setVisibility(View.VISIBLE);
                tfNoktp.setVisibility(View.GONE);
                tfAlamat.setVisibility(View.GONE);
                tfNohp.setVisibility(View.GONE);
                tvSebelum.setVisibility(View.GONE);
                btnRegister.setVisibility(View.GONE);
            }
        });

    }
}