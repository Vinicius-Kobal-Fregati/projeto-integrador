package br.com.alura.cupcake2.dao

import androidx.room.*
import br.com.alura.cupcake2.model.Cupcake
import kotlinx.coroutines.flow.Flow

@Dao
interface CupcakeDao {
    @Query("SELECT * FROM Cupcake")
    fun buscaTodos(): Flow<List<Cupcake>>

    @Query("SELECT * FROM Cupcake WHERE destacado = 1")
    fun buscarDestacados(): Flow<List<Cupcake>>

    @Query("SELECT * FROM Cupcake WHERE porcentagemDesconto > 0")
    fun buscarComDescontos(): Flow<List<Cupcake>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(cupcake: Cupcake)

    @Delete
    suspend fun remove(cupcake: Cupcake)

    @Query("SELECT * FROM Cupcake WHERE id = :id")
    fun buscaPorId(id: Long): Flow<Cupcake?>
}