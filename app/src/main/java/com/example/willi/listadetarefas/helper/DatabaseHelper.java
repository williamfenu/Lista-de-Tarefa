package com.example.willi.listadetarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final int VERSION = 1;
    public static final String NOME_DB = "db_tarefas";
    public static final String TABELA_NOME = "tb_tarefas";

    public DatabaseHelper(Context context) {

        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_NOME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, descricao TEXT)";
        try {
            db.execSQL(sql);
            Log.i("db_info", "Criacao do banco de dados feita com sucesso");
        }catch(Exception e){
            Log.i("db_erro", "erro ao criar o bando de dados: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
