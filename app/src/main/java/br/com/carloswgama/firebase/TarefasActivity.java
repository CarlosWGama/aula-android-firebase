package br.com.carloswgama.firebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.carloswgama.firebase.Model.Tarefa;
import br.com.carloswgama.firebase.Util.TarefasAdapter;

public class TarefasActivity extends AppCompatActivity {

    private ListView lista;
    private ArrayList<Tarefa> tarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas);


        tarefas = new ArrayList<Tarefa>();

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

        final FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        getSupportActionBar().setSubtitle(usuario.getEmail());

        FirebaseDatabase banco = FirebaseDatabase.getInstance();
        DatabaseReference referencia = banco.getReference("tarefas");
        referencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tarefas = new ArrayList<Tarefa>();

                for (DataSnapshot dados: dataSnapshot.getChildren()) {
                    Tarefa tarefa = dados.getValue(Tarefa.class);
                    tarefas.add(tarefa);
                    atualizaLista();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
