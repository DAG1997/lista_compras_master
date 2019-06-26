package com.example.lista_compras.Inserir_alterar_eliminar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lista_compras.BD.BdTableListaProdutos;
import com.example.lista_compras.BD.Compras_Efetuadas_ContentProvider;
import com.example.lista_compras.ClassesBd.ListaProdutos;
import com.example.lista_compras.Main_Recyclers.Produtos;
import com.example.lista_compras.R;


public class eliminar_lista_produtos extends AppCompatActivity {
    private Uri enderecoListaProdutosApagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_lista_produtos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewNome_do_produto = (TextView) findViewById(R.id.textViewEliminar_nome_do_produto);
        TextView textViewQuantidade = (TextView) findViewById(R.id.textViewEliminar_quantidade);
        TextView textViewCategoria = (TextView) findViewById(R.id.textViewEliminar_categoria);

        Intent intent = getIntent();
        long idProdutos = intent.getLongExtra(Produtos.ID_PRODUTOS, -1);
        if (idProdutos == -1) {
            Toast.makeText(this, "Não foi possível eliminar o produto", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoListaProdutosApagar = Uri.withAppendedPath(Compras_Efetuadas_ContentProvider.ENDEREÇO_LISTAPRODUTOS, String.valueOf(idProdutos));

        Cursor cursor = getContentResolver().query(enderecoListaProdutosApagar, BdTableListaProdutos.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Não foi possível eliminar o produto", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        ListaProdutos listaProdutos = ListaProdutos.fromCursor(cursor);

        textViewNome_do_produto.setText(listaProdutos.getNome_do_produto());
        textViewQuantidade.setText(String.valueOf(listaProdutos.getQuantidade()));
        /*textViewCategoria.setText(listaProdutos.getCategoria());*/
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
        int ProdutosApagados = getContentResolver().delete(enderecoListaProdutosApagar, null, null);

        if (ProdutosApagados == 1) {
            Toast.makeText(this, "Produto eliminado com sucesso!!!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Não foi possível eliminar o produto", Toast.LENGTH_LONG).show();
        }
    }
}
