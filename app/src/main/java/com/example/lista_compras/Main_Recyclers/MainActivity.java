package com.example.lista_compras.Main_Recyclers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lista_compras.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.Categorias);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Lista com os produtos adquiridos", Toast.LENGTH_SHORT).show();
                openCompras_efetuadas();
            }


        });

        Button btn2 = (Button) findViewById(R.id.ListaProdutos);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Lista das categorias existentes", Toast.LENGTH_SHORT).show();
                openBotaoCarregado();
            }
        });

        Button btn3 = (Button) findViewById(R.id.DinheiroGasto);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Dinheiro gasto em compras", Toast.LENGTH_SHORT).show();
                openDinheiro_gasto();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater_menu = getMenuInflater();
        inflater_menu.inflate(R.menu.menu_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemSair) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void openBotaoCarregado() {
        Intent intent = new Intent(this, Produtos.class);
        startActivity(intent);


    }

    public void openCompras_efetuadas() {
        Intent intent = new Intent(this, Categoria.class);
        startActivity(intent);
    }

    public void openDinheiro_gasto() {
        Intent intent = new Intent(this, Dinheiro_gasto.class);
        startActivity(intent);
    }
}
