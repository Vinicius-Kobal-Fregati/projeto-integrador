package br.com.alura.cupcake2.extensions

import CHAVE_PESSOA
import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.alura.cupcake2.model.Pessoa
import br.com.alura.cupcake2.ui.activity.PrincipalActivity

fun Context.vaiPara(clazz: Class<*>, intent: Intent.() -> Unit = {}) {
    Intent(this, clazz)
        .apply {
            // Se quiser, pode executar uma operação na intent
            intent()
            startActivity(this)
        }
}

fun Context.vaiParaComPessoa(clazz: Class<*>, pessoa: Pessoa) {
    Intent(this, clazz)
        .apply {
            putExtra(CHAVE_PESSOA, pessoa)
        }.run {
            startActivity(this)
        }
}

fun Context.toast(mensagem: String) {
    Toast.makeText(
        this,
        mensagem,
        Toast.LENGTH_SHORT
    ).show()
}