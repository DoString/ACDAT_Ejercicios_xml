package com.example.acdat.ejercicios_xml;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class Tiempo extends AppCompatActivity implements View.OnClickListener {

    TextView _tempMinHoy, _tempMaxHoy, _tempMinSig, _tempMaxSig, _diaHoy, _diaSig;
    Button _reload;
    ImageView _hoyManana, _hoyMedioDia, _hoyTarde,_hoyNoche,_sigManana,_sigMedioDia,_sigTarde,_sigNoche;
    LinearLayout _tablas;
    final String URL = "http://www.aemet.es/xml/municipios/localidad_29067.xml";
    final String URL_IMAGENES = "http://www.aemet.es/imagenes/gif/estado_cielo/";
    AsyncHttpClient _cliente;
    ArrayList<Prediccion> _predicciones;
    int reload = 0;
    public void inicializar()
    {
        _reload = (Button) findViewById(R.id.reload);
        _reload.setOnClickListener(this);
        _tablas = (LinearLayout) findViewById(R.id.tablas);
        _tempMinHoy = (TextView)findViewById(R.id.txvTempMinDiaHoy);
        _tempMaxHoy = (TextView)findViewById(R.id.txvTempMaxDiaHoy);
        _tempMinSig = (TextView)findViewById(R.id.txvTempMinDiaManana);
        _tempMaxSig = (TextView)findViewById(R.id.txvTempMaxDiaManana);
        _hoyManana = (ImageView)findViewById(R.id.imgHoyManana);
        _hoyMedioDia = (ImageView)findViewById(R.id.imgHoyMedioDia);
        _hoyTarde = (ImageView)findViewById(R.id.imgHoyTarde);
        _hoyNoche = (ImageView)findViewById(R.id.imgHoyNoche);
        _sigManana = (ImageView)findViewById(R.id.imgSigManana);
        _sigMedioDia = (ImageView)findViewById(R.id.imgSigMedioDia);
        _sigTarde = (ImageView)findViewById(R.id.imgSigTarde);
        _sigNoche = (ImageView)findViewById(R.id.imgSigNoche);
        _diaHoy = (TextView)findViewById(R.id.txvDiaHoy);
        _diaSig = (TextView)findViewById(R.id.txvDiaSig);

        _cliente = new AsyncHttpClient();
        _predicciones = new ArrayList<>();
    }

    private void descargarFichero(){
       final ProgressDialog p = new ProgressDialog(this);
        p.setMessage("Cargando");
        p.show();
        _cliente.get(this, URL, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                p.dismiss();
                Toast.makeText(Tiempo.this, "Error al descargar el fichero", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                p.dismiss();
                try {
                    _predicciones = Analisis.analizar(file, URL_IMAGENES);
                    boolean entro = false;
                    for (int i = 0; i < _predicciones.size(); i++) {
                        entro=true;
                        if (i == 0) {
                            Picasso.with(Tiempo.this).load(_predicciones.get(i).getImgNoche()).into(_hoyManana);
                            Picasso.with(Tiempo.this).load(_predicciones.get(i).getImgAmanecer()).into(_hoyMedioDia);
                            Picasso.with(Tiempo.this).load(_predicciones.get(i).getImgAtardecer()).into(_hoyTarde);
                            Picasso.with(Tiempo.this).load(_predicciones.get(i).getImgAnochecer()).into(_hoyNoche);
                            _tempMinHoy.setText(_predicciones.get(i).getTempMin());
                            _tempMaxHoy.setText(_predicciones.get(i).getTempMax());
                            _diaHoy.setText(_predicciones.get(i).getFecha());
                        }
                        else
                        {
                            Picasso.with(Tiempo.this).load(_predicciones.get(i).getImgNoche()).into(_sigManana);
                            Picasso.with(Tiempo.this).load(_predicciones.get(i).getImgAmanecer()).into(_sigMedioDia);
                            Picasso.with(Tiempo.this).load(_predicciones.get(i).getImgAtardecer()).into(_sigTarde);
                            Picasso.with(Tiempo.this).load(_predicciones.get(i).getImgAnochecer()).into(_sigNoche);

                            _tempMinSig.setText(_predicciones.get(i).getTempMin());
                            _tempMaxSig.setText(_predicciones.get(i).getTempMax());
                            _diaSig.setText(_predicciones.get(i).getFecha());
                        }
                    }
                    if (!entro){
                        Toast.makeText(Tiempo.this, "Error: No hay predicciones disponibles.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(reload > 0)
                            Toast.makeText(Tiempo.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                        _tablas.setVisibility(View.VISIBLE);
                    }
                } catch (XmlPullParserException e) {
                    Toast.makeText(Tiempo.this, "Error al parsear el fichero", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(Tiempo.this, "Error al abrir el fichero", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiempo);
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

        try
        {
            inicializar();
            _tablas.setVisibility(View.INVISIBLE);
            descargarFichero();
            reload++;
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error al cargar los datos, intente recargar.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        _tablas.setVisibility(View.INVISIBLE);
        _predicciones.clear();
        _reload.setEnabled(false);
        descargarFichero();
        _reload.setEnabled(true);
    }
}
