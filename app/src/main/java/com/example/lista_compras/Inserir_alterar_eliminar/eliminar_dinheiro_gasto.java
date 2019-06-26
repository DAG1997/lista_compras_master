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

import com.example.lista_compras.BD.BdTableDinheiroGasto;
import com.example.lista_compras.BD.Compras_Efetuadas_ContentProvider;
import com.example.lista_compras.ClassesBd.DinheiroGasto;
import com.example.lista_compras.Main_Recyclers.Dinheiro_gasto;
import com.example.lista_compras.R;


public class eliminar_dinheiro_gasto extends AppCompatActivity {
    private Uri enderecoDinheiroGastoApagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_dinheiro_gasto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewDia = (TextView) findViewById(R.id.textViewEliminarDia);
        TextView textViewMontante_gasto = (TextView) findViewById(R.id.textViewEliminar_Montante_gasto);


        Intent intent = getIntent();
        long idDinheiroGasto = intent.getLongExtra(Dinheiro_gasto.ID_DINHEIRO_GASTO, -1);
        if (idDinheiroGasto == -1) {
            Toast.makeText(this, "Não foi possível eliminar os  valores!!!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoDinheiroGastoApagar = Uri.withAppendedPath(Compras_Efetuadas_ContentProvider.ENDERECO_DINHEIROGASTO, String.valueOf(idDinheiroGasto));

        Cursor cursor = getContentResolver().query(enderecoDinheiroGastoApagar, BdTableDinheiroGasto.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Não foi possível eliminar os valores", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        DinheiroGasto dinheiroGasto = DinheiroGasto.fromCursor(cursor);

        textViewDia.setText(dinheiroGasto.getDia());
        textViewMontante_gasto.setText(String.valueOf(dinheiroGasto.getMontante_gasto()));

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
        int valoresApagados = getContentResolver().delete(enderecoDinheiroGastoApagar, null, null);

        if (valoresApagados == 1) {
            Toast.makeText(this, "Valores eliminados com sucesso!!!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Não foi possível eliminar os valore!!!", Toast.LENGTH_LONG).show();
        }
    }
}
