package br.com.alura.cupcake2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alura.cupcake2.model.Pedido
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(pedido: Pedido)

    @Query("SELECT * FROM Pedido WHERE pessoaId = :pessoaId")
    fun buscaTodosDaPessoa(pessoaId: String): Flow<List<Pedido>>
}