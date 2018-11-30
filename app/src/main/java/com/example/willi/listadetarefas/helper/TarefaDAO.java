package com.example.willi.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.example.willi.listadetarefas.activity.MainActivity;
import com.example.willi.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements IDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    public TarefaDAO(Context context){
        DatabaseHelper db = new DatabaseHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }
    @Override
    public boolean salvar(Tarefa tarefa) {
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNome());
        cv.put("descricao", tarefa.getDescricao());
        try {
            escreve.insert(DatabaseHelper.TABELA_NOME, null, cv);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        escreve.close();
        return true;
    }

    @Override
    public boolean alterar(Tarefa tarefa) {
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNome());
        cv.put("descricao", tarefa.getDescricao());
        cv.put("id", tarefa.getID());
        String idString = Long.toString(tarefa.getID());
        String[] args = {idString};
        try {
            escreve.update(DatabaseHelper.TABELA_NOME, cv, "id=?", args);
            Log.i("db_status", "sucesso ao atualizar o banco de dados");
        } catch (SQLException e) {
            Log.i("db_status", "Erro ao atualizar o banco de dados" + e.getMessage());
            return false;
        }
        escreve.close();
        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        String idString = Long.toString(tarefa.getID());
        String[] args = {idString};
        try {
            escreve.delete(DatabaseHelper.TABELA_NOME, "id=?", args);
            Log.i("db_status","sucesso ao excluir a registro" +
                    tarefa.getNome());
        }catch(SQLException e){
            Log.i("db_status","Erro ao excluir a registro" +
                    tarefa.getNome() + e.getMessage());
            return false;
        }
    return true;
    }


    @Override
    public List<Tarefa> retornar() {
        List<Tarefa> listaTarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DatabaseHelper.TABELA_NOME;
        Cursor cursor = le.rawQuery(sql,null);

        while (cursor.moveToNext()){
            String nomeTarefa = cursor.getString(cursor.getColumnIndex("nome"));
            String descricaoTarefa = cursor.getString(cursor.getColumnIndex("descricao"));
            long id = cursor.getLong(cursor.getColumnIndex("id"));
            Tarefa tarefa = new Tarefa();
            tarefa.setNome(nomeTarefa);
            tarefa.setID(id);
            tarefa.setDescricao(descricaoTarefa);
            listaTarefas.add(tarefa);
        }
        cursor.close();
        le.close();
        return listaTarefas;
    }
}
