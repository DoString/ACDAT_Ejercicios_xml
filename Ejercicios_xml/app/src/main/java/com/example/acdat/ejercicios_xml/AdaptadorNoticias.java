package com.example.acdat.ejercicios_xml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by juan on 13/12/2015.
 */
public class AdaptadorNoticias extends ArrayAdapter<NoticiasGenericas> {
    ViewHolder _holder;
    LayoutInflater _inflater;
    public AdaptadorNoticias(Context context, int resource, ArrayList<NoticiasGenericas> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        _inflater = inflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = _inflater.inflate(R.layout.titular_item, null);
            _holder = new ViewHolder();
            _holder._titular = (TextView) convertView.findViewById(R.id.titular);
            convertView.setTag(_holder);
        }
        else {
            _holder = (ViewHolder) convertView.getTag();
        }

        NoticiasGenericas n = getItem(position);
        _holder._titular.setText(n.get_titulo());

        return convertView;
    }

    class ViewHolder {
        TextView _titular;
    }
}
