package com.example.acdat.ejercicios_xml;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class EstacionDetalles extends AppCompatActivity implements View.OnClickListener{

    TextView nBicis,nAnclajes,estado, fecha, estacion;
    ImageButton mapa;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacion_detalles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        bundle = this.getIntent().getExtras();
        nBicis = (TextView) findViewById(R.id.bicis);
        nAnclajes = (TextView) findViewById(R.id.anclajes);
        estado = (TextView) findViewById(R.id.estado);
        mapa = (ImageButton) findViewById(R.id.mapa);
        mapa.setOnClickListener(this);
        fecha = (TextView) findViewById(R.id.fecha);
        estacion = (TextView) findViewById(R.id.titulo);

        //----------------

        fecha.setText(bundle.getString("fecha"));
        nBicis.setText(String.valueOf(bundle.getInt("nBicis")));
        nAnclajes.setText(String.valueOf(bundle.getInt("nAnclajes")));
        estado.setText(bundle.getString("estado"));
        fecha.setText(bundle.getString("fecha"));
        estacion.setText(bundle.getString("titulo"));
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(bundle.getString("uri")));
        startActivity(i);
    }
}
