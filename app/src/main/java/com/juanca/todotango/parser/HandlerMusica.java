package com.juanca.todotango.parser;

import com.com.juanca.todotangoradio.clases.ClaseCancion;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;

/**
 * Created by Juanca on 10/8/15.
 */
public class HandlerMusica extends DefaultHandler{

    private ArrayList<ClaseCancion> canciones;
    private ClaseCancion cancionActual;
    private StringBuilder sbTexto;


    @Override
    public void startDocument() throws SAXException {

        super.startDocument();

        canciones = new ArrayList<ClaseCancion>();
        sbTexto = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        super.startElement(uri, localName, qName, attributes);

        if (localName.equals("Tema")){
            cancionActual = new ClaseCancion();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if (this.cancionActual != null)
            sbTexto.append(ch, start,length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        super.endElement(uri, localName, qName);

        if (this.cancionActual != null){

            if (localName.equals("Id")){
                cancionActual.setId(sbTexto.toString().trim().replace("\n", ""));
            }else if(localName.equals("Titulo")){
                cancionActual.setTitulo(sbTexto.toString().trim().replace("\n", ""));
            }else if (localName.equals("Descripcion")){
                cancionActual.setDescripcion(sbTexto.toString().trim().replace("\n", ""));
            }else if(localName.equals("MediaMP3")){
                cancionActual.setRuta(sbTexto.toString().trim().replace("\n", ""));
            }else if(localName.equals("Musica")){
                cancionActual.setMusica(sbTexto.toString().trim().replace("\n", ""));
            }else if(localName.equals("Letra")){
                cancionActual.setLetra(sbTexto.toString().trim().replace("\n", ""));
            }else if(localName.equals("Canta")){
                cancionActual.setCanta(sbTexto.toString().trim().replace("\n", ""));
            }else if(localName.equals("Orquesta")){
                cancionActual.setOrquesta(sbTexto.toString().trim().replace("\n", ""));
            }else if(localName.equals("Tema")){
                canciones.add(cancionActual);
            }

            sbTexto.setLength(0);

        }
    }

    public ArrayList<ClaseCancion> getCanciones() {

        return canciones;
    }
}
