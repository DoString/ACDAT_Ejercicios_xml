package com.example.acdat.ejercicios_xml;

import android.graphics.Bitmap;

/**
 * Created by juan on 10/12/2015.
 */
public class Prediccion {
    private String fecha;
    private String tempMax;
    private String tempMin;
    private String imgNoche;
    private String imgAmanecer;
    private String imgAtardecer;
    private String imgAnochecer;


    public String getFecha()
    {
        return fecha;
    }

    public void setFecha(String fecha)
    {
        this.fecha = fecha;
    }

    public String getTempMax()
    {
        return tempMax;
    }

    public void setTempMax(String temperaturaMaxima)
    {
        this.tempMax = temperaturaMaxima;
    }

    public String getTempMin()
    {
        return tempMin;
    }

    public void setTempMin(String temperaturaMinima)
    {
        this.tempMin = temperaturaMinima;
    }

    public String getImgNoche()
    {
        return imgNoche;
    }

    public void setImgNoche(String imgEstadoCieloNoche)
    {
        this.imgNoche = imgEstadoCieloNoche;
    }

    public String getImgAmanecer()
    {
        return imgAmanecer;
    }

    public void setImgAmanecer(String imgEstadoCieloAmanecer)
    {
        this.imgAmanecer = imgEstadoCieloAmanecer;
    }

    public String getImgAtardecer()
    {
        return imgAtardecer;
    }

    public void setImgAtardecer(String imgEstadoCieloAtardecer)
    {
        this.imgAtardecer = imgEstadoCieloAtardecer;
    }

    public String getImgAnochecer()
    {
        return imgAnochecer;
    }

    public void setImgAnochecer(String imgEstadoCieloAnochecer)
    {
        this.imgAnochecer = imgEstadoCieloAnochecer;
    }
}
