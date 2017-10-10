package br.com.carloswgama.firebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void logar(View v) {
        EditText emailEditText = (EditText) findViewById(R.id.login_et_email);
        EditText senhaEditText = (EditText) findViewById(R.id.login_et_senha);

        String email = emailEditText.getText().toString();
        String senha = senhaEditText.getText().toString();


        Intent intent = new Intent(LoginActivity.this, TarefasActivity.class);
        startActivity(intent);
    }


    public void solicitarCadastro(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View alertView = inflater.inflate(R.layout.alert_cadastrar_usuario, null);

        new AlertDialog.Builder(this).setTitle("Cadastrar Usuário")
                .setView(alertView)
                .setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText campoEmail = (EditText) alertView.findViewById(R.id.alert_cadastrar_et_email);
                        EditText campoSenha = (EditText) alertView.findViewById(R.id.alert_cadastrar_et_senha);

                        String email = campoEmail.getText().toString();
                        String senha = campoSenha.getText().toString();
                    }
                }).create().show();
    }
}
