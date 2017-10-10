package br.com.carloswgama.firebase.Util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.carloswgama.firebase.Model.Tarefa;
import br.com.carloswgama.firebase.R;

public class TarefasAdapter extends ArrayAdapter {


    private Context c;
    private ArrayList<Tarefa> tarefas;

    public TarefasAdapter(Context c, ArrayList<Tarefa> tarefas) {
        super(c, 0, tarefas);

        this.c = c;
        this.tarefas = tarefas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v;
        if (convertView != null)
            v = convertView;
        else {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_tarefas, parent, false);
        }

        //Busca o titulo
        TextView tituloTextView = (TextView) v.findViewById(R.id.listview_tarefas_titulo);
        tituloTextView.setText(tarefas.get(position).getTitulo());

        //Busca a Imagem
        ImageView fotoImageView = (ImageView) v.findViewById(R.id.listview_tarefas_imagem);

        return v;
    }
}
