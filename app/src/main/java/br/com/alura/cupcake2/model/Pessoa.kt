package br.com.alura.cupcake2.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Pessoa (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nomeCompleto: String,
    val email: String,
    val senha: String,
    val tipoConta: TipoConta,
)