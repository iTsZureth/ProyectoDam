package com.example.memopills.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.memopills.R;
import com.example.memopills.prueba;
import com.example.memopills.qrprueba;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    String str_email,str_password;
    String url = "http://192.168.1.128/android_mysql/registro.php";
    EditText email;
    EditText pass;
    CheckBox check;
    static View view;

    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_MemoPills);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView texto = (TextView) findViewById(R.id.textlogin);
        texto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Lector -QR");
                integrator.setCameraId(0);
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(false);
                integrator.setCaptureActivity(qrprueba.class);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        email = findViewById(R.id.editEmail);
        pass = findViewById(R.id.editcontra);
        Button registrar = (Button) findViewById(R.id.btregistro);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(MainActivity.this, Registroacti.class);
                startActivity(registro);
            }
        });

        Button loguear = (Button) findViewById(R.id.btlogin);
        loguear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });

    }

    public void login(View view) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        if(email.getText().toString().equals("")){
            Toast.makeText(this, "Introduzca email", Toast.LENGTH_SHORT).show();
        }
        else if(pass.getText().toString().equals("")){
            Toast.makeText(this, "Introduzca contraseña", Toast.LENGTH_SHORT).show();
        }
        else{
            str_email = email.getText().toString().trim();
            str_password = pass.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String id = response.substring(39,40);
                    response = response.substring(0,39);
                    if(response.equalsIgnoreCase("El usuario se logueo de manera correcta")){

                        email.setText("");
                        pass.setText("");

                        Bundle extras = new Bundle();
                        extras.putString("id", id);
                        Intent inte = new Intent(MainActivity.this ,Principal.class);
                        inte.putExtras(extras);
                        startActivity(inte);
                        Toast.makeText(getApplicationContext(), response.length(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }

                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("email",str_email);
                    params.put("pass",str_password);
                    return params;
                }
            };


            requestQueue.add(request);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Escaneado", Toast.LENGTH_LONG).show();
                String[] datos = result.getContents().split(",");
                login_qr(view, datos[0], datos[1]);
            }
        }
    }

    public void login_qr(View view, String pass, String email){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en la insercion del usuario", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", email.toString());
                params.put("pass", pass.toString());

                return params;
            }
        };
        queue.add(stringRequest);
        Intent changelogin = new Intent(MainActivity.this, Principal.class);
        startActivity(changelogin);
    }

}

