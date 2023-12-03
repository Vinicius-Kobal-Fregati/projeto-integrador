package br.com.alura.cupcake2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Endereco (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val cidade: String,
    val bairro: String,
    val rua: String,
    val numero: Int = 0,
    var pessoaId: Long? = 0L
)