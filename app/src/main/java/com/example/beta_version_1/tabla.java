package com.example.beta_version_1;

import android.provider.BaseColumns;

public class tabla implements BaseColumns {
    public static final String nom_tabla="usuarios";
    public static final String col1="id";
    public static final String col2="usuario";
    public static final String col3="pass";
    public static final String col4="nombre";
    public static final String col5="apellido";
    public static final String col6="fecha";
    public static final String col7="correo";

    private static final String SQL_DELETE_ENTRIES ="DROP TABLE IF EXISTS " + nom_tabla;
    private static  final String tabla="create table"+nom_tabla+"("+
            col1+"integer primary key AUTOINCREMENT,"+
            col2+"text(30) unique"+
            col3+"text(30)"+
            col4+"text(30)"+
            col5+"text(30)"+
            col6+"text(30)"+
            col7+"text(30)"+");";

}
