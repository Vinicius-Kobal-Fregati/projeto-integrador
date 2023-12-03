package br.com.alura.cupcake2.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import br.com.alura.cupcake2.dao.EnderecoDao
import br.com.alura.cupcake2.dao.PessoaDao
import br.com.alura.cupcake2.database.AppDatabase
import br.com.alura.cupcake2.databinding.ActivityCadastroClienteBinding
import br.com.alura.cupcake2.extensions.toast
import br.com.alura.cupcake2.model.Endereco
import br.com.alura.cupcake2.model.Pessoa
import br.com.alura.cupcake2.model.TipoConta
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch

class CadastroClienteActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroClienteBinding.inflate(layoutInflater)
    }

    private val pessoaDao: PessoaDao by lazy {
        AppDatabase.instancia(this).pessoaDao()
    }

    private val enderecoDao: EnderecoDao by lazy {
        AppDatabase.instancia(this).enderecoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        carregarBotaoCadastro()
    }

    fun carregarBotaoCadastro() {
        binding.telaCadastroBotaoCadastro.setOnClickListener{
            val nomeTexto: String = binding.inputNome.text.toString()
            val emailTexto: String = binding.inputEmail.text.toString()
            val senhaTexto: String = binding.inputSenha.text.toString()
            var pessoa: Pessoa = Pessoa(nomeCompleto = nomeTexto,
                email = emailTexto,
                senha = senhaTexto,
                tipoConta = TipoConta.CLIENTE)

            lifecycleScope.launch {
                try {
                    pessoaDao.salva(pessoa)
                } catch (e: Exception) {
                    Log.e("CadastroUsuario", "configuraBotaoCadastrar: ", e)
                    toast("Falha ao cadastrar usuário")
                }
            }

            lifecycleScope.launch {
                pessoaDao.buscarPorEmail(pessoa.email)
                    .firstOrNull()?.let { pessoa = it }
            }

            val cidadeTexto: String = binding.inputCidade.text.toString()
            val bairroTexto: String = binding.inputBairro.text.toString()
            val ruaTexto: String = binding.inputRua.text.toString()
            val numeroInt: Int = binding.inputNumero.text.toString().toInt()
            val endereco: Endereco = Endereco(cidade = cidadeTexto,
                                              bairro = bairroTexto,
                                              rua = ruaTexto,
                                              numero = numeroInt)

            endereco.pessoaId = pessoa.id

            lifecycleScope.launch {
                try {
                    enderecoDao.salva(endereco)
                } catch (e: Exception) {
                    Log.e("CadastroUsuario", "configuraBotaoCadastrar: ", e)
                    toast("Falha ao cadastrar usuário")
                }
            }
        }
    }
}