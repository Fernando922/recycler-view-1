package br.com.dipaulamobilesolutions.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.dipaulamobilesolutions.ceep.R;
import br.com.dipaulamobilesolutions.ceep.model.Nota;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {


    private final List<Nota> notas;
    private final Context context;

    public ListaNotasAdapter(List<Nota> notas, Context context) {

        this.notas = notas;
        this.context = context;
    }


    @NonNull
    @Override    //cria as views até preencher a tela e um pouco mais
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false);
        return new NotaViewHolder(viewCriada);
    }

    //popula a view (faz o bind com o layout)  (sempre é executado)
    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
        Nota nota = notas.get(position);
        holder.vincula(nota);
    }


    @Override
    public int getItemCount() {
        return notas.size();
    }

    //classe interna criada aqui dentro (é executado apenas uma vez)
    static class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_nota_titulo);
            descricao = itemView.findViewById(R.id.item_nota_descricao);
        }


        public void vincula(Nota nota) {
            preencheCampos(nota);
        }

        private void preencheCampos(Nota nota) {
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
        }

    }


    public void adiciona(Nota nota){
        notas.add(nota);
        notifyDataSetChanged();
    }
}
