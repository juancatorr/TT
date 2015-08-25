package com.juanca.todotango.parser;

import com.com.juanca.todotangoradio.clases.ClaseCancion;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Juanca on 12/8/15.
 */
public class ParserSax {

    private URL musicaUrl;

    public ParserSax(String url){
        try{
            this.musicaUrl = new URL(url);
        }catch (MalformedURLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ClaseCancion> parse(){

        SAXParserFactory factory = SAXParserFactory.newInstance();

        try{
            SAXParser parser = factory.newSAXParser();
            HandlerMusica handler = new HandlerMusica();
            parser.parse(this.getInputStream(),handler);
            return handler.getCanciones();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private InputStream getInputStream(){
        try {
            return musicaUrl.openConnection().getInputStream();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
