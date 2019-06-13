package com.example.lista_compras;

import android.content.ContentValues;
import android.database.Cursor;

public class Categorias {

    public long id;

    public String Nome_da_categoria;

    public String Tipo_de_produto;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome_da_categoria() {
        return Nome_da_categoria;
    }

    public void setNome_da_categoria(String nome_da_categoria) {
        Nome_da_categoria = nome_da_categoria;
    }

    public String getTipo_de_produto() {
        return Tipo_de_produto;
    }

    public void setTipo_de_produto(String tipo_de_produto) {
        Tipo_de_produto = tipo_de_produto;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();


        valores.put(BdTableCategorias.NOME_CATEGORIA, Nome_da_categoria);
        valores.put(BdTableCategorias.TIPO_PRODUTO,Tipo_de_produto);



        return valores;
    }

    public static Categorias fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableCategorias._ID)
        );

        String Nome_da_categoria = cursor.getString(
                cursor.getColumnIndex(BdTableCategorias.NOME_CATEGORIA)
        );

        String Tipo_de_produto = cursor.getString(
                cursor.getColumnIndex(BdTableCategorias.TIPO_PRODUTO)
        );




        Categorias Categorias = new Categorias();

        Categorias.setId(id);
        Categorias.setNome_da_categoria(Nome_da_categoria);
        Categorias.setTipo_de_produto(Tipo_de_produto);


        return Categorias;

    }
}



