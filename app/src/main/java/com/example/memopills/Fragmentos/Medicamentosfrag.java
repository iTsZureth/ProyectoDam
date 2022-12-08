package com.example.memopills.Fragmentos;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.memopills.Adaptermeds;
import com.example.memopills.Activities.Nuevomed;
import com.example.memopills.Objetos.Medicamentos;
import com.example.memopills.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Medicamentosfrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Medicamentosfrag extends Fragment {
    String urlreal = "memopills.000webhostapp.com";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Bundle b;
    String id;
    ArrayList<Medicamentos> lista = new ArrayList<>();
    static RecyclerView reci;
    static Adaptermeds adaptador = null;
    View vista;
    TextView textomitad;


    public Medicamentosfrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Histfrag.
     */
    // TODO: Rename and change types and number of parameters
    public static Medicamentosfrag newInstance(String param1, String param2) {
        Medicamentosfrag fragment = new Medicamentosfrag();
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
        b = getArguments();
        id = b.getString("dato");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_medicamentos, container, false);

        reci = vista.findViewById(R.id.recifilahisto);
        reci.setLayoutManager(new LinearLayoutManager(getContext()));
        lista = new ArrayList<>();
        textomitad = (TextView) vista.findViewById(R.id.textomitad);

        Button agregar = (Button) vista.findViewById(R.id.btañadirmed);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent agregarmed = new Intent(getActivity(), Nuevomed.class);
                startActivity(agregarmed);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(vista.getContext());
        String url = "http://" + urlreal + "/android_mysql/users.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] usuario = response.toString().split(",");


                        if (usuario[6].equals("0")) {
                            agregar.setVisibility(View.INVISIBLE);
                        } else {
                            agregar.setVisibility(View.VISIBLE);
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

                params.put("id", id);

                return params;
            }
        };
        queue.add(stringRequest);

        String url2 = "http://"+urlreal+"/android_mysql/medicamentos.php";
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            String[] medicamentos = response.toString().split("-");
                            String[] aux;


                            for(int i = 0; i < medicamentos.length; i++){
                                aux = medicamentos[i].split(",");
                                lista.add(new Medicamentos(aux[0], aux[1], aux[2], aux[3]));
                            }

                                adaptador = new Adaptermeds(lista);

                            if(lista.size()>0){
                                adaptador = new Adaptermeds(lista);
                                textomitad.setVisibility(View.INVISIBLE);

                            }else{
                                textomitad.setVisibility(View.VISIBLE);
                            }
                            reci.setAdapter(adaptador);


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
        queue.add(stringRequest2);

        return vista;
    }
}