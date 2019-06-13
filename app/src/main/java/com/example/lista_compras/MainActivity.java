package com.example.lista_compras;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Botoes
        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Lista com os produtos adquiridos", Toast.LENGTH_SHORT).show();
                openProdutos();

            }
        });

        Button btn2 = findViewById(R.id.button2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Categorias de produtos", Toast.LENGTH_SHORT).show();
                openCategoria();
            }
        });

        Button btn3 = findViewById(R.id.button3);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Dinheiro gasto até ao momento", Toast.LENGTH_SHORT).show();
                openDinheiro_gasto();
            }
        });

    }

    //Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater_menu = getMenuInflater();
        inflater_menu.inflate(R.menu.menu_inicial,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemSair){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //Calling Activities

    private void openProdutos() {
        Intent intent1 = new Intent(this, Produtos.class);
        startActivity(intent1);
    }

    private void openCategoria() {
        Intent intent2 = new Intent(this, Categoria.class);
        startActivity(intent2);
    }
    private void openDinheiro_gasto() {
        Intent intent4 = new Intent(this, Dinheiro_gasto.class);
        startActivity(intent4);
    }
}



    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Lista com os produtos a adquirir", Toast.LENGTH_SHORT).show();
                openBotaoCarregado();
            }


        });

        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Lista com as compras efetuadas", Toast.LENGTH_SHORT).show();
                openCompras_efetuadas();
            }
        });

        Button btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Dinheiro gasto em compras ao longo do tempo", Toast.LENGTH_SHORT).show();
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
*/
