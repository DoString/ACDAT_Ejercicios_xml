package com.example.acdat.ejercicios_xml;

/**
 * Created by juan on 13/12/2015.
 */
public class NoticiasGenericas {
    public static final String URL_ELPAIS = "http://ep00.epimg.net/rss/tags/ultimas_noticias.xml";
    public static final String URL_ELMUNDO = "http://estaticos.elmundo.es/elmundodeporte/rss/futbol.xml";
    public static final String URL_LINUX = "http://www.linux-magazine.com/rss/feed/lmi_news";
    public static final String URL_PCWORLD = "http://www.pcworld.com/index.rss";

    String _titulo;
    String _link;

    public String get_titulo() {
        return _titulo;
    }

    public void set_titulo(String _titulo) {
        this._titulo = _titulo;
    }

    public String get_link() {
        return _link;
    }

    public void set_link(String _link) {
        this._link = _link;
    }
}
