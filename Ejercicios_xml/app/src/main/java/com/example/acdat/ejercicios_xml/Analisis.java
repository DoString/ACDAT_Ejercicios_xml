package com.example.acdat.ejercicios_xml;

import android.app.Activity;
import android.content.Context;
import android.net.ParseException;
import android.util.Xml;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Analisis {
    static XmlPullParser xml;

    public static ArrayList<NoticiasGenericas> analizarNoticias(File file) throws XmlPullParserException, IOException {
        ArrayList<NoticiasGenericas> noticias = new ArrayList<>();
        xml = Xml.newPullParser();
        xml.setInput(new FileReader(file));
        int evento = xml.getEventType();
        boolean dentroItem = false;
        NoticiasGenericas noticia = null;
        while (evento != XmlPullParser.END_DOCUMENT) {
            switch (evento) {
                case XmlPullParser.START_TAG:

                    if (xml.getName().equalsIgnoreCase("item")) {
                        dentroItem = true;
                        noticia = new NoticiasGenericas();
                    }

                    if (dentroItem) {
                        if (xml.getName().equalsIgnoreCase("title"))
                            noticia.set_titulo(xml.nextText().replace("![CDATA[", "").replace("]]", ""));
                        if (xml.getName().equalsIgnoreCase("link"))
                            noticia.set_link(xml.nextText().replace("![CDATA[", "").replace("]]", ""));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (xml.getName().equalsIgnoreCase("item")) {
                        noticias.add(noticia);
                        dentroItem = false;
                    }
                    break;
            }
            evento = xml.next();
        }
        return noticias;
    }

    public static ArrayList<Estacion> analizar(File fichero) throws IOException, XmlPullParserException, java.text.ParseException {
        ArrayList<Estacion> estaciones = new ArrayList<>();
        Estacion estacion = null;
        xml = Xml.newPullParser();
        xml.setInput(new FileReader(fichero));
        int evento = xml.getEventType();
        boolean dentroEstacion = false;
        String d = "";
        while (evento != XmlPullParser.END_DOCUMENT) {
            switch (evento) {
                case XmlPullParser.START_TAG:
                    if (xml.getName().equalsIgnoreCase("estacion")) {
                        estacion = new Estacion();
                        dentroEstacion = true;
                    }

                    if (dentroEstacion) {
                        if (xml.getName().equalsIgnoreCase("uri"))
                            estacion.set_uri(xml.nextText());

                        else if (xml.getName().equalsIgnoreCase("title"))
                            estacion.set_titulo(xml.nextText());

                        else if (xml.getName().equalsIgnoreCase("estado")) {
                            String estado = xml.nextText();
                            estacion.set_estado(estado.equals("OPN") ? "Abierta" : "Cerrada");
                        } else if (xml.getName().equalsIgnoreCase("bicisDisponibles"))
                            estacion.set_nBicis(Integer.parseInt(xml.nextText()));

                        else if (xml.getName().equalsIgnoreCase("anclajesDisponibles"))
                            estacion.set_nAnclajes(Integer.parseInt(xml.nextText()));

                        else if (xml.getName().equalsIgnoreCase("lastUpdated")) {
                            d = xml.nextText().substring(0, 16).replace('-', '/').replace('T', ' '); //Fecha(10) + Hora(6)
                            estacion.set_fechaUltimaMod(d);
                        } else if (xml.getName().equalsIgnoreCase("icon")) {
                            estacion.set_imagen(xml.nextText());
                        }
                    }

                    break;
                case XmlPullParser.END_TAG:
                    if (xml.getName().equalsIgnoreCase("estacion")) {
                        estaciones.add(estacion);
                        dentroEstacion = false;
                    }
                    break;
            }
            evento = xml.next();
        }
        return estaciones;
    }

    public static ArrayList<Prediccion> analizar(File pagina, String urlImg) throws XmlPullParserException, IOException, ParseException {
        boolean estempMax = false;
        boolean estempMin = false;
        boolean esTemperatura = false;
        boolean esEstadoCielo = false;

        Prediccion prediccion = null;

        ArrayList<String> atributos = null;
        ArrayList<Prediccion> predicciones = new ArrayList<Prediccion>();

        xml = Xml.newPullParser();
        xml.setInput(new FileReader(pagina));
        int evento = xml.getEventType();
        int cuenta = 0;
        while (evento != XmlPullParser.END_DOCUMENT && cuenta < 2) {
            switch (evento) {
                case XmlPullParser.START_TAG:

                    if (xml.getName().equalsIgnoreCase("dia")) {
                        prediccion = new Prediccion();
                        prediccion.setFecha(xml.getAttributeValue(0));
                    }
                    if (xml.getName().equalsIgnoreCase("temperatura")) {
                        esTemperatura = true;
                    }
                    if (xml.getName().equalsIgnoreCase("estado_cielo")) {
                        esEstadoCielo = true;
                        atributos = new ArrayList<String>();
                        for (int i = 0; i < xml.getAttributeCount(); i++) {
                            atributos.add(xml.getAttributeValue(i));
                        }
                    }
                    if (xml.getName().equalsIgnoreCase("maxima")) {
                        estempMax = true;
                    }
                    if (xml.getName().equalsIgnoreCase("minima")) {
                        estempMin = true;
                    }
                    break;
                case XmlPullParser.TEXT:
                    if (esEstadoCielo) {
                        if (atributos.get(0).equals("00-06")) {
                            if (xml.getText().length() > 0) {
                                prediccion.setImgNoche(urlImg + xml.getText() + ".gif");
                            }
                        }
                        if (atributos.get(0).equals("06-12")) {
                            if (xml.getText().length() > 0) {
                                prediccion.setImgAmanecer(urlImg + xml.getText() + ".gif");
                            }
                        }
                        if (atributos.get(0).equals("12-18")) {
                            if (xml.getText().length() > 0) {
                                prediccion.setImgAtardecer(urlImg + xml.getText() + ".gif");
                            }
                        }
                        if (atributos.get(0).equals("18-24")) {
                            if (xml.getText().length() > 0) {
                                prediccion.setImgAnochecer(urlImg + xml.getText() + ".gif");
                            }
                        }
                    }
                    if (esTemperatura & estempMax) {
                        prediccion.setTempMax(xml.getText());
                    }
                    if (esTemperatura & estempMin) {
                        prediccion.setTempMin(xml.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (xml.getName().equalsIgnoreCase("dia")) {
                        predicciones.add(prediccion);
                        cuenta++;
                    }
                    if (xml.getName().equalsIgnoreCase("temperatura")) {
                        esTemperatura = false;
                    }
                    if (xml.getName().equalsIgnoreCase("estado_cielo")) {
                        esEstadoCielo = false;
                    }
                    if (xml.getName().equalsIgnoreCase("maxima")) {
                        estempMax = false;
                    }
                    if (xml.getName().equalsIgnoreCase("minima")) {
                        estempMin = false;
                    }
                    break;
            }
            evento = xml.next();
        }

        return predicciones;
    }

    public static void analizarEmpleados(Context contexto, TextView informacion) throws XmlPullParserException, IOException {
        boolean esNombre = false;
        boolean esPuesto = false;
        boolean esEdad = false;
        boolean esSueldo = false;
        boolean PrimeraVez = true;
        int edadMedia;
        int contMedia = 0;
        int numEmpleados = 0;
        int sueldoMaximo = 0;
        int sueldoMinimo = 0;


        xml = contexto.getResources().getXml(R.xml.empleados);
        int eventType = xml.getEventType();
        informacion.setText("Inicio . . . \n\n");
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (xml.getName().equalsIgnoreCase("nombre"))
                        esNombre = true;

                    if (xml.getName().equalsIgnoreCase("puesto"))
                        esPuesto = true;


                    if (xml.getName().equalsIgnoreCase("edad"))
                        esEdad = true;

                    if (xml.getName().equalsIgnoreCase("Sueldo"))
                        esSueldo = true;

                    break;

                case XmlPullParser.TEXT:
                    if (esNombre) {
                        informacion.append("Nombre: " + xml.getText() + '\n');
                        numEmpleados++;
                    }
                    if (esPuesto)
                        informacion.append("Puesto: " + xml.getText() + '\n');
                    if (esEdad) {
                        informacion.append("Edad: " + xml.getText() + '\n');
                        contMedia += Integer.parseInt(xml.getText());
                    }
                    if (esSueldo) {
                        informacion.append("Sueldo: " + xml.getText() + '\n');

                        if (PrimeraVez) {
                            sueldoMaximo = Integer.parseInt(xml.getText());
                            sueldoMinimo = Integer.parseInt(xml.getText());
                            PrimeraVez = false;
                        }

                        if (Integer.parseInt(xml.getText()) > sueldoMaximo) {
                            sueldoMaximo = Integer.parseInt(xml.getText());
                        }
                        if (Integer.parseInt(xml.getText()) < sueldoMinimo) {
                            sueldoMinimo = Integer.parseInt(xml.getText());
                        }
                        informacion.append("---------------------\n");
                    }

                    break;
                case XmlPullParser.END_TAG:
                    if (xml.getName().equalsIgnoreCase("nombre")) ;
                    esNombre = false;
                    if (xml.getName().equalsIgnoreCase("puesto"))
                        esPuesto = false;
                    if (xml.getName().equalsIgnoreCase("edad")) ;
                    esEdad = false;
                    if (xml.getName().equalsIgnoreCase("sueldo"))
                        esSueldo = false;
                    break;
            }

            eventType = xml.next();
        }

        //calcular medias , min y max
        edadMedia = contMedia / numEmpleados;

        informacion.append("\nEdad media: " + edadMedia);
        informacion.append("\nSueldo Máximo: " + sueldoMaximo);
        informacion.append("\nSueldo Mínimo: " + sueldoMinimo);
        informacion.append("\n\nFin");
    }
}
