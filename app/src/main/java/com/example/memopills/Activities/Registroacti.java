package com.example.memopills.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.memopills.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registroacti extends AppCompatActivity {
    String urlreal = "memopills.000webhostapp.com";
    Long inloc;
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
        Spinner localidades;
        localidades = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.localidades, android.R.layout.simple_list_item_1);

        localidades.setAdapter(adapter);

        localidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inloc =  parent.getSelectedItemId()+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btnagregar = (Button) findViewById(R.id.btregis);
        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validarEmail(txtemail.getText().toString())==true){
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = "http://"+urlreal+"/android_mysql/inusuario.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error en el registro del usuario", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        public Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("nombre", txtnombre.getText().toString());
                            params.put("email", txtemail.getText().toString());
                            params.put("edad", txtedad.getText().toString());
                            params.put("localidad", inloc.toString());
                            params.put("contrasena", hash(txtpass.getText().toString()));


                            return params;
                        }
                    };
                    queue.add(stringRequest);
                    Intent changelogin = new Intent(Registroacti.this, MainActivity.class);
                    startActivity(changelogin);
                }else{
                    Toast.makeText(getApplicationContext(),"El email no es válido",Toast.LENGTH_SHORT).show();
                }
            }
        });


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

    public boolean validarEmail(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


}