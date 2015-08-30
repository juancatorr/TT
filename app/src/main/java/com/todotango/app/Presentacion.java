package com.todotango.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.todotango.clases.ClaseCancion;
import com.todotango.parser.ParserSax;

import java.util.ArrayList;

/**
 * Created by Juanca on 13/8/15.
 */
public class Presentacion extends Activity {

     ArrayList<ClaseCancion> canciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.presentacion);
        CargarXmlTask tarea = new CargarXmlTask();
        tarea.execute();

    }

    private class CargarXmlTask extends AsyncTask<String,Integer,Boolean> {

        ParserSax parserSax;

        protected Boolean doInBackground(String... params) {

            try {

                parserSax = new ParserSax("http://media.todotango.com/xml/playlist.xml");
                canciones = parserSax.parse();
                Thread.sleep(3500);
                return true;

            } catch (InterruptedException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean result) {

            ClaseComunicador.setObjeto(canciones);
            Intent mostrarLista = (Intent) new Intent(Presentacion.this, MainActivity.class);
            startActivity(mostrarLista);
        }
    }
}
