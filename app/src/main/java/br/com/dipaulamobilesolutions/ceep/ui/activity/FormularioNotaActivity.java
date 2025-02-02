package br.com.dipaulamobilesolutions.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.dipaulamobilesolutions.ceep.R;
import br.com.dipaulamobilesolutions.ceep.model.Nota;

import static br.com.dipaulamobilesolutions.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.dipaulamobilesolutions.ceep.ui.activity.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;
import static br.com.dipaulamobilesolutions.ceep.ui.activity.NotaActivityConstantes.NOVA_NOTA;

public class FormularioNotaActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);
        setTitle(NOVA_NOTA);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(ehMenuSalvaNota(item)){
            Nota novaNota = criarNota();
            retornaNotaParaLista(novaNota);
        }

        return super.onOptionsItemSelected(item);
    }

    private void retornaNotaParaLista(Nota novaNota) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, novaNota);
        setResult(CODIGO_RESULTADO_NOTA_CRIADA, resultadoInsercao);
        finish();

    }

    private Nota criarNota() {
        EditText titulo = findViewById(R.id.formulario_nota_titulo);
        EditText descricao = findViewById(R.id.formulario_nota_descricao);
        return new Nota(titulo.getText().toString(), descricao.getText().toString());
    }

    private boolean ehMenuSalvaNota(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }


}