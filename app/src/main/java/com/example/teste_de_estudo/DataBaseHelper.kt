package com.example.teste_de_estudo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "app.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "usuario"

        // Campos do cadastro
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOME = "nome"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PERFIL = "perfil" // atleta ou apoiador
        private const val COLUMN_SENHA = "senha"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL UNIQUE,
                $COLUMN_PERFIL TEXT NOT NULL,
                $COLUMN_SENHA TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertUsuario(nome: String, email: String, perfil: String, senha: String) {
        val db = writableDatabase
        val insertQuery = """
            INSERT INTO $TABLE_NAME ($COLUMN_NOME, $COLUMN_EMAIL, $COLUMN_PERFIL, $COLUMN_SENHA)
            VALUES ('$nome', '$email', '$perfil', '$senha')
        """.trimIndent()
        db.execSQL(insertQuery)
        db.close()
    }

    fun getAllUsuarios(): ArrayList<Usuario> {
        val listaUsuarios = ArrayList<Usuario>()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                val perfil = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PERFIL))
                val senha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SENHA))

                val usuario = Usuario(id, nome, email, perfil, senha)
                listaUsuarios.add(usuario)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return listaUsuarios
    }

    fun login(email: String, senha: String): Usuario? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ? AND $COLUMN_SENHA = ?"
        val cursor = db.rawQuery(query, arrayOf(email, senha))

        var usuario: Usuario? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME))
            val perfil = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PERFIL))

            usuario = Usuario(id, nome, email, perfil, senha)
        }

        cursor.close()
        db.close()

        return usuario
    }

}
