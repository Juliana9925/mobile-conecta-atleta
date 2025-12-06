package com.example.teste_de_estudo

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        // Referências dos campos
        val edtNome = findViewById<EditText>(R.id.edtNome)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtSenha = findViewById<EditText>(R.id.edtSenha)
        val edtConfirmaSenha = findViewById<EditText>(R.id.edtConfirmaSenha)
        val grupoPerfil = findViewById<RadioGroup>(R.id.grupoPerfil)
        val btnInscrever = findViewById<Button>(R.id.btnInscrever)
        val txtLoginRedirect = findViewById<TextView>(R.id.txtLoginRedirect)

        // Instancia o banco
        val db = DatabaseHelper(this)

        // Clique do botão
        btnInscrever.setOnClickListener {

            val nome = edtNome.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val senha = edtSenha.text.toString()
            val confirmaSenha = edtConfirmaSenha.text.toString()

            val perfil = when (grupoPerfil.checkedRadioButtonId) {
                R.id.rbAtleta -> "atleta"
                R.id.rbApoiador -> "apoiador"
                else -> ""
            }

            // Validações
            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || perfil.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senha != confirmaSenha) {
                Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Salva no banco
            db.insertUsuario(nome, email, perfil, senha)

            Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
            finish() // Fecha a tela e volta para a anterior
        }

        txtLoginRedirect.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
