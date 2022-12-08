package com.example.memopills.Fragmentos;

import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.memopills.Activities.MainActivity;
import com.example.memopills.Activities.Registroacti;
import com.example.memopills.Adaptermeds;
import com.example.memopills.Objetos.Medicamentos;
import com.example.memopills.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Addfrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Addfrag extends Fragment {
    String urlreal = "memopills.000webhostapp.com";
    ArrayList<Medicamentos> lista = new ArrayList<>();
    String[] nombre;
    Long inloc;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Addfrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Addfrag.
     */
    // TODO: Rename and change types and number of parameters
    public static Addfrag newInstance(String param1, String param2) {
        Addfrag fragment = new Addfrag();
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

        View vista = inflater.inflate(R.layout.fragment_addfrag, container, false);

        EditText txtnombre = (EditText) vista.findViewById(R.id.editnommed);
        EditText txtnombremed = (EditText) vista.findViewById(R.id.editcant);
        EditText txttiempo = (EditText) vista.findViewById(R.id.edittiempo);
        Bundle b = getArguments();
        String id = b.getString("dato");

        Spinner loc = vista.findViewById(R.id.spinner3);

        RequestQueue queue = Volley.newRequestQueue(vista.getContext());
        String url = "http://"+urlreal+"/android_mysql/medicamentos.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            String[] medicamentos = response.toString().split("-");
                            String[] aux;
                            lista = new ArrayList<>();
                            nombre = new String[medicamentos.length];

                            for(int i = 0; i < medicamentos.length; i++){
                                aux = medicamentos[i].split(",");
                                lista.add(new Medicamentos(aux[0], aux[1], aux[2], aux[3]));
                                nombre[i]=(lista.get(i).getNombre());
                            }

                            loc.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, nombre));
                            loc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    inloc =  parent.getSelectedItemId();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }catch(ArrayIndexOutOfBoundsException e){
                            Toast.makeText(getActivity(), "No hay ningún registro", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }


        }) {
            @Override
            public Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        queue.add(stringRequest);

        Button meds = (Button) vista.findViewById(R.id.btmeds);
        meds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue queue = Volley.newRequestQueue(vista.getContext());
                String url = "http://"+urlreal+"/android_mysql/intratamiento.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(vista.getContext(), "Medicamento registrado correctamente", Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(vista.getContext(), "Error en la insercion del medicamento", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("idusuario", id);
                        params.put("idmedicamento", inloc.toString());
                        params.put("nombre", txtnombre.getText().toString());
                        params.put("nombre_med", txtnombremed.getText().toString());
                        params.put("tiempo", txttiempo.getText().toString());
                        params.put("alarma", String.valueOf(0));


                        return params;
                    }
                };
                queue.add(stringRequest);

                replaceFragmentDatos(new Reciclerfrag(), id);


            }

        });
        return vista;

    }
    private void replaceFragmentDatos(Fragment fragment, String dato){
        Bundle bundle = new Bundle();
        bundle.putString("dato", dato);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }
}