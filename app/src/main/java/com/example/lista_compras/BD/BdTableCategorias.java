package com.example.lista_compras.BD;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableCategorias implements BaseColumns {

    public static final String NOME_TABELA = "Categorias";
    public static final String NOME_CATEGORIA = "Nome";
    public static final String TIPO_PRODUTO = "Tipo";


    public static final String[] TODAS_COLUNAS = new String[]{_ID,  NOME_CATEGORIA,TIPO_PRODUTO};


    public SQLiteDatabase db;


    public BdTableCategorias(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        NOME_CATEGORIA + " TEXT NOT NULL, " +
                        TIPO_PRODUTO + " TEXT NOT NULL" +



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