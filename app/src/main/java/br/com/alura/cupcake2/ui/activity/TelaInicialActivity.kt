package br.com.alura.cupcake2.ui.activity

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

class TelaInicialActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTelaInicialBinding.inflate(layoutInflater);
    }

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

    fun carregarBotaoLogin() {
        binding.botaoLogin.setOnClickListener{
            val textoEmail: String = binding.activityFormularioProdutoNome.text.toString()
            val textoSenha: String = binding.activityFormularioProduto.text.toString()

            lifecycleScope.launch {
                try {
                    val pessoaEncontrada: Pessoa? = pessoaDao.autentica(textoEmail, textoSenha)
                    if (pessoaEncontrada != null) {
                        vaiPara(PrincipalActivity::class.java)
                    } else {
                        toast("Usuário ou senha incorretos!")
                    }
                } catch (e: Exception) {
                    Log.e("CadastroUsuario", "configuraBotaoCadastrar: ", e)
                    toast("Falha ao cadastrar usuário")
                }
            }
        }
    }
}