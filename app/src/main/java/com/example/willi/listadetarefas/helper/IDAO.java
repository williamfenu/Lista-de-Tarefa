package com.example.willi.listadetarefas.helper;

import com.example.willi.listadetarefas.model.Tarefa;

import java.util.List;

public interface IDAO {
    public boolean salvar(Tarefa tarefa);
    public boolean alterar(Tarefa tarefa);
    public boolean deletar(Tarefa tarefa);
    public List<Tarefa> retornar();
}
