package com.example.juanca.todotangoradio;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;

import com.com.juanca.todotangoradio.clases.ClaseCancion;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Juanca on 17/8/15.
 */
public class ServicioMusica extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{

    private MediaPlayer reproductor;
    private int posicionCancion;
    private ArrayList<ClaseCancion> canciones;
    private  String nombreCancion="";
    private static final int NOTIFY_ID=1;

    private  final IBinder musicBinder = new MusicaBinder();

    @Override
    public void onCreate() {

        super.onCreate();
        posicionCancion=0;
        reproductor = new MediaPlayer();

        inicioReproductorMusica();

    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return musicBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {

        reproductor.stop();
        reproductor.release();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        reproducirNext();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();

        Intent notIntent = new Intent(this, MainActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this,0,notIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendInt)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(nombreCancion)
                .setOngoing(true)
                .setContentTitle(getString(R.string.todo_tango))
                .setContentText(nombreCancion);
        Notification notificacion = builder.build();

        startForeground(NOTIFY_ID, notificacion);

    }


    public void inicioReproductorMusica(){

       // reproductor.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        //reproductor.setAudioStreamType(AudioManager.STREAM_MUSIC);

        reproductor.setOnPreparedListener(this);
        reproductor.setOnCompletionListener(this);
        reproductor.setOnErrorListener(this);
    }

    public void setList(ArrayList<ClaseCancion> theSongs){

        canciones=theSongs;
    }
    public void reproducirCancion(){

        reproductor.reset();

        ClaseCancion cancion = canciones.get(posicionCancion);
        nombreCancion=cancion.getTitulo();

        try {
            reproductor.setDataSource(getApplicationContext(), Uri.parse(cancion.getRuta()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        reproductor.prepareAsync();
    }

    public class MusicaBinder extends Binder {
        ServicioMusica getService(){
            return ServicioMusica.this;
        }
    }

    public void go(){
        reproductor.start();
    }

    public void reproducirPrev(){
        posicionCancion--;

        if (posicionCancion < 0)
            posicionCancion=canciones.size()-1;

        reproducirCancion();
    }

    public void reproducirNext(){
        posicionCancion++;
        if (posicionCancion >= canciones.size())
            posicionCancion=0;

        reproducirCancion();
    }

    public void pausar(){
        reproductor.pause();
    }

    public  void reanudar(){
        reproductor.start();
    }
    public void setSong(int songIndex){
        posicionCancion=songIndex;
    }
}
