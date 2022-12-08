package com.example.memopills.Fragmentos;

import static java.lang.Integer.parseInt;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.memopills.Adapter;
import com.example.memopills.AlarmReceiver;
import com.example.memopills.Objetos.Tratamientos;
import com.example.memopills.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Reciclerfrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reciclerfrag extends Fragment implements PopupMenu.OnMenuItemClickListener {
    String urlreal = "memopills.000webhostapp.com";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    Bundle b;
    String iduser;
    ArrayList<Tratamientos> lista = new ArrayList<>();
    static RecyclerView reci;
    static Adapter adaptador = null;
    int pos = 0, alarm = 0;
    Tratamientos tratanew;
    View vista;
    TextView textomedio;
    TextView ejemplo;

    public Reciclerfrag() {
        // Required empty public constructor
    }


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
        b = getArguments();
        iduser = b.getString("dato");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_reciclerfrag, container, false);
        createNotificationChannel();

        reci = vista.findViewById(R.id.recifila);
        reci.setLayoutManager(new LinearLayoutManager(getContext()));
        textomedio = (TextView) vista.findViewById(R.id.textomedio);
        ejemplo = (TextView) vista.findViewById(R.id.ejemplo);

        RequestQueue queue = Volley.newRequestQueue(vista.getContext());
        String url = "http://"+urlreal+"/android_mysql/tratamientos.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            lista = new ArrayList<>();
                            String[] tratamiento = response.toString().split("-");
                            String[] aux;

                                for(int i = 0; i < tratamiento.length; i++){
                                    aux = tratamiento[i].split(",");
                                    lista.add(new Tratamientos(aux[0], aux[1], aux[2], aux[3], Integer.parseInt(aux[4])));
                                }

                            if(lista.size()>0){
                            adaptador = new Adapter(lista);
                            adaptador.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View view) {
                                    tratanew = lista.get(reci.getChildAdapterPosition(view));
                                    showMenu(view);
                                    return true;
                                }
                            });
                                textomedio.setVisibility(View.INVISIBLE);
                            }else{
                                textomedio.setVisibility(View.VISIBLE);
                            }
                            reci.setAdapter(adaptador);


                        }catch(ArrayIndexOutOfBoundsException e){
                            Toast.makeText(getActivity(), "No hay medicacion registrada", Toast.LENGTH_SHORT).show();
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
                params.put("idusuario", iduser);
                return params;
            }
        };
        queue.add(stringRequest);

        return vista;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.borrar:
                cancelAlarm();
                borrartratamiento();
                replaceFragmentDatos(new Intermediario(), iduser);
                return true;

            case R.id.activar:
                setAlarm(tratanew);
                modificarAlarma();
                replaceFragmentDatos(new Intermediario(), iduser);
                //actualizarlista();
                return true;

            case R.id.cancelar:
                cancelAlarm();
                modificarAlarma();
                replaceFragmentDatos(new Intermediario(), iduser);
                //actualizarlista();
                return true;
            default:
                return false;

        }
    }

    private void setAlarm(Tratamientos tratanuevo) {

       long time = 0;
       long minuto = 60000;

        if(tratanuevo.getTiempo().contains(":")){
            String[] tiempo = tratanuevo.getTiempo().split(":");
            long horas = Integer.parseInt(tiempo[0]);
            long min = Integer.parseInt(tiempo[1]);
            time = (horas*3600000)+(min*60000);
        }else{
            time = Long.parseLong(tratanuevo.getTiempo())*3600000;
        }

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + time,
                time,pendingIntent);

        Toast.makeText(getActivity(), "Alarma activada", Toast.LENGTH_SHORT).show();
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.setOnMenuItemClickListener(this);
        if(tratanew.getAlarma()==0){
            popup.inflate(R.menu.desplegable);
        }else{
            popup.inflate(R.menu.desplegable2);
        }
        popup.show();
    }

    public void borrartratamiento(){
        RequestQueue queue = Volley.newRequestQueue(vista.getContext());
        String url = "http://"+urlreal+"/android_mysql/borrartratamiento.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                        }catch(ArrayIndexOutOfBoundsException e){
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
                params.put("id", tratanew.getId());
                return params;
            }
        };
        queue.add(stringRequest);
        replaceFragmentDatos(new Medicamentosfrag(), iduser);
    }

    public void modificarAlarma(){
        if(tratanew.getAlarma()==0){
            alarm = 1;
        }else{
            alarm = 0;
        }
        RequestQueue queue = Volley.newRequestQueue(vista.getContext());
        String url = "http://"+urlreal+"/android_mysql/modificaralarma.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", tratanew.getId());
                params.put("alarma" , String.valueOf(alarm));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "foxandroidReminderChannel";
            String description = "Channel For Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("foxandroid",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void cancelAlarm() {

        Bundle extras = new Bundle();
        extras.putString("id", tratanew.getId());
        Intent intent = new Intent(getActivity(),AlarmReceiver.class);
        intent.putExtras(extras);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        if (alarmManager == null){
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
        Toast.makeText(getActivity(), "Alarma cancelada", Toast.LENGTH_SHORT).show();
    }

    public void actualizarlista(){
        lista = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(vista.getContext());
        String url = "http://"+urlreal+"/android_mysql/tratamientos.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            String[] trata = response.toString().split("-");
                            String[] aux;


                                for(int i = 0; i < trata.length; i++){
                                    aux = trata[i].split(",");
                                    lista.add(new Tratamientos(aux[0], aux[1], aux[2], aux[3], Integer.parseInt(aux[4])));
                                }
                            if(lista.size()>0){
                            adaptador = new Adapter(lista);
                            adaptador.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View view) {

                                    tratanew = lista.get(reci.getChildAdapterPosition(view));
                                    showMenu(view);
                                    return true;
                                }
                            });

                                textomedio.setVisibility(View.INVISIBLE);
                                reci.setAdapter(adaptador);
                            }else{
                                adaptador = new Adapter(lista);
                                reci.setAdapter(adaptador);
                                textomedio.setVisibility(View.VISIBLE);
                            }


                        }catch(ArrayIndexOutOfBoundsException e){
                            reci.setAdapter(null);
                            textomedio.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "No hay tratamientos", Toast.LENGTH_SHORT).show();
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
                params.put("idusuario", iduser);
                return params;
            }
        };
        queue.add(stringRequest);

        replaceFragmentDatos(new Intermediario(), iduser);

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