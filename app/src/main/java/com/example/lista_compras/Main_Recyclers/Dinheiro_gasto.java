package com.example.lista_compras.Main_Recyclers;

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

import com.example.lista_compras.Adaptadores.Adaptador_DinheiroGasto;
import com.example.lista_compras.BD.BdTableDinheiroGasto;
import com.example.lista_compras.BD.Compras_Efetuadas_ContentProvider;
import com.example.lista_compras.ClassesBd.DinheiroGasto;
import com.example.lista_compras.R;
import com.example.lista_compras.Inserir_alterar_eliminar.alterar_dinheiro_gasto;
import com.example.lista_compras.Inserir_alterar_eliminar.eliminar_dinheiro_gasto;
import com.example.lista_compras.Inserir_alterar_eliminar.inserir_dinheiro_gasto;


public class Dinheiro_gasto extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String ID_DINHEIRO_GASTO = "ID_DINHEIRO_GASTO";
    private  static  final int ID_CURSO_LOADER_DINHEIRO_GASTO = 0;

    private Adaptador_DinheiroGasto adaptadorDinheiroGasto;
    private RecyclerView recyclerViewDinheiroGasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinheiro_gasto);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_DINHEIRO_GASTO, null, this);

        recyclerViewDinheiroGasto = findViewById(R.id.recyclerViewDinheiroGasto);
        adaptadorDinheiroGasto = new Adaptador_DinheiroGasto(this);
        recyclerViewDinheiroGasto.setAdapter(adaptadorDinheiroGasto);
        recyclerViewDinheiroGasto.setLayoutManager(new LinearLayoutManager(this));

        //Botao Voltar

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_DINHEIRO_GASTO, null, this);
        super.onResume();
    }

    private Menu menu;

    public void atualizaOpcoesMenu() {
        DinheiroGasto dinheiroGasto = adaptadorDinheiroGasto.getDinheiroGastoSelecionado();

        boolean mostraAlterarEliminar = (dinheiroGasto != null);

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
                Intent intent = new Intent (this, inserir_dinheiro_gasto.class);
                startActivity(intent);
                return true;

            case R.id.itemEditar:
                Intent intent1 = new Intent (this, alterar_dinheiro_gasto.class);
                intent1.putExtra(ID_DINHEIRO_GASTO, adaptadorDinheiroGasto.getDinheiroGastoSelecionado().getId());
                startActivity(intent1);
                return true;

            case R.id.itemEliminar:
                Intent intent2 = new Intent (this, eliminar_dinheiro_gasto.class);
                intent2.putExtra(ID_DINHEIRO_GASTO, adaptadorDinheiroGasto.getDinheiroGastoSelecionado().getId());
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(this, Compras_Efetuadas_ContentProvider.ENDERECO_DINHEIROGASTO, BdTableDinheiroGasto.TODAS_COLUNAS, null, null, BdTableDinheiroGasto.MONTANTE_GASTO);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorDinheiroGasto.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorDinheiroGasto.setCursor(null);

    }


}
