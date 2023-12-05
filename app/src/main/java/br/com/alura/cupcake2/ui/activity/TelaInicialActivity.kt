package br.com.alura.cupcake2.ui.activity

import CHAVE_PESSOA
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import br.com.alura.cupcake2.database.AppDatabase
import br.com.alura.cupcake2.databinding.ActivityTelaInicialBinding
import br.com.alura.cupcake2.extensions.toast
import br.com.alura.cupcake2.extensions.vaiPara
import br.com.alura.cupcake2.model.Pessoa
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TelaInicialActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTelaInicialBinding.inflate(layoutInflater);
    }

    private lateinit var pessoaGeral: Pessoa

    private val pessoaDao by lazy {
        AppDatabase.instancia(this).pessoaDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        carregarBotaoCadastra()
        carregarBotaoLogin()
    }

    fun carregarBotaoCadastra() {
        binding.botaoCadastro.setOnClickListener {
            vaiPara(CadastroClienteActivity::class.java)
        }
    }

    fun vai(pessoa: Pessoa) {
        val intent = Intent(
            this,
            PrincipalActivity::class.java
        ).apply {
            putExtra(CHAVE_PESSOA, pessoa)
        }
        startActivity(intent)
    }

    fun carregarBotaoLogin() {
        binding.botaoLogin.setOnClickListener{
            val textoEmail: String = binding.activityFormularioProdutoNome.text.toString()
            val textoSenha: String = binding.activityFormularioProduto.text.toString()

            runBlocking {
                try {
                    val pessoaEncontrada: Pessoa? = pessoaDao.autentica(textoEmail, textoSenha)
                    if (pessoaEncontrada != null) {
                        pessoaGeral = pessoaEncontrada
                    } else {
                        toast("Usuário ou senha incorretos!")
                    }
                } catch (e: Exception) {
                    Log.e("CadastroUsuario", "configuraBotaoCadastrar: ", e)
                    toast("Falha ao realizar o login do  usuário")
                }
            }

            vai(pessoaGeral)
        }
    }
}