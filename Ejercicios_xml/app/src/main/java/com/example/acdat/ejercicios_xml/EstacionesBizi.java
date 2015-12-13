package com.example.acdat.ejercicios_xml;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class EstacionesBizi extends AppCompatActivity implements View.OnClickListener {

    final String URL = "http://www.zaragoza.es/api/recurso/urbanismo-infraestructuras/estacion-bicicleta.xml";
    ArrayList<Estacion> estaciones;
    ListView lista;
    ImageButton reload;
    AdaptadorBizis adaptador;
    AsyncHttpClient _cliente;
    TextView _hora;
    Calendar cal;
    int actualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estaciones_bizi);
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
        _hora = (TextView) findViewById(R.id.hora);
        reload = (ImageButton) findViewById(R.id.reload);
        reload.setOnClickListener(this);
        lista = (ListView) findViewById(R.id.list);
        estaciones = new ArrayList<>();


        actualizar = 0;
        reload.setEnabled(false);
        //--------------------
        _cliente = new AsyncHttpClient();
        cargarLista(_cliente);
        //--------------------
        reload.setEnabled(true);
        actualizar++;




        final Activity activity = this;
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Estacion estacion = adaptador.getItem(position);
                Intent i = new Intent(EstacionesBizi.this, EstacionDetalles.class);
                i.putExtra("titulo", estacion.get_titulo());
                i.putExtra("estado", estacion.get_estado());
                i.putExtra("nBicis", estacion.get_nBicis());
                i.putExtra("nAnclajes", estacion.get_nAnclajes());
                i.putExtra("uri", estacion.get_uri());
                i.putExtra("fecha", estacion.get_fechaUltimaMod());
                activity.startActivity(i);
            }
        });
    }

    private void cargarLista(AsyncHttpClient _cliente) {
        _cliente.setMaxRetriesAndTimeout(0, 10000);
        final ProgressDialog progreso = new ProgressDialog(this);
        progreso.setIndeterminate(true);
        progreso.setMessage("Cargando");
        progreso.show();
        _cliente.get(URL, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                progreso.dismiss();
                Toast.makeText(EstacionesBizi.this, "Error al descargar el fichero xml", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                progreso.dismiss();
                try {
                    estaciones = Analisis.analizar(file);
                } catch (IOException e) {
                    Toast.makeText(EstacionesBizi.this, "Error al abrir el fichero xml", Toast.LENGTH_SHORT).show();
                } catch (XmlPullParserException e) {
                    Toast.makeText(EstacionesBizi.this, "Error al parsear el fichero xml, error desconocido", Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    Toast.makeText(EstacionesBizi.this, "Error al parsear el fichero xml, el string no tiene el formato correcto", Toast.LENGTH_SHORT).show();
                }
                adaptador = new AdaptadorBizis(EstacionesBizi.this, R.layout.item, estaciones, getLayoutInflater());
                lista.setAdapter(adaptador);


                if ((actualizar = estaciones.size()) > 0) {
                    _hora.setText(estaciones.get(0).get_fechaUltimaMod().substring(11));
                    Toast.makeText(EstacionesBizi.this, "Lista Actualizada", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(EstacionesBizi.this, "No se ha podido actualizar intente de nuevo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        v.setEnabled(false);
        estaciones.clear();
        cargarLista(_cliente);
        v.setEnabled(true);

    }
}
