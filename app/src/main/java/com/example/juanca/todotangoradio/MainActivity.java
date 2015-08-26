package com.example.juanca.todotangoradio;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.com.juanca.todotangoradio.clases.ClaseCancion;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private ServicioMusica servicioMusica;
    private ArrayList<ClaseCancion> canciones;
    private ListView view_lista_canciones;
    private int songId;
    private SeekBar sb;
    private boolean musicBound=false;
    private Intent playIntent;
    private boolean enReproduccion = true;
    private TextView cancionTextView;
    private TextView artistaTextView;
    private TextView letraTextView;
    private TextView orquestaTextView;
    private TextView musicaTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cancionTextView = (TextView) findViewById(R.id.song_title);
        artistaTextView = (TextView) findViewById(R.id.song_artist);
        letraTextView = (TextView) findViewById(R.id.song_letra);
        orquestaTextView = (TextView) findViewById(R.id.song_orquesta);
        musicaTextView = (TextView) findViewById(R.id.song_musica);

//        view_lista_canciones = (ListView) this.findViewById(R.id.list_view_temas);
//        sb = (SeekBar) findViewById(R.id.seekBar);

        canciones =(ArrayList<ClaseCancion>) ClaseComunicador.getObjeto();

//        final CancionAdapter songAdt = new CancionAdapter(this, canciones);
//        view_lista_canciones.setAdapter(songAdt);


//        view_lista_canciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                servicioMusica.setSong(position);
//                servicioMusica.reproducirCancion();
//
//                if(((Button) findViewById(R.id.button_play)).getText() == getString(R.string.play)){
//                    ((Button) findViewById(R.id.button_play)).setText(R.string.pausa);
//                }
//            }
//        });


        ((ImageButton) findViewById(R.id.button_ff)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if (((ImageButton) findViewById(R.id.button_play)).getText() == getString(R.string.play)) {
                  //  ((ImageButton) findViewById(R.id.button_play)).setText(R.string.pausa);
                //}
                servicioMusica.reproducirNext();
            }
        });

        ((ImageButton) findViewById(R.id.button_rew)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if (((Button) findViewById(R.id.button_play)).getText() == getString(R.string.play)) {
                  //  ((Button) findViewById(R.id.button_play)).setText(R.string.pausa);
                //}
                servicioMusica.reproducirPrev();
            }
        });

        ((ImageButton) findViewById(R.id.button_play)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (enReproduccion) {
                  //  ((Button) findViewById(R.id.button_play)).setText(R.string.pausa);
                    servicioMusica.pausar();
                    enReproduccion=false;

                } else {
                    servicioMusica.reanudar();
                    enReproduccion=true;
                    //((Button) findViewById(R.id.button_play)).setText(R.string.play);
                }
            }
        });

    }

    private ServiceConnection coneccionMusica = new ServiceConnection()

    {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServicioMusica.MusicaBinder binder = (ServicioMusica.MusicaBinder)service;
            servicioMusica = binder.getService();
            servicioMusica.setList(canciones);
            musicBound=true;

            servicioMusica.setSong(0);
            servicioMusica.reproducirCancion();
            enReproduccion=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound=false;
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null){
            playIntent = new Intent(this, ServicioMusica.class);
            bindService(playIntent,coneccionMusica, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }

        MyReceiver myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("MY_ACTION");
        registerReceiver(myReceiver, intentFilter);
    }


    @Override
    protected void onDestroy() {
        stopService(playIntent);
        servicioMusica=null;
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static void metodo(){

    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            int datapassed = arg1.getIntExtra("DATAPASSED", 0);

            establecerCancion(datapassed);

        }

    }

    private void establecerCancion (int songId){

        //get song using position
        ClaseCancion currSong = canciones.get(songId);

        //get title and artist strings
        cancionTextView.setText(currSong.getTitulo());
        artistaTextView.setText(getString(R.string.canta) + ": " + currSong.getCanta());
        letraTextView.setText(getString(R.string.lentra) + ": " + currSong.getLetra());
        orquestaTextView.setText(getString(R.string.orquesta) + ": " + currSong.getOrquesta());
        musicaTextView.setText(getString(R.string.musica) + ": " + currSong.getMusica());
    }
}
