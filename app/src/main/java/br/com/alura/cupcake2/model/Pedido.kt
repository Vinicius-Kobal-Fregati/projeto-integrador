package br.com.alura.cupcake2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
class Pedido (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val entregarEmCasa: Boolean,
    val custoDeEntrega: BigDecimal,
    val porcentagemDesconto: BigDecimal,
    val pessoaId: Long = 0L
)