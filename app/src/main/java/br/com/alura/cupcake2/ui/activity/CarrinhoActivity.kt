package br.com.alura.cupcake2.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.alura.cupcake2.dao.CupcakeDao
import br.com.alura.cupcake2.dao.PedidoLocalDao
import br.com.alura.cupcake2.databinding.ActivityCarrinhoBinding
import br.com.alura.cupcake2.databinding.ActivityPrincipalBinding
import br.com.alura.cupcake2.extensions.toast
import br.com.alura.cupcake2.extensions.vaiPara
import br.com.alura.cupcake2.ui.recyclerview.adapter.ListaCupcakeAdapter
import java.math.BigDecimal

class CarrinhoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCarrinhoBinding.inflate(layoutInflater)
    }

    private val adapter = ListaCupcakeAdapter(context = this)
    private var precoTotal: BigDecimal = 0.toBigDecimal()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraPrecoTotal()
        configuraBotaoCancelar()
        configuraSaldo()
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.carrinhoRecycler
        recyclerView.adapter = adapter
        adapter.atualiza(PedidoLocalDao.cupcakes)
        adapter.quandoClicaNoItem = {
            PedidoLocalDao.cupcakes.remove(it)
            adapter.atualiza(PedidoLocalDao.cupcakes)
            precoTotal -= it.valorComDesconto()
            configuraSaldo()
        }
    }

    fun configuraPrecoTotal() {
        PedidoLocalDao.cupcakes.forEach { cupcake ->
            run {
                precoTotal += cupcake.valorComDesconto()
            }
        }
    }

    fun configuraSaldo(){
        binding.activityCarrinhoCusto.setText("Valor: R$${precoTotal}")
    }

    fun configuraBotaoCancelar() {
        binding.activityCarrinhoCancelar.setOnClickListener{
            PedidoLocalDao.cupcakes.clear()
            finish()
        }
    }

    fun configuraBotaoFinalizar() {
        binding.activityCarrinhoFinalizar.setOnClickListener{

        }
    }
}