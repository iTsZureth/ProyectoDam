package com.example.memopills.Fragmentos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.memopills.Activities.MainActivity;
import com.example.memopills.Activities.Principal;
import com.example.memopills.R;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Userfrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Userfrag extends Fragment {
    String datos;
    String urlreal = "memopills.000webhostapp.com";
    Long inloc;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Userfrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Userfrag.
     */
    // TODO: Rename and change types and number of parameters
    public static Userfrag newInstance(String param1, String param2) {
        Userfrag fragment = new Userfrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_userfrag, container, false);
        Bundle b = getArguments();
        String id = b.getString("dato");


        EditText editNom = vista.findViewById(R.id.editnom2);
        EditText editEma = vista.findViewById(R.id.editemail2);
        EditText editEdad = vista.findViewById(R.id.editEdad2);
        Spinner loc = vista.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.localidades, android.R.layout.simple_list_item_1);

        loc.setAdapter(adapter);

        RequestQueue queue = Volley.newRequestQueue(vista.getContext());
        String url = "http://"+urlreal+"/android_mysql/users.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] usuario = response.toString().split(",");
                        editNom.setText(usuario[1]);
                        editEma.setText(usuario[2]);
                        editEdad.setText(usuario[3]);
                        loc.setSelection(Integer.parseInt(usuario[5])-1);
                        datos = usuario[0] + "," + usuario[2] + "," + usuario[4];

                        loc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                inloc =  parent.getSelectedItemId()+1;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);

                return params;
            }
        };
        queue.add(stringRequest);



        Button qr = (Button) vista.findViewById(R.id.btqr);
        ImageView imgQr = vista.findViewById(R.id.qrcode);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.encodeBitmap(datos, BarcodeFormat.QR_CODE, 750, 750);
                    imgQr.setImageBitmap(bitmap);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        Button borrar = (Button) vista.findViewById(R.id.borraruser);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(vista.getContext());
                String url = "http://"+urlreal+"/android_mysql/borraruser.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(vista.getContext(), "Usuario borrado correctamente", Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(vista.getContext(), "Error en el borrado del usuario", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", id);

                        return params;
                    }
                };
                queue.add(stringRequest);
                Intent inte = new Intent(getActivity() , MainActivity.class);
                startActivity(inte);
            }
        });

        Button modificar = (Button) vista.findViewById(R.id.moduser);
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(vista.getContext());
                String url = "http://"+urlreal+"/android_mysql/modificarusuario.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(vista.getContext(), "Usuario modificado correctamente", Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(vista.getContext(), "Error en la modificacion del medicamento", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("nombre", editNom.getText().toString());
                        params.put("email", editEma.getText().toString());
                        params.put("edad", editEdad.getText().toString());
                        params.put("localidad", inloc.toString());
                        params.put("id", id);

                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });
        return vista;
    }

}