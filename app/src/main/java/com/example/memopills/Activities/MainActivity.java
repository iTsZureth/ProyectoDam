package com.example.memopills.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
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
import com.example.memopills.qrprueba;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    String str_email,str_password;
    String urlreal = "memopills.000webhostapp.com";
    EditText email;
    EditText pass;
    String ema;
    String id;
    String contr;
    static View view;

    protected void onCreate(Bundle savedInstanceState) {
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
                login();
            }
        });

    }

    public void login() {

        String url = "http://"+urlreal+"/android_mysql/logueo.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        if(email.getText().toString().equals("")){
            Toast.makeText(this, "Introduzca email", Toast.LENGTH_SHORT).show();
        }
        else if(pass.getText().toString().equals("")){
            Toast.makeText(this, "Introduzca contraseña", Toast.LENGTH_SHORT).show();
        }
        else if(secureSQL(email.getText().toString())==true || secureSQL(pass.getText().toString())==true) {
            Toast.makeText(this, "Caracteres no permitidos", Toast.LENGTH_LONG).show();
        }else{
            str_email = email.getText().toString().trim();
            str_password = hash(pass.getText().toString().trim());

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        String[] respuesta = response.toString().split(",");
                        String id = respuesta[1];
                        response = respuesta[0];
                        if(response.equalsIgnoreCase("El usuario se logueo de manera correcta")){

                            email.setText("");
                            pass.setText("");

                            Bundle extras = new Bundle();
                            extras.putString("id", id);
                            Intent inte = new Intent(MainActivity.this ,Principal.class);
                            inte.putExtras(extras);
                            startActivity(inte);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Error en el inicio de sesion", Toast.LENGTH_SHORT).show();
                        }
                    }catch (IndexOutOfBoundsException e){
                        Toast.makeText(getApplicationContext(), "Error en el inicio de sesion", Toast.LENGTH_SHORT).show();
                    }


                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("email",str_email);
                    params.put("contrasena",str_password);
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
                Toast.makeText(this, "Escaneado de qr cancelado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Codigo qr escaneado correctamente", Toast.LENGTH_LONG).show();
                String[] datos = result.getContents().split(",");
                ema = datos[1].toString();
                id = datos[0].toString();
                contr = datos[2].toString();
                login_qr(contr, ema, id);
            }
        }
    }

    public void login_qr(String pass, String email, String id){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://"+urlreal+"/android_mysql/logueo.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Inicio de sesion correcto", Toast.LENGTH_LONG).show();
                        Bundle extras = new Bundle();
                        extras.putString("id", id);
                        Intent inte = new Intent(MainActivity.this ,Principal.class);
                        inte.putExtras(extras);
                        startActivity(inte);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en el inicio de sesion", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", email.toString());
                params.put("contrasena", pass.toString());

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public String hash(String pass){
        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("SHA-256");

        }catch(NoSuchAlgorithmException n){
            n.printStackTrace();
        }

        byte[] hash = md.digest(pass.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hash){
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public boolean secureSQL(String dato){
        if(dato.contains("'") || dato.contains("=") || dato.contains("*") || dato.contains("/") || dato.contains("-") || dato.contains("`")
                || dato.contains("?") || dato.contains("¿") || dato.contains("¡") || dato.contains("!") || dato.contains("&") || dato.contains("|")){
            return true;
        }else{
            return false;
        }
    }

}

