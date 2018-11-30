package com.example.willi.listadetarefas.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.willi.listadetarefas.R;
import com.example.willi.listadetarefas.adapter.Adaptador;
import com.example.willi.listadetarefas.helper.DatabaseHelper;
import com.example.willi.listadetarefas.helper.RecyclerItemClickListener;
import com.example.willi.listadetarefas.helper.TarefaDAO;
import com.example.willi.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcTarefas;
    List<Tarefa> listaTarefas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rcTarefas = findViewById(R.id.rcTarefas);
        rcTarefas.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), rcTarefas,
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(),
                        AdicionarTarefaActivity.class);
                intent.putExtra("tarefaAtual", listaTarefas.get(position));
                startActivity(intent);

            }

            @Override
            public void onLongItemClick(View view, final int position) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Confirmar a exclusão?");
                dialog.setMessage("Deseja excluir a tarefa: " +
                        listaTarefas.get(position).getNome() + "?");
                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TarefaDAO tarefaDAO = new TarefaDAO(MainActivity.this);
                        if(tarefaDAO.deletar(listaTarefas.get(position))){
                            Toast.makeText(getApplicationContext(),"Tarefa excluida",
                            Toast.LENGTH_SHORT).show();
                            carregarLista();
                        }else{
                            Toast.makeText(getApplicationContext(),"Erro ao Excluir a tarefa",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("Não",null);
                dialog.create();
                dialog.show();
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AdicionarTarefaActivity.class);
                startActivity(intent);
            }
        });
    }
    public void carregarLista() {
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
        rcTarefas.setLayoutManager(layout);
        rcTarefas.setHasFixedSize(true);
        rcTarefas.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        listaTarefas = new TarefaDAO(getApplicationContext()).retornar();
        Adaptador adaptador = new Adaptador(listaTarefas);
        rcTarefas.setAdapter(adaptador);
    }
    @Override
    protected void onStart() {
        carregarLista();
        super.onStart();
    }
}
