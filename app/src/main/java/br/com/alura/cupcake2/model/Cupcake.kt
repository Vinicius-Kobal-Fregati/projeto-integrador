package br.com.alura.cupcake2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity
@Parcelize
class Cupcake (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val sabor: String,
    val ingredientes: String = "",
    val alergenicos: String = "",
    val precoOriginal: BigDecimal = 0.toBigDecimal(),
    val porcentagemDesconto: BigDecimal = 0.toBigDecimal(),
    val destacado: Boolean = false
) : Parcelable {
    fun valorComDesconto() = (precoOriginal - precoOriginal * porcentagemDesconto / 100.toBigDecimal())
}