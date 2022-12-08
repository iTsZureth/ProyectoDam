package com.example.memopills.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.memopills.R;

import java.util.HashMap;
import java.util.Map;

public class Registroacti extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroacti);
        TextView login = (TextView) findViewById(R.id.textlogin);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registroacti.this, MainActivity.class);
                startActivity(intent);
            }
        });

        EditText txtnombre = (EditText) findViewById(R.id.editnom);
        EditText txtemail = (EditText) findViewById(R.id.editape);
        EditText txtpass = (EditText) findViewById(R.id.editpassres);
        EditText txtedad = (EditText) findViewById(R.id.editage);

        RequestQueue queue = Volley.newRequestQueue(this);

        Button btnagregar = (Button) findViewById(R.id.btregis);
        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.1.128/android_mysql/insertar.php";
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

                        params.put("nombre", txtnombre.getText().toString());
                        params.put("email", txtemail.getText().toString());
                        params.put("edad", txtedad.getText().toString());
                        params.put("pass", txtpass.getText().toString());


                        return params;
                    }
                };
                queue.add(stringRequest);
                Intent changelogin = new Intent(Registroacti.this, MainActivity.class);
                startActivity(changelogin);
            }
        });


    }

}