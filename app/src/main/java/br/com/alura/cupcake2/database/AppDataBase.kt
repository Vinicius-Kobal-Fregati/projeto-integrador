package br.com.alura.cupcake2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.cupcake2.dao.CupcakeDao
import br.com.alura.cupcake2.dao.EnderecoDao
import br.com.alura.cupcake2.dao.PedidoDao
import br.com.alura.cupcake2.dao.PessoaDao
import br.com.alura.cupcake2.model.Cupcake
import br.com.alura.cupcake2.model.Endereco
import br.com.alura.cupcake2.model.Pedido
import br.com.alura.cupcake2.model.Pessoa

@Database(
    entities = [
        Cupcake::class,
        Endereco::class,
        Pedido::class,
        Pessoa::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cupcakeDao(): CupcakeDao
    abstract fun enderecoDao(): EnderecoDao
    abstract fun pedidoDao(): PedidoDao
    abstract fun pessoaDao(): PessoaDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null
        fun instancia(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "cupcake.db"
            )
                .build().also {
                    db = it
                }
        }
    }
}