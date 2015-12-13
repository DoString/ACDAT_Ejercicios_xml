package com.example.acdat.ejercicios_xml;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Noticias extends AppCompatActivity {

   ArrayList<String> _fuentes ;
    AsyncHttpClient _cliente;
    ArrayList<NoticiasGenericas> _noticias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);
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

        _fuentes = new ArrayList<>();

        _cliente = new AsyncHttpClient();
        String[] arr = new String[]{"El Pais", "El Mundo", "Linux-Magazine", "PcWorld"};

        for (int i = 0; i < arr.length ; i++) {
            _fuentes.add(arr[i]);
        }
        ListView lista = (ListView) findViewById(R.id.listaP);
        lista.setAdapter(new ArrayAdapter<String>(this, R.layout.noticia_item,R.id.fuente, _fuentes));
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String URL = "";
                switch (position){
                    case 0: URL = NoticiasGenericas.URL_ELPAIS;
                        break;
                    case 1: URL = NoticiasGenericas.URL_ELMUNDO;
                        break;
                    case 2:URL = NoticiasGenericas.URL_LINUX;
                        break;
                    case 3: URL = NoticiasGenericas.URL_PCWORLD;
                        break;
                }

                Intent i = new Intent(Noticias.this, Titulares.class);
                i.putExtra("url", URL);
                startActivity(i);
            }
        });
    }

}
