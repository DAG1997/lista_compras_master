package com.example.lista_compras;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableDinheiroGasto implements BaseColumns {
    public static final String NOME_TABELA = "Dinheiro_gasto";

    public static final String MONTANTE_GASTO = "Dinheiro_dispendido";
    public static final String DIA = "Dia_da_semana";



    public static final String[] TODAS_COLUNAS = new String[]{_ID, MONTANTE_GASTO, DIA};


    public SQLiteDatabase db;


    public BdTableDinheiroGasto(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MONTANTE_GASTO + " TEXT NOT NULL," +
                        DIA + " TEXT NOT NULL" +



                        ")"



        );
    }


    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }

    public int update(ContentValues values, String whereClause, String [] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}







