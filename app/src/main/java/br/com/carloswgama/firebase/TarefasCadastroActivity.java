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

import br.com.carloswgama.firebase.Model.Tarefa;
import br.com.carloswgama.firebase.Util.FotoHelper;

public class TarefasCadastroActivity extends AppCompatActivity {

    private final int ABRIR_CAMERA = 1;
    private Tarefa tarefa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tarefa = new Tarefa();
        setContentView(R.layout.activity_tarefas_cadastro);
    }

    public void btTirarFoto(View v) {
//        tarefa.setImagem("aaa");

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

                    ImageView imageViewFoto = (ImageView)
                            findViewById(R.id.cadastro_tarefa_iv_foto);

                    imageViewFoto.setImageBitmap(tarefa.getImagemBitmap());
                }
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
            Toast.makeText(this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
