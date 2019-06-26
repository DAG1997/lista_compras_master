package com.example.lista_compras.Inserir_alterar_eliminar;


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

import com.example.lista_compras.BD.BdTableCategorias;
import com.example.lista_compras.BD.Compras_Efetuadas_ContentProvider;
import com.example.lista_compras.ClassesBd.Categorias;
import com.example.lista_compras.R;
import com.google.android.material.snackbar.Snackbar;





public class inserir_compras_efetuadas extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_CURSO_LOADER_CATEGORIAS = 0;

    private EditText editTextNome_da_Categoria;
    private EditText editTextTipo_de_produto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_compras_efetuadas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNome_da_Categoria = (EditText) findViewById(R.id.editTextAlterar_nome_da_categoria);
        editTextTipo_de_produto = (EditText) findViewById(R.id.editTextAlterar_tipo_de_produto);

    }

    @Override
    protected void onResume(){
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);

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
        String nome_da_categoria = editTextNome_da_Categoria.getText().toString();

        if(nome_da_categoria.trim().isEmpty()){
            editTextNome_da_Categoria.setError("Preencha o espaço por favor!");
            return;
        }


        String tipo_de_produto = editTextTipo_de_produto.getText().toString();

        if (tipo_de_produto.trim().isEmpty()){
            editTextTipo_de_produto.setError("Preencha o espaço por favor!");
            return;
        }



        Categorias categorias = new Categorias();


        categorias.setNome_da_categoria(nome_da_categoria);
        categorias.setTipo_de_produto(tipo_de_produto);


        try {
            getContentResolver().insert(Compras_Efetuadas_ContentProvider.ENDERECO_CATEGORIAS, categorias.getContentValues());

            Toast.makeText(this, "Categoria guardada com sucesso!!!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNome_da_Categoria,"Não foi possível criar uma categoria!!", Snackbar.LENGTH_LONG).show();


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


