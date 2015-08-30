package com.todotango.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.todotango.clases.ClaseCancion;


import java.util.ArrayList;

/**
 * Created by Juanca on 3/8/15.
 */
public class CancionAdapter extends BaseAdapter {


    private ArrayList<ClaseCancion> songs;
    private LayoutInflater songInf;
    private Context contexto;

    public CancionAdapter(Context c, ArrayList<ClaseCancion> theSongs){
        songs=theSongs;
        songInf=LayoutInflater.from(c);
        contexto = c;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return songs.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //map to song layout
        View cancion_layout = (LinearLayout)songInf.inflate(R.layout.cancion, parent, false);

        //get title and artist views
        TextView songView = (TextView)cancion_layout.findViewById(R.id.song_title);
        TextView artistView = (TextView)cancion_layout.findViewById(R.id.song_artist);
        TextView letra = (TextView)cancion_layout.findViewById(R.id.song_letra);
        TextView orquesta = (TextView)cancion_layout.findViewById(R.id.song_orquesta);
        TextView musica = (TextView)cancion_layout.findViewById(R.id.song_musica);

        TextView descView = (TextView)cancion_layout.findViewById(R.id.song_descripcion);

        //get song using position
        ClaseCancion currSong = songs.get(position);

        //get title and artist strings
        songView.setText(currSong.getTitulo());
        artistView.setText(contexto.getResources().getString(R.string.canta) + ": " + currSong.getCanta());
        letra.setText(contexto.getResources().getString(R.string.lentra) + ": " + currSong.getLetra());
        orquesta.setText(contexto.getResources().getString(R.string.orquesta) + ": " + currSong.getOrquesta());
        musica.setText(contexto.getResources().getString(R.string.musica) + ": " + currSong.getMusica());
        descView.setText(contexto.getResources().getString(R.string.lentra) + ": " + currSong.getLetra() + " " +
                contexto.getResources().getString(R.string.orquesta) + ": " + currSong.getCanta() + " " +
        contexto.getResources().getString(R.string.musica) + ": " + currSong.getMusica());


        //set position as tag



        return cancion_layout;
    }

}