package br.com.alura.cupcake2.ui.activity

import CHAVE_PESSOA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import br.com.alura.cupcake2.dao.PedidoDao
import br.com.alura.cupcake2.dao.PedidoLocalDao
import br.com.alura.cupcake2.dao.PessoaDao
import br.com.alura.cupcake2.database.AppDatabase
import br.com.alura.cupcake2.databinding.ActivityFinalizaCompraBinding
import br.com.alura.cupcake2.extensions.toast
import br.com.alura.cupcake2.extensions.vaiParaComPessoa
import br.com.alura.cupcake2.model.Pedido
import br.com.alura.cupcake2.model.Pessoa
import br.com.alura.orgs.extensions.formataParaMoedaBrasileira
import kotlinx.coroutines.launch
import java.math.BigDecimal

class FinalizaCompraActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFinalizaCompraBinding.inflate(layoutInflater)
    }

    private val pedidoDao: PedidoDao by lazy {
        AppDatabase.instancia(this).pedidoDao()
    }

    private var entregarEmCasa: Boolean = false
    private var precoTotalComDesconto: BigDecimal = 0.toBigDecimal()
    private var precoTotal: BigDecimal = 0.toBigDecimal()
    private lateinit var pessoaGeral: Pessoa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraPrecoTotalComDesconto()
        configuraPrecoTotal()
        carregaPessoaRecebida()
        atribuiCampos()
        configuraBotaoConcluir()
    }

    fun atribuiCampos() {
        entregarEmCasa = binding.activityFinalizaCheckboxEntregaCasa.isChecked
        binding.activityFinalizaPrecoComDesconto.setText("Preço original: ${precoTotalComDesconto.formataParaMoedaBrasileira()}")
        binding.activityFinalizaPrecoOriginal.setText("Desconto: ${precoTotal.formataParaMoedaBrasileira()}")
        binding.activityFinalizaDesconto.setText("Preço com desconto: ${(precoTotal - precoTotalComDesconto).formataParaMoedaBrasileira()}")
    }

    fun configuraPrecoTotal() {
        PedidoLocalDao.cupcakes.forEach { cupcake ->
            run {
                precoTotal += cupcake.precoOriginal
            }
        }
    }

    fun configuraPrecoTotalComDesconto() {
        PedidoLocalDao.cupcakes.forEach { cupcake ->
            run {
                precoTotalComDesconto += cupcake.valorComDesconto()
            }
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

    fun configuraBotaoConcluir() {
        binding.activityFinalizaBotaoFinalizar.setOnClickListener{
            val pedido = Pedido(entregarEmCasa = entregarEmCasa, precoOriginal = precoTotal,
                precoComDesconto = precoTotalComDesconto, pessoaId = pessoaGeral.id)

            lifecycleScope.launch {
                try {
                    pedidoDao.salva(pedido)
                    vaiParaComPessoa(PrincipalActivity::class.java, pessoaGeral)
                    PedidoLocalDao.cupcakes.clear()
                }
                catch (e: Exception) {
                    toast("Ocorreu um erro ao salvar o pedido")
                }
            }
        }
    }

}