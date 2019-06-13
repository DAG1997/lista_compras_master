package com.example.lista_compras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;



public class eliminar_compras_efetuadas extends AppCompatActivity {
    private Uri enderecoCategoriasApagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_compras_efetuadas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewNome_da_categoria = (TextView) findViewById(R.id.textViewEliminar_nome_da_categoria);
        TextView textViewTipo_de_produto = (TextView) findViewById(R.id.textViewEliminar_tipo_de_produto);

        Intent intent = getIntent();
        long idCategoria = intent.getLongExtra(Categoria.ID_CATEGORIAS, -1);
        if (idCategoria == -1) {
            Toast.makeText(this, "Não foi possível eliminar a categoria", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoCategoriasApagar = Uri.withAppendedPath(Compras_Efetuadas_ContentProvider.ENDERECO_CATEGORIAS, String.valueOf(idCategoria));

        Cursor cursor = getContentResolver().query(enderecoCategoriasApagar, BdTableCategorias.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Não foi possível eliminar a categoria", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Categorias categorias = Categorias.fromCursor(cursor);

        textViewNome_da_categoria.setText(categorias.getNome_da_categoria());
        textViewTipo_de_produto.setText(String.valueOf(categorias.getTipo_de_produto()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_eliminar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_eliminar) {
            eliminar();
            return true;
        } else if (id == R.id.action_cancelar) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void eliminar() {
        int CategoriasApagadss = getContentResolver().delete(enderecoCategoriasApagar, null, null);

        if (CategoriasApagadss == 1) {
            Toast.makeText(this, "Categoria eliminada com sucesso!!!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Não foi possível eliminar a categoria", Toast.LENGTH_LONG).show();
        }
    }
}
