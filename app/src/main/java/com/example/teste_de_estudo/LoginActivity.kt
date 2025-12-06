package com.example.teste_de_estudo

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edtEmailLogin = findViewById<EditText>(R.id.edtEmailLogin)
        val edtSenhaLogin = findViewById<EditText>(R.id.edtSenhaLogin)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val txtCadastrarRedirect = findViewById<TextView>(R.id.txtCadastrarRedirect)

        val db = DatabaseHelper(this)

        btnEntrar.setOnClickListener {
            val email = edtEmailLogin.text.toString().trim()
            val senha = edtSenhaLogin.text.toString().trim()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usuario = db.login(email, senha)

            if (usuario != null) {
                Toast.makeText(this, "Bem-vindo(a), ${usuario.nome}!", Toast.LENGTH_SHORT).show()
                if (usuario.perfil == "Atleta") {
                    startActivity(Intent(this, PerfilAtletaActivity::class.java))
                } else {
                    startActivity(Intent(this, PerfilApoiadorActivity::class.java))
                }

                finish()

            } else {
                Toast.makeText(this, "Email ou senha incorretos.", Toast.LENGTH_SHORT).show()
            }
        }

        txtCadastrarRedirect.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }
}
