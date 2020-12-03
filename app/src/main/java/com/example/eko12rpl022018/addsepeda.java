package com.example.eko12rpl022018;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class addsepeda extends AppCompatActivity {

    EditText judul, kode, merk, jenis, harga;
    String link;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsepeda);

        kode = findViewById(R.id.eskode);
        merk = findViewById(R.id.esmerk);
        jenis = findViewById(R.id.esjenis);
        harga = findViewById(R.id.esharga);
        judul = findViewById(R.id.esjudul);
        iv = findViewById(R.id.iv);
        iv.setImageURI(Uri.parse("android.resource://com.example.eko12rpl022018/drawable/logo"));
        link = "android.resource://com.example.eko12rpl022018/drawable/logo";

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkImage();
            }
        });

        findViewById(R.id.addsave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String skode = kode.getText().toString();
                String smerk = merk.getText().toString();
                String sjenis = jenis.getText().toString();
                String sharga = harga.getText().toString();
                String sjudul = judul.getText().toString();

                if (skode.length() < 1 ||
                        smerk.length() < 1 ||
                        sjenis.length() < 1 ||
                        sharga.length() < 1 ||
                        sjudul.length() < 1 ){
                    Toast.makeText(addsepeda.this, "Isi semua data", Toast.LENGTH_SHORT).show();
                    return;}

                AndroidNetworking.post("https://animendo.000webhostapp.com/APIsepeda/sepeda/addsepeda.php")
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
                                    if (sukses){
                                        Toast.makeText(addsepeda.this, "Tambah Data Suskses", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(addsepeda.this, "Tambah Data Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                    System.out.println("pppp" + e.getMessage());
                                    Toast.makeText(addsepeda.this, "Tambah Data Gagal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                System.out.println("ttttt" + anError);
                                System.out.println("ttttt" + anError.getErrorBody());
                                System.out.println("ttttt" + anError.getErrorDetail());
                                System.out.println("ttttt" + anError.getResponse());
                                System.out.println("ttttt" + anError.getErrorCode());

                                Toast.makeText(addsepeda.this, "Tambah Data Gagal", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    void linkImage(){
        AlertDialog.Builder alert = new AlertDialog.Builder(addsepeda.this);

        final EditText edittext = new EditText(addsepeda.this);
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
                    Toast.makeText(addsepeda.this, "file yang di dukung .png/.jpg/.jpeg/.webp", Toast.LENGTH_SHORT).show();
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