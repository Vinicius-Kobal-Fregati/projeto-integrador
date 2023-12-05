package br.com.alura.cupcake2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
class Pedido (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val entregarEmCasa: Boolean,
    val precoOriginal: BigDecimal = 0.00.toBigDecimal(),
    val precoComDesconto: BigDecimal = 0.00.toBigDecimal(),
    val pessoaId: Long = 0L
)