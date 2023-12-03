package br.com.alura.cupcake2.dao

import androidx.room.*
import br.com.alura.cupcake2.model.Pessoa
import kotlinx.coroutines.flow.Flow

@Dao
interface PessoaDao {
    @Insert
    suspend fun salva(pessoa: Pessoa)

    @Query(
        """
        SELECT * FROM Pessoa 
        WHERE email = :pessoaEmail 
        AND senha = :senha"""
    )
    suspend fun autentica(
        pessoaEmail: String,
        senha: String
    ): Pessoa?

    @Query(
        """
            SELECT * FROM Pessoa
            WHERE id = :pessoaId
        """
    )
    fun buscaPorId(pessoaId: String): Flow<Pessoa>

    @Query(
        """
            SELECT * FROM Pessoa
            WHERE email = :pessoaEmail
        """
    )
    fun buscarPorEmail(pessoaEmail: String): Flow<Pessoa>
}