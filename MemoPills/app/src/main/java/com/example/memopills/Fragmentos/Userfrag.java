package com.example.memopills.Fragmentos;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
    static String datos = "";
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
        TextView nombre = vista.findViewById(R.id.txtusnom);
        TextView email = vista.findViewById(R.id.txtusemail);
        TextView edad = vista.findViewById(R.id.txtusedad);
        TextView pass = vista.findViewById(R.id.txtuscontra);

        RequestQueue queue = Volley.newRequestQueue(vista.getContext());
        String url = "http://192.168.1.128/android_mysql/users.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] usuario = response.toString().split(",");
                        nombre.setText(usuario[0]);
                        email.setText(usuario[1]);
                        edad.setText(usuario[2]);
                        pass.setText(usuario[3]);
                        datos = usuario[1] + "," + usuario[3];
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
        return vista;
    }

}