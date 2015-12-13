package com.example.acdat.ejercicios_xml;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button _botonMedias, _botonTiempo, _botonBizis, _botonNoticias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        _botonMedias = (Button) findViewById(R.id.btn1);
        _botonMedias.setOnClickListener(this);
        _botonTiempo = (Button) findViewById(R.id.btn2);
        _botonTiempo.setOnClickListener(this);
        _botonBizis = (Button) findViewById(R.id.btn3);
        _botonBizis.setOnClickListener(this);
        _botonNoticias = (Button) findViewById(R.id.btn4);
        _botonNoticias.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v==_botonMedias){
            Intent i = new Intent(this,Empleados.class);
            startActivity(i);
        }
        else if (v==_botonTiempo){
            Intent i = new Intent(this,Tiempo.class);
            startActivity(i);
        }
        else if (v == _botonBizis){
            Intent i = new Intent(this,EstacionesBizi.class);
            startActivity(i);
        }
        else if (v == _botonNoticias){
            Intent i = new Intent(this,Noticias.class);
            startActivity(i);
        }
    }
}
