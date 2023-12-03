package br.com.alura.cupcake2.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.alura.cupcake2.databinding.ActivityCarrinhoBinding
import br.com.alura.cupcake2.databinding.ActivityPrincipalBinding

class CarrinhoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCarrinhoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}