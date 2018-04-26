package br.com.carloswgama.firebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import br.com.carloswgama.firebase.Model.Tarefa;

public class TarefaVisualizarActivity extends AppCompatActivity {

    private Tarefa tarefa;

    private EditText editTextTitulo;
    private ImageView imageViewFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa_visualizar);

        tarefa = new Tarefa("Título da Mensagem", "A");

        //Atualiza Titulo
        editTextTitulo = (EditText) findViewById(R.id.visualizar_tarefa_et_titulo);
        editTextTitulo.setText(tarefa.getTitulo());

        //Atualiza imagem
        imageViewFoto = (ImageView) findViewById(R.id.visualizar_tarefa_iv_foto);


    }

    public void btTirarFoto(View v) {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap foto = (Bitmap) data.getExtras().get("data");
            tarefa.setImagemBitmap(foto);
            imageViewFoto.setImageBitmap(tarefa.getImagemBitmap());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void btAtualizar(View v) {
        if (editTextTitulo.getText().toString().equals(""))
            Toast.makeText(this, "Preencha o título da mensagem", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void btExcluir(View v) {
        Toast.makeText(this, "Excluído com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }



}
