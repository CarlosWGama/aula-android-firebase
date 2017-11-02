package br.com.carloswgama.firebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, TarefasActivity.class);
                    startActivity(intent);
                }

            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void logar(View v) {
        EditText emailEditText = (EditText) findViewById(R.id.login_et_email);
        final EditText senhaEditText = (EditText) findViewById(R.id.login_et_senha);

        String email = emailEditText.getText().toString();
        String senha = senhaEditText.getText().toString();
        if (!email.equals("") && !senha.equals("")) {
            mAuth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    Toast.makeText(LoginActivity.this, "Email ou Senha incorreto",
                                            Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(LoginActivity.this, "Falha no login",
                                            Toast.LENGTH_SHORT).show();
                                }
                                senhaEditText.setText("");
                            }
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, "Preencha o email e senha",
                    Toast.LENGTH_SHORT).show();
        }


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

                        if (!email.equals("") && !senha.equals("")) {
                            mAuth.createUserWithEmailAndPassword(email, senha)
                                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (!task.isSuccessful()) {
                                                try {
                                                    throw task.getException();
                                                } catch (FirebaseAuthUserCollisionException e) {
                                                    Toast.makeText(LoginActivity.this, "Email já cadastrado",
                                                            Toast.LENGTH_SHORT).show();
                                                } catch (FirebaseAuthWeakPasswordException e) {
                                                    Toast.makeText(LoginActivity.this, "Senha fraca",
                                                            Toast.LENGTH_SHORT).show();
                                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                                    Toast.makeText(LoginActivity.this, "Email ou Senha incorreto",
                                                            Toast.LENGTH_SHORT).show();
                                                } catch (Exception e) {
                                                    Toast.makeText(LoginActivity.this, "Falha no cadastro",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                        }
                                    });
                        } else {
                            Toast.makeText(LoginActivity.this, "Preencha o email e senha",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }).create().show();
    }
}
