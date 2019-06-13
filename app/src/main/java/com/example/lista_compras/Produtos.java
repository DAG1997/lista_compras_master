package com.example.lista_compras;
import android.content.Intent;
import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class Produtos extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String ID_PRODUTOS = "ID_PRODUTOS";
    private  static  final int ID_CURSO_LOADER_PRODUTOS = 0;

    private Adaptador_Lista_Compras adaptador_lista_compras;
    private RecyclerView recyclerViewProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botao_carregado);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_PRODUTOS, null, this);

        recyclerViewProdutos = findViewById(R.id.recyclerViewProdutos);
        adaptador_lista_compras = new Adaptador_Lista_Compras(this);
        recyclerViewProdutos.setAdapter(adaptador_lista_compras);
        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(this));

        //Botao Voltar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_PRODUTOS, null, this);
        super.onResume();
    }

    private Menu menu;

    public void atualizaOpcoesMenu() {
        ListaProdutos autor = adaptador_lista_compras.getListaProdutosSelecionada();

        boolean mostraAlterarEliminar = (autor != null);

        menu.findItem(R.id.itemEditar).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.itemEliminar).setVisible(mostraAlterarEliminar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_atividades, menu);

        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.itemAdicionar:
                Intent intent = new Intent (this, Inserir_lista_produtos.class);
                startActivity(intent);
                return true;

            case R.id.itemEditar:
                Intent intent1 = new Intent (this, alterar_lista_produtos.class);
                intent1.putExtra(ID_PRODUTOS, adaptador_lista_compras.getListaProdutosSelecionada().getId());
                startActivity(intent1);
                return true;

            case R.id.itemEliminar:
                Intent intent2 = new Intent (this, eliminar_lista_produtos.class);
                intent2.putExtra(ID_PRODUTOS, adaptador_lista_compras.getListaProdutosSelecionada().getId());
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(this, Compras_Efetuadas_ContentProvider.ENDEREÇO_LISTAPRODUTOS, BdTableListaProdutos.TODAS_COLUNAS, null, null, BdTableListaProdutos.NOME_PRODUTO);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptador_lista_compras.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptador_lista_compras.setCursor(null);

    }


}


/*import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class BotaoCarregado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botao_carregado);
        Button btn = (Button) findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openinserir_lista_produtos();

            }
        });

        Button btn2 = (Button) findViewById(R.id.button5);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openalterar_lista_produtos();

            }
        });

        Button btn3 = (Button) findViewById(R.id.button6);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openeliminar_lista_produtos();

            }
        });
    }

    public void openinserir_lista_produtos(){
        Intent intent = new Intent(this, Inserir_lista_produtos.class);
        startActivity(intent);
    }

    public void openalterar_lista_produtos(){
        Intent intent = new Intent(this, alterar_lista_produtos.class);
        startActivity(intent);
    }

    public void openeliminar_lista_produtos(){
        Intent intent = new Intent(this, eliminar_lista_produtos.class);
        startActivity(intent);
    }




    }

*/


