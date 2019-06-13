package com.example.lista_compras;

import android.content.ContentValues;
import android.database.Cursor;

public class ListaProdutos {

    private long id;
    private String nome_do_produto;
    private int quantidade;
    private String categoria;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome_do_produto() {
        return nome_do_produto;
    }

    public void setNome_do_produto(String nome_do_produto) {
        this.nome_do_produto = nome_do_produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTableListaProdutos.NOME_PRODUTO, nome_do_produto);
        valores.put(BdTableListaProdutos.QUANTIDADE, quantidade);
        valores.put(BdTableListaProdutos.CAMPO_CATEGORIA,categoria);


        return valores;
    }

    public static ListaProdutos fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableListaProdutos._ID)
        );

        String nome_do_produto = cursor.getString(
                cursor.getColumnIndex(BdTableListaProdutos.NOME_PRODUTO)
        );

        int quantidade = cursor.getInt(
                cursor.getColumnIndex(BdTableListaProdutos.QUANTIDADE)
        );

        String categoria = cursor.getString(
                cursor.getColumnIndex(BdTableListaProdutos.CAMPO_CATEGORIA)
        );


        ListaProdutos listaProdutos = new ListaProdutos();

        listaProdutos.setId(id);
        listaProdutos.setNome_do_produto(nome_do_produto);
        listaProdutos.setQuantidade(quantidade);
        listaProdutos.setCategoria(categoria);


        return listaProdutos;

    }
}

