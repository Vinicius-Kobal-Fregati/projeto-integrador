package br.com.alura.cupcake2.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import br.com.alura.cupcake2.database.AppDatabase
import br.com.alura.cupcake2.databinding.ActivityPrincipalBinding
import br.com.alura.cupcake2.model.Cupcake
import br.com.alura.cupcake2.ui.recyclerview.adapter.ListaCupcakeAdapter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class PrincipalActivity : AppCompatActivity() {

    private val adapter = ListaCupcakeAdapter(context = this)
    private val adapterDestacados = ListaCupcakeAdapter(context = this)
    private val binding by lazy {
        ActivityPrincipalBinding.inflate(layoutInflater)
    }

    private val cupcakeDao by lazy {
        AppDatabase.instancia(this).cupcakeDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adicionarProdutosCasoNãoTenha()
        buscarTodosOsProdutos()
        buscarProdutosDestacados()
        configuraRecyclerView()
        configuraRecyclerViewDestacados()
    }

    fun adicionarProdutosCasoNãoTenha() {
        lifecycleScope.launch {
            val cupcakes: List<Cupcake>? = cupcakeDao.buscaTodos().firstOrNull()
            cupcakes?.let { cupcakes ->
                if (cupcakes.isEmpty()) {
                    cupcakeDao.salva(
                        Cupcake(sabor = "Morango",
                            destacado = true,
                            ingredientes = "Farinha, morango, gotas de chocolate",
                            precoOriginal = 20.00.toBigDecimal(),
                            porcentagemDesconto = 0.toBigDecimal(),
                            alergenicos = "")
                    )

                    cupcakeDao.salva(Cupcake(sabor = "Nutella",
                        destacado = true,
                        ingredientes = "Farinha, nutella, gotas de chocolate",
                        precoOriginal = 25.00.toBigDecimal(),
                        porcentagemDesconto = 0.toBigDecimal(),
                        alergenicos = ""))

                    cupcakeDao.salva(Cupcake(sabor = "Cereja",
                        destacado = false,
                        ingredientes = "Farinha, cereja, gotas de chocolate",
                        precoOriginal = 22.00.toBigDecimal(),
                        porcentagemDesconto = 0.toBigDecimal(),
                        alergenicos = ""))
                }
            }
        }
    }

    fun buscarTodosOsProdutos() {
        lifecycleScope.launch {
            cupcakeDao.buscaTodos().collect { cupcakes ->
                adapter.atualiza(cupcakes)
            }
        }
    }

    fun buscarProdutosDestacados() {
        lifecycleScope.launch {
            cupcakeDao.buscarDestacados().collect { cupcakes ->
                adapterDestacados.atualiza(cupcakes)
            }
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.recyclerCupcake
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                MaisDetalhesActivity::class.java
            ).apply {
                //putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
    }

    private fun configuraRecyclerViewDestacados() {
        val recyclerView = binding.recyclerDestacados
        recyclerView.adapter = adapterDestacados
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                MaisDetalhesActivity::class.java
            ).apply {
                //putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
    }
}