package br.com.alura.cupcake2.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.alura.cupcake2.databinding.ActivityMaisDetalhesBinding
import br.com.alura.cupcake2.databinding.ActivityPrincipalBinding

class MaisDetalhesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMaisDetalhesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}