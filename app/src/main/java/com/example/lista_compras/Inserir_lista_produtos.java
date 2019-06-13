package com.example.lista_compras;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Inserir_lista_produtos extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_CURSO_LOADER_LISTA_PRODUTOS = 0;

    private EditText editTextNome_do_produto;
    private EditText editTextQuantidade;
    private EditText editTextCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_lista_produtos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNome_do_produto = (EditText) findViewById(R.id.editTextNome_do_produto);
        editTextQuantidade = (EditText) findViewById(R.id.editTextQuantidade);
        editTextCategoria = (EditText) findViewById(R.id.editTextNome_do_produto);
    }

    @Override
    protected void onResume(){
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_LISTA_PRODUTOS, null, this);

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_guardar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.item_guardar) {
            guardar();
            return true;
        } else if (id == R.id.item_cancelar) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void guardar() {
        String nome_do_produto = editTextNome_do_produto.getText().toString();

        if(nome_do_produto.trim().isEmpty()){
            editTextNome_do_produto.setError("Preencha o espaço por favor!");
            return;
        }

        int quantidade;

        String strQuantidade = editTextNome_do_produto.getText().toString();

        if (strQuantidade.trim().isEmpty()){
            editTextQuantidade.setError("Preencha o espaço por favor!");
            return;
        }

        try {
            quantidade = Integer.parseInt(strQuantidade);
        } catch (NumberFormatException e) {
            editTextQuantidade.setError("Número Inválido!!");
            return;
        }

        String categoria = editTextCategoria.getText().toString();

        if(categoria.trim().isEmpty()){
            editTextCategoria.setError("Preencha o espaço por favor!");
            return;
        }

        ListaProdutos listaProdutos = new ListaProdutos();


        listaProdutos.setNome_do_produto(nome_do_produto);
        listaProdutos.setQuantidade(quantidade);
        listaProdutos.setCategoria(categoria);

        try {
            getContentResolver().insert(Compras_Efetuadas_ContentProvider.ENDEREÇO_LISTAPRODUTOS, listaProdutos.getContentValues());

            Toast.makeText(this, "Lista Produtos guardada com sucesso!!!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNome_do_produto,"Não foi possivel criar uma lista de produtos", Snackbar.LENGTH_LONG).show();


            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, Compras_Efetuadas_ContentProvider.ENDEREÇO_LISTAPRODUTOS, BdTableListaProdutos.TODAS_COLUNAS, null, null, BdTableListaProdutos.NOME_PRODUTO
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}