package br.com.alura.cupcake2.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.alura.cupcake2.databinding.ActivityTelaInicialBinding
import br.com.alura.cupcake2.extensions.vaiPara

class TelaInicialActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTelaInicialBinding.inflate(layoutInflater);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        carregarBotaoCadastra()
    }

    fun carregarBotaoCadastra() {
        binding.botaoCadastro.setOnClickListener {
            vaiPara(CadastroClienteActivity::class.java)
        }
    }
}