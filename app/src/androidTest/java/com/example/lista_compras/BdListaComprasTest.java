package com.example.lista_compras;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.lista_compras.BD.BdListaComprasOpenHelper;
import com.example.lista_compras.BD.BdTableCategorias;
import com.example.lista_compras.BD.BdTableDinheiroGasto;
import com.example.lista_compras.BD.BdTableListaProdutos;
import com.example.lista_compras.ClassesBd.Categorias;
import com.example.lista_compras.ClassesBd.DinheiroGasto;
import com.example.lista_compras.ClassesBd.ListaProdutos;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */



@RunWith(AndroidJUnit4.class)
public class BdListaComprasTest {
    @Before
    public void apagaBaseDados() {
        getAppContext().deleteDatabase(BdListaComprasOpenHelper.NOME_BASE_DADOS);
    }



    @Test
    public void criaBdListaCompras() {
        // Context of the app under test.
        Context appContext = getAppContext();

        BdListaComprasOpenHelper openHelper = new BdListaComprasOpenHelper(appContext);

        SQLiteDatabase db = openHelper.getReadableDatabase();

        assertTrue(db.isOpen());
    }

    @SuppressWarnings("deprecation")
    private Context getAppContext() {
        return InstrumentationRegistry.getTargetContext();
    }



    @Test
    public void testCRUD() {
        BdListaComprasOpenHelper openHelper = new BdListaComprasOpenHelper(getAppContext());
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTableCategorias tabelaCategorias = new BdTableCategorias(db);


        // Teste read Compras Efetuadas (cRud)
        Cursor cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(0, cursorCategorias.getCount());

        // Teste create/read Compras Efetuadas (CRud)

        String Nome_da_categoria = "Mercearia";
        String Tipo_de_produto = "Carne, pescado e ovoc";

        long idMercearia = criaCategorias(tabelaCategorias, Nome_da_categoria, Tipo_de_produto);

        cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(1, cursorCategorias.getCount());

        Categorias categorias = getCategoriasComID(cursorCategorias, idMercearia);

        assertEquals(Nome_da_categoria, categorias.getNome_da_categoria());
        assertEquals(Tipo_de_produto, categorias.getTipo_de_produto());




        Nome_da_categoria = "Frutas e Legumes";
        Tipo_de_produto = "Frutas";
        long idFrutasLegumes = criaCategorias(tabelaCategorias, Nome_da_categoria, Tipo_de_produto);

        cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(2, cursorCategorias.getCount());

        categorias = getCategoriasComID(cursorCategorias, idFrutasLegumes);

        assertEquals(Nome_da_categoria, categorias.getNome_da_categoria());
        assertEquals(Tipo_de_produto, categorias.getTipo_de_produto());


        // Teste Update/Read categorias (cRUd)

        //UPDATE
        Nome_da_categoria = " Mercearia ";
        Tipo_de_produto = " Carne,pescado e ovos ";


        categorias.setNome_da_categoria(Nome_da_categoria);
        categorias.setTipo_de_produto(Tipo_de_produto);


        int registosAlterados = tabelaCategorias.update(categorias.getContentValues(), BdTableCategorias._ID + "=?", new String[]{String.valueOf(idMercearia)});

        assertEquals(1, registosAlterados);

        cursorCategorias = getCategorias(tabelaCategorias);
        categorias = getCategoriasComID(cursorCategorias, idMercearia);

        assertEquals(Nome_da_categoria, categorias.getNome_da_categoria());
        assertEquals(Tipo_de_produto, categorias.getTipo_de_produto());


        // Teste Create/Delete/Read autores (CRuD)
        long id = criaCategorias(tabelaCategorias, " Lacticinios",  "Leite e derivados de leite");
        cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(3, cursorCategorias.getCount());

        tabelaCategorias.delete(BdTableCategorias._ID + "=?", new String[]{String.valueOf(id)});
        cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(2, cursorCategorias.getCount());

        getCategoriasComID(cursorCategorias, idMercearia);
        getCategoriasComID(cursorCategorias, idFrutasLegumes);





        BdTableListaProdutos tabelaListaProdutos = new BdTableListaProdutos(db);

        // Teste read lista produtos (cRud)
        Cursor cursorListaProdutos = getListaProdutos(tabelaListaProdutos);
        assertEquals(0, cursorListaProdutos.getCount());

        // Teste create/read lista produtos (CRud)
        String nome_do_produto = "Febras";
        int quantidade  = 5;



        id = criaListaProdutos(tabelaListaProdutos,nome_do_produto, quantidade,idMercearia);
        cursorListaProdutos = getListaProdutos(tabelaListaProdutos);
        assertEquals(1, cursorListaProdutos.getCount());



        ListaProdutos listaProdutos = getListaProdutosComID(cursorListaProdutos, id);

        assertEquals(nome_do_produto,  listaProdutos.getNome_do_produto());
        assertEquals(quantidade,  listaProdutos.getQuantidade());
        assertEquals(idMercearia,  listaProdutos.getCategoria());



        nome_do_produto = "Bananas ";
        quantidade = 4;
        id = criaListaProdutos(tabelaListaProdutos, nome_do_produto, quantidade, idFrutasLegumes);
        cursorListaProdutos = getListaProdutos(tabelaListaProdutos);
        assertEquals(2, cursorListaProdutos.getCount());


        listaProdutos = getListaProdutosComID(cursorListaProdutos, id);
        assertEquals(nome_do_produto,  listaProdutos.getNome_do_produto());
        assertEquals(quantidade,  listaProdutos.getQuantidade());
        assertEquals(idFrutasLegumes,  listaProdutos.getCategoria());


        // Teste read/update livros (cRUd)

        /*listaProdutos = getListaProdutosComID(cursorListaProdutos, id);*/
        nome_do_produto = "Latas de atum";
        quantidade = 7;


        listaProdutos.setNome_do_produto(nome_do_produto);
        listaProdutos.setQuantidade(quantidade);
        listaProdutos.setCategoria(idMercearia);

        tabelaListaProdutos.update(listaProdutos.getContentValues(), BdTableListaProdutos._ID + "=?", new String[]{String.valueOf(id)});

        cursorListaProdutos = getListaProdutos(tabelaListaProdutos);

        listaProdutos = getListaProdutosComID(cursorListaProdutos, id);
        assertEquals(nome_do_produto,  listaProdutos.getNome_do_produto());
        assertEquals(quantidade,  listaProdutos.getQuantidade());
        assertEquals(idMercearia,  listaProdutos.getCategoria());



        // Teste read/delete listaprodutos (cRuD)
        id = criaListaProdutos(tabelaListaProdutos, " Latas de atum",  2, idMercearia);
        cursorListaProdutos = getListaProdutos(tabelaListaProdutos);
        assertEquals(3, cursorListaProdutos.getCount());

        tabelaListaProdutos.delete(BdTableListaProdutos._ID + "=?", new String[]{String.valueOf(id)});
        cursorListaProdutos = getListaProdutos(tabelaListaProdutos);
        assertEquals(2, cursorListaProdutos.getCount());




        BdTableDinheiroGasto tabelaDinheiroGasto = new BdTableDinheiroGasto(db);
        // Teste read Compras Efetuadas (cRud)
        Cursor cursorDinheiroGasto = getDinheiroGasto(tabelaDinheiroGasto);
        assertEquals(0, cursorDinheiroGasto.getCount());

        // Teste create/read Compras Efetuadas (CRud)

        String Dia = "Segunda_feira";
        int Montante_gasto = 26;

        id = criaDinheiroGasto(tabelaDinheiroGasto, Dia, Montante_gasto);

        cursorDinheiroGasto = getDinheiroGasto(tabelaDinheiroGasto);
        assertEquals(1, cursorDinheiroGasto.getCount());

        DinheiroGasto dinheiroGasto = getDinheiroGastoComID(cursorDinheiroGasto, id);

        assertEquals(Dia, dinheiroGasto.getDia());
        assertEquals(Montante_gasto, dinheiroGasto.getMontante_gasto());




        Dia = "Terca-feira";
        Montante_gasto = 19;
        id = criaDinheiroGasto(tabelaDinheiroGasto, Dia, Montante_gasto);

        cursorDinheiroGasto= getDinheiroGasto(tabelaDinheiroGasto);
        assertEquals(2, cursorDinheiroGasto.getCount());

        dinheiroGasto = getDinheiroGastoComID(cursorDinheiroGasto, id);

        assertEquals(Dia, dinheiroGasto.getDia());
        assertEquals(Montante_gasto, dinheiroGasto.getMontante_gasto());


        // Teste Update/Read categorias (cRUd)

        //UPDATE
        Dia = "Terca-feira";
        Montante_gasto = 19;


        categorias.setNome_da_categoria(Nome_da_categoria);
        categorias.setTipo_de_produto(Tipo_de_produto);


        tabelaDinheiroGasto.update(dinheiroGasto.getContentValues(), BdTableDinheiroGasto._ID + "=?", new String[]{String.valueOf(id)});

        cursorDinheiroGasto = getDinheiroGasto(tabelaDinheiroGasto);


        dinheiroGasto = getDinheiroGastoComID(cursorDinheiroGasto, id);

        assertEquals(Dia, dinheiroGasto.getDia());
        assertEquals(Montante_gasto, dinheiroGasto.getMontante_gasto());


        // Teste Create/Delete/Read autores (CRuD)
        id = criaDinheiroGasto(tabelaDinheiroGasto, " Quarta-feira",  26);
        cursorDinheiroGasto = getDinheiroGasto(tabelaDinheiroGasto);
        assertEquals(3, cursorDinheiroGasto.getCount());

        tabelaDinheiroGasto.delete(BdTableDinheiroGasto._ID + "=?", new String[]{String.valueOf(id)});
        cursorDinheiroGasto = getDinheiroGasto(tabelaDinheiroGasto);
        assertEquals(2, cursorDinheiroGasto.getCount());


    }


    private long criaCategorias(BdTableCategorias tabelaCategorias ,String nome_da_categoria, String tipo_de_produto) {

        Categorias categorias = new Categorias();
        categorias.setNome_da_categoria(nome_da_categoria);
        categorias.setTipo_de_produto(tipo_de_produto);


        long id = tabelaCategorias.insert(categorias.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getCategorias(BdTableCategorias tabelaCategorias) {
        return tabelaCategorias.query(BdTableCategorias.TODAS_COLUNAS, null, null, null, null, null);
    }

    private Categorias getCategoriasComID(Cursor cursor, long id) {
        Categorias categorias = null;

        while (cursor.moveToNext()) {
            categorias = Categorias.fromCursor(cursor);

            if (categorias.getId() == id) {
                break;
            }
        }

        assertNotNull(categorias);

        return categorias;
    }

    private ListaProdutos getListaProdutosComID(Cursor cursor, long id) {
        ListaProdutos lista_produtos = null;

        while (cursor.moveToNext()) {
            if (ListaProdutos.fromCursor(cursor).getId() == id) {
                lista_produtos = ListaProdutos.fromCursor(cursor);
                break;
            }
        }

        assertNotNull(lista_produtos);

        return lista_produtos;
    }



    private long criaListaProdutos(BdTableListaProdutos tabelaListaProdutos, String nome_do_produto, int quantidade, long categoria){
        ListaProdutos lista_produtos = new ListaProdutos();
        lista_produtos.setNome_do_produto(nome_do_produto);
        lista_produtos.setQuantidade(quantidade);
        lista_produtos.setCategoria(categoria);

        long id = tabelaListaProdutos.insert(lista_produtos.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }


    private Cursor getListaProdutos(BdTableListaProdutos tabelaListaProdutos) {
        return tabelaListaProdutos.query(BdTableListaProdutos.TODAS_COLUNAS, null, null, null, null, null);
    }



    private long criaDinheiroGasto(BdTableDinheiroGasto tabelaDinheiroGasto, String dia, int montante_gasto) {
        DinheiroGasto dinheiroGasto = new DinheiroGasto();

        dinheiroGasto.setDia(dia);
        dinheiroGasto.setMontante_gasto(montante_gasto);


        long id = tabelaDinheiroGasto.insert(dinheiroGasto.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getDinheiroGasto(BdTableDinheiroGasto tableDinheiroGasto) {
        return tableDinheiroGasto.query(BdTableDinheiroGasto.TODAS_COLUNAS, null, null, null, null, null);
    }

    private DinheiroGasto getDinheiroGastoComID(Cursor cursor, long id) {
        DinheiroGasto dinheiroGasto = null;

        while (cursor.moveToNext()) {
            dinheiroGasto = DinheiroGasto.fromCursor(cursor);

            if (dinheiroGasto.getId() == id) {
                break;
            }
        }

        assertNotNull(dinheiroGasto);

        return dinheiroGasto;
    }
}
