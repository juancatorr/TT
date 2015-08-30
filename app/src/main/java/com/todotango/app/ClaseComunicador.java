package com.todotango.app;

/**
 * Created by Juanca on 14/8/15.
 */
 class ClaseComunicador {

    private static Object objeto = null;

    public static void setObjeto(Object newObjeto) {
        objeto = newObjeto;
    }

    public static Object getObjeto() {
        return objeto;
    }
}
