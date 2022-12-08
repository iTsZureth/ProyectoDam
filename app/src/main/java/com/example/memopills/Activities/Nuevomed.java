package com.example.memopills.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Nuevomed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevomed);
        String urlreal = "memopills.000webhostapp.com";


        EditText nombre = (EditText) findViewById(R.id.editnombremed);
        EditText tiempo = (EditText) findViewById(R.id.edittiempomed);
        EditText observaciones = (EditText) findViewById(R.id.editobservacionesmed);

        Button agregar = (Button) findViewById(R.id.button2);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://"+urlreal+"/android_mysql/inmedicamento.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), "Medicamento registrado correctamente", Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error en la insercion del medicamento", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("nombre", nombre.getText().toString());
                        params.put("tiempo", tiempo.getText().toString());
                        params.put("observaciones", observaciones.getText().toString());

                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}