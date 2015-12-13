package com.example.acdat.ejercicios_xml;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdaptadorBizis extends ArrayAdapter<Estacion> {

    LayoutInflater _inflater;
    ViewHolder _holder;
    Context _contexto;
    public AdaptadorBizis(Context context, int resource, ArrayList<Estacion> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        _inflater=inflater;
        _contexto = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = _inflater.inflate(R.layout.item, null);
            _holder = new ViewHolder();
            _holder.titulo = (TextView) convertView.findViewById(R.id.lestacion);
            _holder.bicis = (ImageView) convertView.findViewById(R.id.limagen);
            _holder.bicis.setFocusable(false);
            convertView.setTag(_holder);
        }
        else {
            _holder = (ViewHolder) convertView.getTag();
        }
        Estacion estacion = getItem(position);

        _holder.titulo.setText(estacion.get_titulo());
        Picasso.with(_contexto).load(estacion.get_imagen()).into(_holder.bicis);
        //setNotifyOnChange(true);
        return convertView;
    }

    class ViewHolder {
        TextView titulo;
        ImageView bicis;
    }
}
