package com.example.memopills.Fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.memopills.Adapter;
import com.example.memopills.Objetos.Pastillas;
import com.example.memopills.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Reciclerfrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reciclerfrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Pastillas> lista = new ArrayList<>();
    static RecyclerView reci;
    static Adapter adaptador = null;
    View vista;

    public Reciclerfrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reciclerfrag.
     */
    // TODO: Rename and change types and number of parameters
    public static Reciclerfrag newInstance(String param1, String param2) {
        Reciclerfrag fragment = new Reciclerfrag();
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

        vista = inflater.inflate(R.layout.fragment_reciclerfrag, container, false);
        Bundle b = getArguments();
        String iduser = b.getString("dato");



        RequestQueue queue = Volley.newRequestQueue(vista.getContext());
        String url = "http://192.168.1.128/android_mysql/pastillas.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            String[] pastillas = response.toString().split("-");
                            String[] aux;

                                for(int i = 0; i < pastillas.length; i++){
                                    aux = pastillas[i].split(",");
                                    lista.add(new Pastillas(aux[0], aux[1], aux[2], aux[3], aux[4]));
                                }

                            reci = vista.findViewById(R.id.recifila);
                            reci.setLayoutManager(new LinearLayoutManager(getContext()));
                            adaptador = new Adapter(lista);
                            reci.setAdapter(adaptador);

                        }catch(ArrayIndexOutOfBoundsException e){
                            Toast.makeText(getActivity(), "No hay pastillas", Toast.LENGTH_SHORT).show();
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

                params.put("iduser", iduser);

                return params;
            }
        };
        queue.add(stringRequest);


        return vista;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actualizar();
    }

    public void actualizar(){
        reci = vista.findViewById(R.id.recifila);
        reci.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptador = new Adapter(lista);
        reci.setAdapter(adaptador);

    }
}