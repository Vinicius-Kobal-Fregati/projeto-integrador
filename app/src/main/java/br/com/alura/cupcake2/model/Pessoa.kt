package br.com.alura.cupcake2.model

class Pessoa (
    val id: Long = 0L,
    val nomeCompleto: String,
    val email: String,
    val senha: String,
    val tipoConta: TipoConta,
    val endereco: Endereco
)