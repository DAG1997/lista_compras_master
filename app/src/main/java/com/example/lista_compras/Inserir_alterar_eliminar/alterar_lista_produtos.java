package com.example.lista_compras.Inserir_alterar_eliminar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.lista_compras.BD.BdTableCategorias;
import com.example.lista_compras.BD.BdTableListaProdutos;
import com.example.lista_compras.BD.Compras_Efetuadas_ContentProvider;
import com.example.lista_compras.ClassesBd.ListaProdutos;
import com.example.lista_compras.Main_Recyclers.Produtos;
import com.example.lista_compras.R;
import com.google.android.material.snackbar.Snackbar;

public class alterar_lista_produtos extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSO_LOADER_LISTA_PRODUTOS = 0;

    private EditText editTextNome_do_produto;
    private EditText editTextQuantidade;
   // private EditText editTextCategoria;
    private Spinner spinnerCategoria;

    private ListaProdutos listaProdutos = null;

    //private boolean categoriasCarregadas = false;
    //private boolean categoriaAtualizada = false;

    private Uri enderecoListaProdutosEditar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_lista_produtos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNome_do_produto = (EditText) findViewById(R.id.editTextAlterar_nome_do_produto_);
        editTextQuantidade = (EditText) findViewById(R.id.editTextAlterar_quantidade);
        //editTextCategoria = (EditText) findViewById(R.id.editTextAlterar_nome_da_categoria) ;
        spinnerCategoria = findViewById(R.id.spinnerCategorias);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_LISTA_PRODUTOS, null, this);


        Intent intent = getIntent();

        long idListaProdutos = intent.getLongExtra(Produtos.ID_PRODUTOS, -1);

        if (idListaProdutos == -1) {
            Toast.makeText(this, "Não foi possível reconhcer a lista de produtos", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoListaProdutosEditar = Uri.withAppendedPath(Compras_Efetuadas_ContentProvider.ENDEREÇO_LISTAPRODUTOS, String.valueOf(idListaProdutos));

        Cursor cursor = getContentResolver().query(enderecoListaProdutosEditar, BdTableListaProdutos.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Não foi possível reconhecer a lista de produtos", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        listaProdutos = listaProdutos.fromCursor(cursor);

        editTextNome_do_produto.setText(listaProdutos.getNome_do_produto());
        editTextQuantidade.setText(String.valueOf(listaProdutos.getQuantidade()));
        //editTextCategoria.setText(String.valueOf(listaProdutos.getCategoria()));
        /*editTextCategoria.setText(listaProdutos.getCategoria());*/

        //atualizaCategoriaSelecionada();


    }

    /*private void atualizaCategoriaSelecionada() {
        if(!categoriasCarregadas) return;
        if(categoriaAtualizada) return;

        for (int i = 0; i < spinnerCategoria.getCount(); i++){
            if(spinnerCategoria.getItemIdAtPosition(i) == listaProdutos.getCategoria()){
                spinnerCategoria.setSelection(i);
                break;
            }
        }

        categoriaAtualizada = true;
    }*/

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_LISTA_PRODUTOS, null, this);

        super.onResume();
    }

    /*private void mostraCategoriaSpinner(Cursor cursorCategorias) {
        SimpleCursorAdapter adaptadorCategorias = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorCategorias,
                new String[]{BdTableCategorias.NOME_CATEGORIA},
                new int[]{android.R.id.text1}
        );
        spinnerCategoria.setAdapter(adaptadorCategorias);
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guardar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
            editTextNome_do_produto.setError("Preencha o espaço vazio por favor!");
            return;
        }


        int quantidade;

        String strQuantidade = editTextQuantidade.getText().toString();

        if (strQuantidade.trim().isEmpty()){
            editTextQuantidade.setError("Preencha o espaço vazio por favor!");
            return;
        }

        try {
            quantidade = Integer.parseInt(strQuantidade);
        } catch (NumberFormatException e) {
            editTextQuantidade.setError("Número Inválido");
            return;
        }

        /*long categoria = editTextCategoria.getText().toString();

        if(categoria.trim().isEmpty()){
            editTextCategoria.setError("Preencha o espaço vazio por favor!");
            return;
        }*/

        //long idCategorias = spinnerCategoria.getSelectedItemId();

        listaProdutos.setNome_do_produto(nome_do_produto);
        listaProdutos.setQuantidade(quantidade);
        /*listaProdutos.setCategoria(categoria);*/
        //listaProdutos.setCategoria(idCategorias);

        try {
            getContentResolver().update(enderecoListaProdutosEditar, listaProdutos.getContentValues(), null, null);

            Toast.makeText(this, "Lista de produtos guardada com sucesso!!!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNome_do_produto,"Não foi possível criar a lista de produtos!!!", Snackbar.LENGTH_LONG).show();


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
        //mostraCategoriaSpinner(data);
        //categoriasCarregadas = true;
        //atualizaCategoriaSelecionada();


    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        //categoriasCarregadas = false;
        //categoriaAtualizada = false;
        //mostraCategoriaSpinner(null);

    }
}
