package com.example.eko12rpl022018;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class admin_edituser extends AppCompatActivity {
    EditText nama,email,alamat, nohp, noktp, password;
    TextView id;
    SharedPreferences sp;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edituser);

        id = findViewById(R.id.eid);
        nama = findViewById(R.id.enama);
        email = findViewById(R.id.eemail);
        alamat = findViewById(R.id.ealamat);
        nohp = findViewById(R.id.enohp);
        noktp = findViewById(R.id.enoktp);
        password = findViewById(R.id.epassword);

        Bundle extras = getIntent().getExtras();
        final String aid = extras.getString("id");
        final String anama = extras.getString("nama");
        final String aemail = extras.getString("email");
        final String anohp = extras.getString("nohp");
        final String aalamat = extras.getString("alamat");
        final String anoktp = extras.getString("noktp");
        final String apass = extras.getString("password");
        final String arole = extras.getString("roleuser");

        id.setText("Id :" + aid);
        nama.setText(anama);
        email.setText(aemail);
        alamat.setText(aalamat);
        nohp.setText(anohp);
        noktp.setText(anoktp);
        password.setText(apass);

        findViewById(R.id.addsave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidNetworking.post("https://animendo.000webhostapp.com/APIsepeda/user/updateuser.php")
                        .addBodyParameter("id",aid)
                        .addBodyParameter("nama", nama.getText().toString())
                        .addBodyParameter("email", email.getText().toString())
                        .addBodyParameter("nohp", nohp.getText().toString())
                        .addBodyParameter("alamat", alamat.getText().toString())
                        .addBodyParameter("noktp", noktp.getText().toString())
                        .addBodyParameter("password", password.getText().toString())
                        .addBodyParameter("roleuser", arole)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean sukses = hasil.getBoolean("respon");
                                    if (sukses) {
                                        Toast.makeText(admin_edituser.this, "Edit Suskses", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(admin_edituser.this, menu_admin.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(admin_edituser.this, "Edit gagal", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(admin_edituser.this, "Edit gagal", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(admin_edituser.this)
                        .setTitle("Delete Data Ini?")
                        .setMessage("data yang sudah dihapus tidak dapat dikembalikan lagi")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                AndroidNetworking.post("https://animendo.000webhostapp.com/APIsepeda/user/deleteuser.php")
                                        .addBodyParameter("id",aid)
                                        .setPriority(Priority.LOW)
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    JSONObject hasil = response.getJSONObject("hasil");
                                                    boolean sukses = hasil.getBoolean("respon");
                                                    if (sukses) {
                                                        Toast.makeText(admin_edituser.this, "Data dihapus", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(admin_edituser.this, menu_admin.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(admin_edituser.this, "Data gagal dihapus", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }

                                            @Override
                                            public void onError(ANError anError) {
                                                Toast.makeText(admin_edituser.this, anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }).create().show();
            }

        });
    }

}