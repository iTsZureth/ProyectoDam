package com.example.memopills.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.memopills.Fragmentos.Addfrag;
import com.example.memopills.Fragmentos.Medicamentosfrag;
import com.example.memopills.Fragmentos.Reciclerfrag;
import com.example.memopills.Fragmentos.Userfrag;
import com.example.memopills.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal3);

        Bundle bun = this.getIntent().getExtras();
        String id = bun.getString("id");

        replaceFragmentDatos(new Reciclerfrag(), id);

        BottomNavigationView b = (BottomNavigationView) findViewById(R.id.menubot);
        b.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.user){
                    replaceFragmentDatos(new Userfrag(), id);
                }
                if(item.getItemId()==R.id.add){
                    replaceFragmentDatos(new Addfrag(), id);
                }
                if(item.getItemId()==R.id.home){
                    replaceFragmentDatos(new Reciclerfrag(), id);
                }
                if(item.getItemId()==R.id.medicamentos){
                    replaceFragmentDatos(new Medicamentosfrag(), id);
                }
                return true;
            }
        });

    }

    private void replaceFragmentDatos(Fragment fragment, String dato){
        Bundle bundle = new Bundle();
        bundle.putString("dato", dato);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

}