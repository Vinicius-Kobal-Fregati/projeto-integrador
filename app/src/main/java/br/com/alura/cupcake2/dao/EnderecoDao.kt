package br.com.alura.cupcake2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alura.cupcake2.model.Endereco
import kotlinx.coroutines.flow.Flow

@Dao
interface EnderecoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(endereco: Endereco)

    @Query("SELECT * FROM Endereco WHERE pessoaId = :pessoaId")
    fun buscaTodosDaPessoa(pessoaId: String): Flow<List<Endereco>>
}