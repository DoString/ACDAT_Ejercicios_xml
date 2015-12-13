package com.example.acdat.ejercicios_xml;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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

public class Titulares extends AppCompatActivity {

    AdaptadorNoticias _adaptador;
    ArrayList<NoticiasGenericas> _noticias;
    AsyncHttpClient _cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titulares);
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

        _cliente = new AsyncHttpClient();
        _noticias = new ArrayList<>();
        final ListView lista = (ListView) findViewById(R.id.listaT);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoticiasGenericas noticia = (NoticiasGenericas)lista.getAdapter().getItem(position);
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(noticia.get_link()));
                startActivity(i);
            }
        });


        Bundle bundle = getIntent().getExtras();
        descargarYMostrar(lista, bundle);
    }

    private void descargarYMostrar(final ListView lista, Bundle bundle) {
        final ProgressDialog p = new ProgressDialog(this);
        p.setMessage("Cargando");
        p.show();

        _cliente.setMaxRetriesAndTimeout(0, 10000);
        _cliente.get(bundle.getString("url"), new FileAsyncHttpResponseHandler(Titulares.this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                p.dismiss();
                Toast.makeText(Titulares.this, "Error al descargar el fichero", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                p.dismiss();
                try {
                    _noticias = Analisis.analizarNoticias(file);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (_noticias.size() > 0) {
                    _adaptador = new AdaptadorNoticias(Titulares.this, R.layout.titular_item, _noticias, getLayoutInflater() );

                    lista.setAdapter(_adaptador);
                }
            }
        });
    }

}
