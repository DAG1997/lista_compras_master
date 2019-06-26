package com.example.lista_compras.Inserir_alterar_eliminar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


import com.example.lista_compras.BD.BdTableDinheiroGasto;
import com.example.lista_compras.BD.Compras_Efetuadas_ContentProvider;
import com.example.lista_compras.ClassesBd.DinheiroGasto;
import com.example.lista_compras.Main_Recyclers.Dinheiro_gasto;
import com.example.lista_compras.R;
import com.google.android.material.snackbar.Snackbar;

public class alterar_dinheiro_gasto extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSO_LOADER_DINHEIRO_GASTO = 0;

    private EditText editTextDia;
    private EditText editTextMontante_gasto;

    private DinheiroGasto dinheiroGasto = null;

    private Uri enderecoDinheiroGastoEditar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_dinheiro_gasto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextDia = (EditText) findViewById(R.id.editTextAlterarDia);
        editTextMontante_gasto= (EditText) findViewById(R.id.editTextAlterar_Montante_gasto);


        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_DINHEIRO_GASTO, null, this);


        Intent intent = getIntent();

        long idDinheiroGasto = intent.getLongExtra(Dinheiro_gasto.ID_DINHEIRO_GASTO, -1);

        if (idDinheiroGasto == -1) {
            Toast.makeText(this, "Não foi possível encontrar os valores inseridos", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoDinheiroGastoEditar = Uri.withAppendedPath(Compras_Efetuadas_ContentProvider.ENDERECO_DINHEIROGASTO, String.valueOf(idDinheiroGasto));

        Cursor cursor = getContentResolver().query(enderecoDinheiroGastoEditar, BdTableDinheiroGasto.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Não foi possível encontrar os valores inseridos!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        dinheiroGasto = dinheiroGasto.fromCursor(cursor);

        editTextDia.setText(dinheiroGasto.getDia());
        editTextMontante_gasto.setText(String.valueOf(dinheiroGasto.getMontante_gasto()));


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
        String dia = editTextDia.getText().toString();

        if(dia.trim().isEmpty()){
            editTextDia.setError("Preencha o espaço por favor!!!");
            return;
        }

        int montante_gasto;

        String strMontante_gasto = editTextMontante_gasto.getText().toString();

        if (strMontante_gasto.trim().isEmpty()){
            editTextMontante_gasto.setError("Preencha o espaço por favor!!!");
            return;
        }

        try {
            montante_gasto = Integer.parseInt(strMontante_gasto);
        } catch (NumberFormatException e) {
            editTextMontante_gasto.setError("Preço inválido");
            return;
        }



        dinheiroGasto.setDia(dia);
        dinheiroGasto.setMontante_gasto(montante_gasto);


        try {
            getContentResolver().update(enderecoDinheiroGastoEditar, dinheiroGasto.getContentValues(), null, null);

            Toast.makeText(this, "Valores alterados com sucesso!!!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextDia,"Não foi possível alterar os valores!!!", Snackbar.LENGTH_LONG).show();


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
