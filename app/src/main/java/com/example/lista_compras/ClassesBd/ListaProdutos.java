package com.example.lista_compras.ClassesBd;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.lista_compras.BD.BdTableListaProdutos;

public class ListaProdutos {

    private long id;
    private String nome_do_produto;
    private int quantidade;
    private long categoria;
    private String nomeCategoria;


    public String getNomeCategoria() {
        return nomeCategoria;
    }
    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

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

    public long getCategoria() {
        return categoria;
    }

    public void setCategoria(long categoria) {
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

        long categoria = cursor.getLong(
                cursor.getColumnIndex(BdTableListaProdutos.CAMPO_CATEGORIA)
        );

        String nomeCategoria = cursor.getString(
                cursor.getColumnIndex(BdTableListaProdutos.ALIAS_NOME_CATEGORIA)
        );


        ListaProdutos listaProdutos = new ListaProdutos();

        listaProdutos.setId(id);
        listaProdutos.setNome_do_produto(nome_do_produto);
        listaProdutos.setQuantidade(quantidade);
        listaProdutos.setCategoria(categoria);
        listaProdutos.nomeCategoria= nomeCategoria;


        return listaProdutos;

    }
}

