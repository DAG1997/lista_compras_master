package com.example.lista_compras.BD;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import com.example.lista_compras.BD.BdTableCategorias;

public class BdTableListaProdutos implements BaseColumns {
    public static final String NOME_TABELA = "LISTA_PRODUTOS";
    public static final String NOME_PRODUTO = "nome_do_produto";
    public static final String QUANTIDADE = "quantidade";
    public static final String CAMPO_CATEGORIA = "categoria";

    public static final String ALIAS_NOME_CATEGORIA = "nome_categoria";
    public static final String CAMPO_NOME_CATEGORIA = BdTableCategorias.NOME_TABELA + "." + BdTableCategorias.NOME_CATEGORIA + " AS " + ALIAS_NOME_CATEGORIA; // tabela de categorias (só de leitura)

    public static final String[] TODAS_COLUNAS = new String[]{NOME_TABELA + "." +_ID, QUANTIDADE, NOME_PRODUTO,CAMPO_CATEGORIA,CAMPO_NOME_CATEGORIA};
    public SQLiteDatabase db;
    public BdTableListaProdutos(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        NOME_PRODUTO + " TEXT NOT NULL," +
                        QUANTIDADE + " INTEGER NOT NULL," +
                        CAMPO_CATEGORIA + " TEXT NOT NULL,"+
                        "FOREIGN KEY (" + CAMPO_CATEGORIA + ") REFERENCES " + BdTableCategorias.NOME_TABELA + "(" + BdTableCategorias._ID + ")" +
                        ")"





        );
    }
    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        String colunasSelect = TextUtils.join(",", columns);

        String sql = "SELECT " + colunasSelect + " FROM " +
                NOME_TABELA + " INNER JOIN " + BdTableCategorias.NOME_TABELA + " WHERE " + NOME_TABELA + "." + CAMPO_CATEGORIA + "=" + BdTableCategorias.NOME_TABELA + "." + BdTableCategorias._ID
                ;

        if (selection != null) {
            sql += " AND " + selection;
        }

        Log.d("Tabela Livros", "query: " + sql);

        return db.rawQuery(sql, selectionArgs);
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



