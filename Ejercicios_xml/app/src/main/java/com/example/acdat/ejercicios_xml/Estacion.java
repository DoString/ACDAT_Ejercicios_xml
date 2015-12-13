package com.example.acdat.ejercicios_xml;

import java.util.Calendar;
import java.util.Date;

public class Estacion {
    String _estado;
    int _nBicis;
    int _nAnclajes;
    String _fechaUltimaMod;
    String _uri;
    String _titulo;
    int _pos;
    String _imagen;

    public String get_imagen() {
        return _imagen;
    }

    public void set_imagen(String _imagen) {
        this._imagen = _imagen;
    }

    public int get_pos() {
        return _pos;
    }

    public void set_pos(int _pos) {
        this._pos = _pos;
    }

    public String get_titulo() {
        return _titulo;
    }

    public void set_titulo(String _titulo) {
        this._titulo = _titulo;
    }

    public String get_uri() {
        return _uri;
    }

    public void set_uri(String _uri) {
        this._uri = _uri;
    }

    public String get_estado() {
        return _estado;
    }

    public void set_estado(String _estado) {
        this._estado = _estado;
    }

    public int get_nBicis() {
        return _nBicis;
    }

    public void set_nBicis(int _nBicis) {
        this._nBicis = _nBicis;
    }

    public int get_nAnclajes() {
        return _nAnclajes;
    }

    public void set_nAnclajes(int _nAnclajes) {
        this._nAnclajes = _nAnclajes;
    }

    public String get_fechaUltimaMod() {
        return _fechaUltimaMod;
    }

    public void set_fechaUltimaMod(String _fechaUltimaMod) {
        this._fechaUltimaMod = _fechaUltimaMod;
    }
}
