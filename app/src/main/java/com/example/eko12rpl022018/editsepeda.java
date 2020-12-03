package com.example.eko12rpl022018;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class editsepeda extends AppCompatActivity {
    TextView id;
    EditText judul, kode, merk, jenis, harga;
    String link;
    ImageView iv;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editsepeda);

        id = findViewById(R.id.eid);
        kode = findViewById(R.id.eskode);
        merk = findViewById(R.id.esmerk);
        jenis = findViewById(R.id.esjenis);
        harga = findViewById(R.id.esharga);
        judul = findViewById(R.id.esjudul);
        iv = findViewById(R.id.iv);

        Bundle extras = getIntent().getExtras();
        final String aid = extras.getString("id");
        final String akode = extras.getString("kode");
        final String amerk = extras.getString("merk");
        final String ajenis = extras.getString("jenis");
        final String aharga = extras.getString("harga");
        final String ajudul = extras.getString("judul");
        final String alink = extras.getString("link");

        id.setText("Id :" + aid);
        kode.setText(akode);
        merk.setText(amerk);
        jenis.setText(ajenis);
        harga.setText(aharga);
        judul.setText(ajudul);
        link = alink;
        Picasso.get().load(Uri.parse(alink)).into(iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkImage();
            }
        });

        findViewById(R.id.addsave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidNetworking.post("https://animendo.000webhostapp.com/APIsepeda/sepeda/updatesepeda.php")
                        .addBodyParameter("id", aid)
                        .addBodyParameter("kode", kode.getText().toString())
                        .addBodyParameter("merk", merk.getText().toString())
                        .addBodyParameter("jenis", jenis.getText().toString())
                        .addBodyParameter("harga", harga.getText().toString())
                        .addBodyParameter("judul", judul.getText().toString())
                        .addBodyParameter("linkimg", link)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean sukses = hasil.getBoolean("respon");
                                    if (sukses) {
                                        Toast.makeText(editsepeda.this, "Edit Suskses", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(editsepeda.this, "Edit gagal", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(editsepeda.this, "Edit gagal", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(editsepeda.this)
                        .setTitle("Delete Data Ini?")
                        .setMessage("data yang sudah dihapus tidak dapat dikembalikan lagi")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                AndroidNetworking.post("https://animendo.000webhostapp.com/APIsepeda/sepeda/deletesepeda.php")
                                        .addBodyParameter("id",aid)
                                        .setPriority(Priority.LOW)
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    JSONObject  hasil   = response.getJSONObject("hasil");
                                                    boolean sukses = hasil.getBoolean("respon");
                                                    if (sukses) {
                                                        Toast.makeText(editsepeda.this, "Data dihapus", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    } else {
                                                        Toast.makeText(editsepeda.this, "Data gagal dihapus", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }

                                            @Override
                                            public void onError(ANError anError) {
                                                Toast.makeText(editsepeda.this, anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }).create().show();
            }

        });
    }

    void linkImage(){
        AlertDialog.Builder alert = new AlertDialog.Builder(editsepeda.this);

        final EditText edittext = new EditText(editsepeda.this);
        alert.setMessage("Masukan link URL dengan akhiran .png/.jpg/.jpeg/.webp");
        alert.setTitle("Image");

        alert.setView(edittext);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String URL = edittext.getText().toString();
                if (edittext.getText().length() < 5 && URL.endsWith("png") || URL.endsWith("jpg") || URL.endsWith("webp") || URL.endsWith("jpeg"))
                {
                    link = edittext.getText().toString();
                    Picasso.get().load(Uri.parse(link)).into(iv);
                }
                else {
                    Toast.makeText(editsepeda.this, "file yang di dukung .png/.jpg/.jpeg/.webp", Toast.LENGTH_SHORT).show();
                    linkImage();
                }
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }
}