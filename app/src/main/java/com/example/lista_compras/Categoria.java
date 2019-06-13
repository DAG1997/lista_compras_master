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



public class Categoria extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String ID_CATEGORIAS = "ID_CATEGORIAS";
    private  static  final int ID_CURSO_LOADER_CATEGORIAS = 0;

    private Adaptador_Categorias adaptadorCategorias;
    private RecyclerView recyclerViewCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras_efetuadas);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);

        recyclerViewCategorias = findViewById(R.id.recyclerViewCategorias);
        adaptadorCategorias = new Adaptador_Categorias(this);
        recyclerViewCategorias.setAdapter(adaptadorCategorias);
        recyclerViewCategorias.setLayoutManager(new LinearLayoutManager(this));

        //Botao Voltar

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);
        super.onResume();
    }

    private Menu menu;

    public void atualizaOpcoesMenu() {
        Categorias categorias = adaptadorCategorias.getCategoriaSelecionada();

        boolean mostraAlterarEliminar = (categorias != null);

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
                Intent intent = new Intent (this, inserir_compras_efetuadas.class);
                startActivity(intent);
                return true;

            case R.id.itemEditar:
                Intent intent1 = new Intent (this, alterar_compras_efetuadas.class);
                intent1.putExtra(ID_CATEGORIAS, adaptadorCategorias.getCategoriaSelecionada().getId());
                startActivity(intent1);
                return true;

            case R.id.itemEliminar:
                Intent intent2 = new Intent (this, eliminar_compras_efetuadas.class);
                intent2.putExtra(ID_CATEGORIAS, adaptadorCategorias.getCategoriaSelecionada().getId());
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(this, Compras_Efetuadas_ContentProvider.ENDERECO_CATEGORIAS, BdTableCategorias.TODAS_COLUNAS, null, null, BdTableCategorias.NOME_CATEGORIA);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorCategorias.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorCategorias.setCursor(null);

    }


}

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras_efetuadas);
        Button btn = (Button) findViewById(R.id.button7);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openinserir_compras_efetuadas();

            }
        });

        Button btn2 = (Button) findViewById(R.id.button8);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openalterar_compras_efetuadas();

            }
        });

        Button btn3 = (Button) findViewById(R.id.button9);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openeliminar_compras_efetuadas();

            }
        });
    }

    public void openinserir_compras_efetuadas(){
        Intent intent = new Intent(this, inserir_compras_efetuadas.class);
        startActivity(intent);
    }

    public void openalterar_compras_efetuadas(){
        Intent intent = new Intent(this, alterar_compras_efetuadas.class);
        startActivity(intent);
    }

    public void openeliminar_compras_efetuadas(){
        Intent intent = new Intent(this, eliminar_compras_efetuadas.class);
        startActivity(intent);
    }




}*/




