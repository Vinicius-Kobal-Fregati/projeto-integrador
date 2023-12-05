package br.com.alura.cupcake2.ui.activity

import CHAVE_CUPCAKE
import CHAVE_PESSOA
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import br.com.alura.cupcake2.database.AppDatabase
import br.com.alura.cupcake2.databinding.ActivityPrincipalBinding
import br.com.alura.cupcake2.extensions.toast
import br.com.alura.cupcake2.extensions.vaiPara
import br.com.alura.cupcake2.extensions.vaiParaComPessoa
import br.com.alura.cupcake2.model.Cupcake
import br.com.alura.cupcake2.model.Pessoa
import br.com.alura.cupcake2.ui.recyclerview.adapter.ListaCupcakeAdapter
import br.com.alura.orgs.extensions.formataParaMoedaBrasileira
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.math.BigDecimal

class PrincipalActivity : AppCompatActivity() {
    private val adapter = ListaCupcakeAdapter(context = this)
    private val adapterDestacados = ListaCupcakeAdapter(context = this)
    private val adapterComDesconto = ListaCupcakeAdapter(context = this)
    private lateinit var pessoaGeral: Pessoa
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
        carregaPessoaRecebida()
        buscarProdutos()
        configuraRecyclerViewEBotao()
    }

    fun buscarProdutos() {
        buscarTodosOsProdutos()
        buscarProdutosDestacados()
        buscarProdutosComDesconto()
    }

    fun configuraRecyclerViewEBotao() {
        configuraRecyclerView()
        configuraRecyclerViewDestacados()
        configuraRecyclerViewDescontos()
        configuraBotaoCarrinho()
    }

    fun configuraBotaoCarrinho() {
        binding.imageViewCarrinho.setOnClickListener{
            //vaiPara(CarrinhoActivity::class.java)
            vaiParaComPessoa(CarrinhoActivity::class.java, pessoaGeral)
        }
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
                            precoOriginal = 8.00.toBigDecimal(),
                            porcentagemDesconto = 10.toBigDecimal(),
                            alergenicos = "")
                    )

                    cupcakeDao.salva(Cupcake(sabor = "Nutella",
                        destacado = true,
                        ingredientes = "Farinha, nutella, gotas de chocolate",
                        precoOriginal = 10.00.toBigDecimal(),
                        porcentagemDesconto = 5.toBigDecimal(),
                        alergenicos = ""))

                    cupcakeDao.salva(Cupcake(sabor = "Floresta negra",
                        destacado = false,
                        ingredientes = "Farinha, cereja, gotas de chocolate",
                        precoOriginal = 7.00.toBigDecimal(),
                        porcentagemDesconto = 0.toBigDecimal(),
                        alergenicos = ""))

                    cupcakeDao.salva(Cupcake(sabor = "Chocolate ao leite",
                        destacado = false,
                        ingredientes = "Farinha, chocolate ao leite, gotas de chocolate",
                        precoOriginal = 7.00.toBigDecimal(),
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

    fun buscarProdutosComDesconto() {
        lifecycleScope.launch {
            cupcakeDao.buscarComDescontos().collect { cupcakes ->
                adapterComDesconto.atualiza(cupcakes)
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
                putExtra(CHAVE_CUPCAKE, it)
            }
            startActivity(intent)
        }
    }

    private fun configuraRecyclerViewDestacados() {
        val recyclerView = binding.recyclerDestacados
        recyclerView.adapter = adapterDestacados
        adapterDestacados.quandoClicaNoItem = {
            val intent = Intent(
                this,
                MaisDetalhesActivity::class.java
            ).apply {
                putExtra(CHAVE_CUPCAKE, it)
            }
            startActivity(intent)
        }
    }

    private fun configuraRecyclerViewDescontos() {
        val recyclerView = binding.recyclerDescontos
        recyclerView.adapter = adapterComDesconto
        adapterComDesconto.quandoClicaNoItem = {
            val intent = Intent(
                this,
                MaisDetalhesActivity::class.java
            ).apply {
                putExtra(CHAVE_CUPCAKE, it)
            }
            startActivity(intent)
        }
    }

    fun carregaPessoaRecebida() {
        val dadosRecebidos = intent
        if (dadosRecebidos.hasExtra(CHAVE_PESSOA)) {
            val pessoa = dadosRecebidos.getParcelableExtra(CHAVE_PESSOA) as Pessoa?
            if (pessoa != null) {
                pessoaGeral = pessoa
            }
        } else {
            finish()
        }
    }
}