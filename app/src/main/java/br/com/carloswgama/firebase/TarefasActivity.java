package br.com.carloswgama.firebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import br.com.carloswgama.firebase.Model.Tarefa;
import br.com.carloswgama.firebase.Util.TarefasAdapter;

public class TarefasActivity extends AppCompatActivity {

    private ListView lista;
    private ArrayList<Tarefa> tarefas;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser usuario = firebaseAuth.getCurrentUser();
        getSupportActionBar().setSubtitle(usuario.getEmail());

        tarefas = new ArrayList<Tarefa>();
        tarefas.add(new Tarefa("Tarefa 1", "imagem"));
        tarefas.add(new Tarefa("Tarefa 2", "imagem"));
        tarefas.add(new Tarefa("Tarefa 3", "imagem"));

        lista = (ListView) findViewById(R.id.tarefas_lv_lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Tarefa tarefa = tarefas.get(position);
                Intent intent = new Intent(TarefasActivity.this, TarefaVisualizarActivity.class);
                intent.putExtra("tarefaID", tarefa.getId());
                startActivity(intent);
            }
        });
    }

    private void atualizaLista() {
        TarefasAdapter adapter = new TarefasAdapter(this, tarefas);
        lista.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaLista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tarefas, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_adicionar) {
            Intent intent = new Intent(this, TarefasCadastroActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_sair) {
            firebaseAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
