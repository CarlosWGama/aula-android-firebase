package br.com.carloswgama.firebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) { //Foi a operação que realizamos
            if (resultCode == RESULT_OK) { //Foi o código que pedimos

                Bitmap foto = (Bitmap) data.getExtras().get("data");
                tarefa.setImagemBitmap(foto);

                ImageView imageViewFoto = findViewById(R.id.cadastro_tarefa_iv_foto);
                imageViewFoto.setImageBitmap(tarefa.getImagemBitmap());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void btCadastrar(View v) {
        EditText texto = (EditText) findViewById(R.id.cadastro_tarefa_et_titulo);
        if (texto.getText().toString().equals(""))
            Toast.makeText(this, "Preencha o título da mensagem", Toast.LENGTH_SHORT).show();
        else if (tarefa.getImagem().equals(""))
            Toast.makeText(this, "Tire uma foto", Toast.LENGTH_SHORT).show();
        else {


            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tarefas/"+userID);
            String id = reference.push().getKey();
            tarefa.setTitulo(texto.getText().toString());
            tarefa.setId(id);
            reference.child(id).setValue(tarefa);

            Toast.makeText(this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}


