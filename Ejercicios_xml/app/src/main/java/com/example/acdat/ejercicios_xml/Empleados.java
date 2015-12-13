package com.example.acdat.ejercicios_xml;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Empleados extends AppCompatActivity implements View.OnClickListener{

    TextView _informacion;
    Button _analizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados);
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

        _analizar = (Button) findViewById(R.id.analizar);
        _analizar.setOnClickListener(this);
        _informacion = (TextView) findViewById(R.id.info);
    }

    @Override
    public void onClick(View v) {
        try {
            Analisis.analizarEmpleados(this, _informacion);
        } catch (XmlPullParserException e) {
            Toast.makeText(Empleados.this, "Error al analizar el contenido xml", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(Empleados.this, "Error al abrir el fichero", Toast.LENGTH_SHORT).show();
        }
    }
}
