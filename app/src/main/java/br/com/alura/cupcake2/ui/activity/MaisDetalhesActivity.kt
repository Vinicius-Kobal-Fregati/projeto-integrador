package br.com.alura.cupcake2.ui.activity

import CHAVE_CUPCAKE
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.cupcake2.dao.PedidoLocalDao
import br.com.alura.cupcake2.databinding.ActivityMaisDetalhesBinding
import br.com.alura.cupcake2.model.Cupcake
import br.com.alura.orgs.extensions.formataParaMoedaBrasileira
import java.math.BigDecimal

class MaisDetalhesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMaisDetalhesBinding.inflate(layoutInflater)
    }

    private lateinit var cupcakeGeral: Cupcake

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        carregaCupcakeRecebido()
        configuraBotaoAdiciona()
        configuraBotaoRemove()
    }

    fun carregaCupcakeRecebido() {
        val dadosRecebidos = intent
        if (dadosRecebidos.hasExtra(CHAVE_CUPCAKE)) {
            val cupcake = dadosRecebidos.getParcelableExtra(CHAVE_CUPCAKE) as Cupcake?
            if (cupcake != null) {
                cupcakeGeral = cupcake
                binding.activityDetalhesProdutoNome.setText(cupcake.sabor)
                val custo: BigDecimal = cupcake.valorComDesconto()
                binding.activityDetalhesProdutoValor.setText(custo.formataParaMoedaBrasileira())
                binding.activityDetalhesProdutoIngredientes.setText(cupcake.ingredientes)
                binding.activityDetalhesProdutoAlergenicos.setText(cupcake.alergenicos)
            }
        } else {
            finish()
        }
    }

    fun configuraBotaoAdiciona() {
        binding.botaoAdicionar.setOnClickListener{
            PedidoLocalDao.cupcakes.add(cupcakeGeral)
        }
    }

    fun configuraBotaoRemove() {
        binding.botaoRemover.setOnClickListener{
            PedidoLocalDao.cupcakes.remove(cupcakeGeral)
        }
    }
}