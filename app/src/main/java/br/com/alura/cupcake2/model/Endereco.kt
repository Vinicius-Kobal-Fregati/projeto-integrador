package br.com.alura.cupcake2.model

class Endereco (
    val id: Long = 0L,
    val cidade: String,
    val bairro: String,
    val rua: String,
    val numero: Int,
    val complemento: String?,
    val pessoaId: Long?
)