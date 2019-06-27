package com.example.lista_compras.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lista_compras.ClassesBd.ListaProdutos;
import com.example.lista_compras.Main_Recyclers.Produtos;
import com.example.lista_compras.R;

import org.w3c.dom.Text;

public class Adaptador_Lista_Compras extends RecyclerView.Adapter<Adaptador_Lista_Compras.ViewHolderListaProdutos> {
    private Cursor cursor;
    private Context context;

    public Adaptador_Lista_Compras(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        if (this.cursor != cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public ViewHolderListaProdutos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista_Produtos = LayoutInflater.from(context).inflate(R.layout.item_lista_produtos, parent, false);

        return new ViewHolderListaProdutos(itemLista_Produtos);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderListaProdutos holder, int position) {
        cursor.moveToPosition(position);
        ListaProdutos listaProdutos = ListaProdutos.fromCursor(cursor);
        holder.setListaProdutos(listaProdutos);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (cursor == null){
            return 0;
        }
        return cursor.getCount();
    }



    public ListaProdutos getListaProdutosSelecionada() {
        if (viewHolderListaProdutosSelecionada == null) {
            return null;
        }
        return viewHolderListaProdutosSelecionada.listaProdutos;
    }

    private static ViewHolderListaProdutos viewHolderListaProdutosSelecionada = null;



    public class ViewHolderListaProdutos extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewNome_produto;
        private TextView textViewQuantidade;
        private TextView textViewCategoria;


        private ListaProdutos listaProdutos;

        public ViewHolderListaProdutos(@NonNull View itemView) {
            super(itemView);

            textViewNome_produto = (TextView)itemView.findViewById(R.id.textViewNome_produto);
            textViewQuantidade = (TextView)itemView.findViewById(R.id.textViewQuantidade);
            textViewCategoria = (TextView)itemView.findViewById(R.id.textViewCategoria);

            itemView.setOnClickListener(this);
        }

        public void setListaProdutos(ListaProdutos listaProdutos) {
            this.listaProdutos = listaProdutos;

            textViewNome_produto.setText(listaProdutos.getNome_do_produto());
            textViewQuantidade.setText(String.valueOf(listaProdutos.getQuantidade()));
            textViewCategoria.setText(String.valueOf(listaProdutos.getNomeCategoria()));

        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if (viewHolderListaProdutosSelecionada != null) {
                viewHolderListaProdutosSelecionada.desSeleciona();
            }

            viewHolderListaProdutosSelecionada = this;

            ((Produtos) context).atualizaOpcoesMenu();

            seleciona();
        }

        private void desSeleciona() {
            itemView.setBackgroundResource(android.R.color.white);
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.colorItemSelecionado);
        }
    }
}
