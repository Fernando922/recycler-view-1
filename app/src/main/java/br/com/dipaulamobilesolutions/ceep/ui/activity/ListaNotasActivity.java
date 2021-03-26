package br.com.dipaulamobilesolutions.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.dipaulamobilesolutions.ceep.R;
import br.com.dipaulamobilesolutions.ceep.dao.NotaDAO;
import br.com.dipaulamobilesolutions.ceep.model.Nota;
import br.com.dipaulamobilesolutions.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

import static br.com.dipaulamobilesolutions.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.dipaulamobilesolutions.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static br.com.dipaulamobilesolutions.ceep.ui.activity.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

public class ListaNotasActivity extends AppCompatActivity {

    public static final String NOTAS = "Notas";
    private ListaNotasAdapter adapter;
    private NotaDAO notaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        setTitle(NOTAS);


        TextView botaoInsereNota = pegaTodasNotas();


        configuraBotaoInsereNota(botaoInsereNota);



        notaDAO.insere(new Nota("Comprar pão", "comprar 5 pães"));
        notaDAO.insere(new Nota("Buscar roupas", "Ja estão prontas na lavanderia"));
        notaDAO.insere(new Nota("Pagar conta de luz", "Pagar pelo app"));
        notaDAO.insere(new Nota("Estudar Java", "Curso da alura"));
        notaDAO.insere(new Nota("Comprar Ração", "no supermercado"));
        notaDAO.insere(new Nota("Buscar carro na oficina", "ficou R$ 500,00"));

        List<Nota> todasNotas = notaDAO.todos();
        configuraRecyclerView(todasNotas);


    }

    private void configuraBotaoInsereNota(TextView botaoInsereNota) {
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaiParaFormularioNotaActivity();
            }
        });
    }

    private void vaiParaFormularioNotaActivity() {
        startActivityForResult(new Intent(ListaNotasActivity.this, FormularioNotaActivity.class), CODIGO_REQUISICAO_INSERE_NOTA);
    }

    private TextView pegaTodasNotas() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        notaDAO = new NotaDAO();
        return botaoInsereNota;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ehResultadoComNota(requestCode, resultCode, data)) {
            Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            adicionaNota(notaRecebida);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void adicionaNota(Nota notaRecebida) {
        notaDAO.insere(notaRecebida);
        adapter.adiciona(notaRecebida);
    }

    private boolean ehResultadoComNota(int requestCode, int resultCode, @Nullable Intent data) {
        return ehCodigoRequisicaoInsereNota(requestCode, CODIGO_REQUISICAO_INSERE_NOTA) && ehCodigoResultadoNotaCriada(resultCode) && temNota(data);
    }

    private boolean temNota(@Nullable Intent data) {
        return data.hasExtra(CHAVE_NOTA);
    }

    private boolean ehCodigoResultadoNotaCriada(int resultCode) {
        return resultCode == CODIGO_RESULTADO_NOTA_CRIADA;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode, int codigoRequisicaoInsereNota) {
        return requestCode == codigoRequisicaoInsereNota;
    }


    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
        configuraLayoutManager(listaNotas);
    }


    private void configuraLayoutManager(RecyclerView listaNotas) {
        //LinearLayoutManager ou GridLayoutManager ou StaggeredGridLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listaNotas.setLayoutManager(linearLayoutManager);
    }

    private void configuraAdapter(List<Nota> todos, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(todos, this);
        listaNotas.setAdapter(adapter);
    }
}