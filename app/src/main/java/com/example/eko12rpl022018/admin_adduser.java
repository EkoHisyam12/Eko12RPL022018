package com.example.eko12rpl022018;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class admin_adduser extends AppCompatActivity {

    EditText nama, email, alamat, nohp,  noktp, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_adduser);

        nama = findViewById(R.id.anama);
        email = findViewById(R.id.aemail);
        alamat = findViewById(R.id.aalamat);
        nohp = findViewById(R.id.anohp);
        noktp = findViewById(R.id.anoktp);
        password = findViewById(R.id.apassword);

        findViewById(R.id.addsave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String semail = email.getText().toString();
                String snama = nama.getText().toString();
                String salamat = alamat.getText().toString();
                String snohp = nohp.getText().toString();
                String snoktp = noktp.getText().toString();
                String spassword = password.getText().toString();

                if (semail.length() < 1 ||
                        snama.length() < 1 ||
                        salamat.length() < 1 ||
                        snohp.length() < 1 ||
                        snoktp.length() < 1 ||
                        spassword.length() < 1 ){
                    Toast.makeText(admin_adduser.this, "Isi semua data", Toast.LENGTH_SHORT).show();
                    return;}

                AndroidNetworking.post("https://animendo.000webhostapp.com/APIsepeda/user/adduser.php")
                        .addBodyParameter("email", semail)
                        .addBodyParameter("nama", snama)
                        .addBodyParameter("alamat", salamat)
                        .addBodyParameter("nohp", snohp)
                        .addBodyParameter("noktp", snoktp)
                        .addBodyParameter("password", spassword)
                        .addBodyParameter("roleuser", "1")
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean sukses = hasil.getBoolean("respon");
                                    if (sukses){
                                        Toast.makeText(admin_adduser.this, "Tambah Data Suskses", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(admin_adduser.this, menu_admin.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(admin_adduser.this, "Tambah Data Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                    System.out.println("pppp" + e.getMessage());
                                    Toast.makeText(admin_adduser.this, "Tambah Data Gagal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                System.out.println("ttttt" + anError);
                                System.out.println("ttttt" + anError.getErrorBody());
                                System.out.println("ttttt" + anError.getErrorDetail());
                                System.out.println("ttttt" + anError.getResponse());
                                System.out.println("ttttt" + anError.getErrorCode());

                                Toast.makeText(admin_adduser.this, "Tambah Data Gagal", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}