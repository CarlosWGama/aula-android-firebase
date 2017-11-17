package br.com.carloswgama.firebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import br.com.carloswgama.firebase.Model.Tarefa;
import br.com.carloswgama.firebase.Util.FotoHelper;

public class TarefaVisualizarActivity extends AppCompatActivity {

    private Tarefa tarefa;

    private EditText editTextTitulo;
    private ImageView imageViewFoto;
    private DatabaseReference referencia;
    private final int ABRIR_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa_visualizar);

        //Busca da outra Intent o Tarefa ID
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String tarefaID = extras.getString("tarefaID");


        //Atualiza Titulo
        editTextTitulo = (EditText) findViewById(R.id.visualizar_tarefa_et_titulo);

        //Atualiza imagem
        imageViewFoto = (ImageView) findViewById(R.id.visualizar_tarefa_iv_foto);


        //Busca do FireBase
        FirebaseDatabase banco = FirebaseDatabase.getInstance();
        referencia = banco.getReference("tarefas/" + tarefaID);
        referencia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tarefa = dataSnapshot.getValue(Tarefa.class);

                editTextTitulo.setText(tarefa.getTitulo());
                imageViewFoto.setImageBitmap(tarefa.getImagemBitmap());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void btTirarFoto(View v) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, ABRIR_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ABRIR_CAMERA) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap fotoTirada = (Bitmap) extras.get("data");

                    tarefa.setImagem(FotoHelper.BitmapToString(fotoTirada));
                    imageViewFoto.setImageBitmap(fotoTirada);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void btAtualizar(View v) {
        if (editTextTitulo.getText().toString().equals(""))
            Toast.makeText(this, "Preencha o título da mensagem", Toast.LENGTH_SHORT).show();
        else {
            tarefa.setTitulo(editTextTitulo.getText().toString());
            referencia.setValue(tarefa);
            Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void btExcluir(View v) {
        referencia.removeValue();
        Toast.makeText(this, "Excluído com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }



}
