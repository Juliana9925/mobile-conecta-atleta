package com.example.teste_de_estudo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.Intent


class MainActivity : AppCompatActivity() {


    private lateinit var btnLogin: Button
    private lateinit var btnCadastrar: Button
    private lateinit var btnSobre: Button
    private lateinit var textTitulo : TextView
    private lateinit var textSubtitulo : TextView


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textTitulo    = findViewById(R.id.textTitulo)
        textSubtitulo    = findViewById(R.id.textSubtitulo)
        btnLogin = findViewById(R.id.btnLogin)
        btnCadastrar = findViewById(R.id.btnCadastrar)
        btnSobre = findViewById(R.id.btnSobre)

        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        btnSobre.setOnClickListener {
            val intent = Intent(this, SobreActivity::class.java)
            startActivity(intent)
        }

    }

}



