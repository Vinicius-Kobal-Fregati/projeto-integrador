package br.com.alura.cupcake2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
class Cupcake (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val sabor: String,
    val ingredientes: String,
    val alergenicos: String,
    val precoOriginal: BigDecimal,
    val porcentagemDesconto: BigDecimal = 0.toBigDecimal(),
    val destacado: Boolean = false
)