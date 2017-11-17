package br.com.carloswgama.firebase.Model;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;

import br.com.carloswgama.firebase.Util.FotoHelper;

public class Tarefa {

    private String id = "";
    private String titulo = "";
    private String imagem = "";

    public Tarefa() {}

    public Tarefa(String titulo, String imagem) {
        this.titulo = titulo;
        this.imagem = imagem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setImagem(String imagem) { this.imagem = imagem; }

    public String getImagem() { return this.imagem; }
    @Exclude
    public Bitmap getImagemBitmap() {
        return FotoHelper.StringToBitmap(this.imagem);
    }

}
