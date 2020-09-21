package com.example.eko12rpl022018;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {

    Button btnLogin;
    EditText username;
    EditText password;
    TextView tvRegister;
    String roleuser;
    ProgressDialog progressDialog;
    SharedPreferences sp;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        username = findViewById(R.id.lEmail);
        password = findViewById(R.id.lPasswprd);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, MainActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Logging In...");
                progressDialog.show();
                AndroidNetworking.post("http://192.168.43.225/RentalSepeda/login.php")
                        .addBodyParameter("username", username.getText().toString())
                        .addBodyParameter("password", password.getText().toString())
                        .setTag("test")
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("hasil", "onResponse: ");
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    Log.d("RBA", "url: "+ hasil.toString());
                                    Boolean respon = hasil.getBoolean("respon");
                                    if(respon){
                                        Toast.makeText(login.this, "Sukses Login", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),datacostumer.class));
                                        progressDialog.dismiss();
                                    }else{
                                        Toast.makeText(login.this, "Gagal Login", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(login.this, anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });

            }
        });
    }

    ;

}
