package com.example.willi.listadetarefas.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.willi.listadetarefas.R;
import com.example.willi.listadetarefas.helper.TarefaDAO;
import com.example.willi.listadetarefas.model.Tarefa;

public class AdicionarTarefaActivity extends AppCompatActivity {
    private EditText etTarefa;
    private EditText etDescricao;
    private Tarefa tarefaAtual;
    private boolean isEdicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);
        etTarefa = findViewById(R.id.etTarefa);
        etDescricao = findViewById(R.id.etDescricao);
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaAtual");
            if(tarefaAtual!=null) {
                isEdicao=true;
                etTarefa.setText(tarefaAtual.getNome());
                etDescricao.setText(tarefaAtual.getDescricao());
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_adicionar_tarefa,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            String nomeTarefa = etTarefa.getText().toString();
            String descricaoTarefa = etDescricao.getText().toString();
        if (nomeTarefa.isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "É necessário preencher o nome da tarefa",Toast.LENGTH_SHORT).show();
        }else{
            TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
            Tarefa tarefa = new Tarefa();
            tarefa.setNome(nomeTarefa);
            tarefa.setDescricao(descricaoTarefa);
                if(isEdicao){
                    tarefa.setID(tarefaAtual.getID());
                    tarefaDAO.alterar(tarefa);
                    finish();

                }else  {
                    tarefaDAO.salvar(tarefa);
                    finish();
                }
            }
        return super.onOptionsItemSelected(item);
    }
}
