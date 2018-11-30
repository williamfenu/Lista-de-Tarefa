package com.example.willi.listadetarefas.adapter;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.willi.listadetarefas.R;
import com.example.willi.listadetarefas.model.Tarefa;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyViewHolder> {

    private List<Tarefa> listaTarefas;

    public Adaptador(List<Tarefa> listaTarefas){
        this.listaTarefas = listaTarefas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_tarefa,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txTarefa.setText(listaTarefas.get(position).getNome());
    }

    @Override
    public int getItemCount() {

        return listaTarefas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView txTarefa;
       public MyViewHolder(@NonNull View itemView) {
           super(itemView);
           txTarefa= itemView.findViewById(R.id.textViewTarefa);
       }
   }
}
