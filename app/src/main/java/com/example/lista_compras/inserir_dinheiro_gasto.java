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

public class inserir_dinheiro_gasto extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_CURSO_LOADER_DINHEIRO_GASTO = 0;

    private EditText editTextDia;
    private EditText editTextMontante_gasto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_dinheiro_gasto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextDia = (EditText) findViewById(R.id.editTextAlterarDia);
        editTextMontante_gasto = (EditText) findViewById(R.id.editTextAlterar_Montante_gasto);

    }

    @Override
    protected void onResume(){
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_DINHEIRO_GASTO, null, this);

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
        } else if (id == R.id.action_cancelar) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void guardar() {
        String dia = editTextDia.getText().toString();

        if(dia.trim().isEmpty()){
            editTextDia.setError("Preencha o espaço for favor!");
            return;
        }

        int montante_gasto;

        String strMontante_gasto = editTextMontante_gasto.getText().toString();

        if (strMontante_gasto.trim().isEmpty()){
            editTextMontante_gasto.setError("Preencha o espaço por favor!");
            return;
        }

        try {
            montante_gasto = Integer.parseInt(strMontante_gasto);
        } catch (NumberFormatException e) {
            editTextMontante_gasto.setError("Preço inválido!");
            return;
        }



        DinheiroGasto dinheiroGasto = new DinheiroGasto();


        dinheiroGasto.setDia(dia);
        dinheiroGasto.setMontante_gasto(montante_gasto);


        try {
            getContentResolver().insert(Compras_Efetuadas_ContentProvider.ENDERECO_DINHEIROGASTO, dinheiroGasto.getContentValues());

            Toast.makeText(this, "Valores inseridos com sucesso!!!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextDia,"Não foi possível inserir os valores!", Snackbar.LENGTH_LONG).show();


            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, Compras_Efetuadas_ContentProvider.ENDERECO_DINHEIROGASTO, BdTableDinheiroGasto.TODAS_COLUNAS, null, null, BdTableDinheiroGasto.DIA
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
