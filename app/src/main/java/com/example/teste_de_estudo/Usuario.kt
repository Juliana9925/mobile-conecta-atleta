package com.example.teste_de_estudo

data class Usuario(
    val id: Int = 0,
    val nome: String,
    val email: String,
    val perfil: String, // "atleta" ou "apoiador"
    val senha: String
)
