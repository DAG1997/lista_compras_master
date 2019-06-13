package com.example.lista_compras;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.android.material.snackbar.Snackbar;

public class alterar_compras_efetuadas extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSO_LOADER_CATEGORIAS = 0;

    private EditText editTextNome_da_categoria;
    private EditText editTextTipo_do_produto;


    private Categorias categorias = null;

    private Uri enderecoCategoriasEditar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_compras_efetuadas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNome_da_categoria = (EditText) findViewById(R.id.editTextAlterar_nome_da_categoria);
        editTextTipo_do_produto = (EditText) findViewById(R.id.editTextAlterar_tipo_de_produto);


        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);


        Intent intent = getIntent();

        long idCategoria = intent.getLongExtra(Categoria.ID_CATEGORIAS, -1);

        if (idCategoria == -1) {
            Toast.makeText(this, " Não foi possível reconhcer a categoria pretendida", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoCategoriasEditar = Uri.withAppendedPath(Compras_Efetuadas_ContentProvider.ENDERECO_CATEGORIAS, String.valueOf(idCategoria));

        Cursor cursor = getContentResolver().query(enderecoCategoriasEditar, BdTableListaProdutos.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Não foi possível reconhecer a categoria pretendida", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        categorias = categorias.fromCursor(cursor);

        editTextNome_da_categoria.setText(categorias.getNome_da_categoria());
        editTextTipo_do_produto.setText(String.valueOf(categorias.getTipo_de_produto()));



    }

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
        String nome_da_categoria = editTextNome_da_categoria.getText().toString();

        if(nome_da_categoria.trim().isEmpty()){
            editTextNome_da_categoria.setError("Preencha o espaço por favor!");
            return;
        }


        String tipo_de_produto = editTextTipo_do_produto.getText().toString();

        if (tipo_de_produto.trim().isEmpty()){
            editTextTipo_do_produto.setError("Preencha o espaço por favor!");
            return;
        }


        categorias.setNome_da_categoria(nome_da_categoria);
        categorias.setTipo_de_produto(tipo_de_produto);


        try {
            getContentResolver().update(enderecoCategoriasEditar, categorias.getContentValues(), null, null);

            Toast.makeText(this, "Categoria guardada com sucesso!!!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNome_da_categoria,"Não foi possível criar a categoria!!!", Snackbar.LENGTH_LONG).show();


            e.printStackTrace();
        }
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, Compras_Efetuadas_ContentProvider.ENDERECO_CATEGORIAS, BdTableCategorias.TODAS_COLUNAS, null, null, BdTableCategorias.NOME_CATEGORIA
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
