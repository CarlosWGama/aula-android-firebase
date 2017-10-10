package br.com.carloswgama.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.carloswgama.firebase.Model.Tarefa;

public class TarefasCadastroActivity extends AppCompatActivity {

    private Tarefa tarefa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tarefa = new Tarefa();
        setContentView(R.layout.activity_tarefas_cadastro);
    }

    public void btTirarFoto(View v) {
        tarefa.setImagem("aaa");
        ImageView imageViewFoto = (ImageView) findViewById(R.id.cadastro_tarefa_iv_foto);
        imageViewFoto.setImageResource(R.drawable.ic_camera2);
    }

    public void btCadastrar(View v) {
        EditText texto = (EditText) findViewById(R.id.cadastro_tarefa_et_titulo);
        if (texto.getText().toString().equals(""))
            Toast.makeText(this, "Preencha o título da mensagem", Toast.LENGTH_SHORT).show();
        else if (tarefa.getImagem().equals(""))
            Toast.makeText(this, "Tire uma foto", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
