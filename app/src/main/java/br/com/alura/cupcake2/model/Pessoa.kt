package br.com.alura.cupcake2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
class Pessoa (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nomeCompleto: String,
    val email: String,
    val senha: String,
    val tipoConta: TipoConta,
) : Parcelable