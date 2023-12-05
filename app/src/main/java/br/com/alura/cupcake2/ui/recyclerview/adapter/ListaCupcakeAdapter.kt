package br.com.alura.cupcake2.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.cupcake2.databinding.CupcakeItemBinding
import br.com.alura.cupcake2.model.Cupcake
import br.com.alura.orgs.extensions.formataParaMoedaBrasileira

class ListaCupcakeAdapter (private val context: Context,
                           cupcakes: List<Cupcake> = emptyList(),
                           var quandoClicaNoItem: (cupcake: Cupcake) -> Unit = {},
) : RecyclerView.Adapter<ListaCupcakeAdapter.ViewHolder>() {
    private val cupcakes = cupcakes.toMutableList()

    inner class ViewHolder(private val binding: CupcakeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var cupcake: Cupcake

        init {
            itemView.setOnClickListener {
                if (::cupcake.isInitialized) {
                    quandoClicaNoItem(cupcake)
                }
            }
        }

        fun vincula(cupcake: Cupcake) {
            this.cupcake = cupcake
            val nome = binding.produtoItemNome
            nome.text = cupcake.sabor
            val descricao = binding.produtoItemDescricao
            descricao.text = cupcake.ingredientes
            val valor = binding.produtoItemValor
            val valorEmMoeda: String = cupcake.valorComDesconto().formataParaMoedaBrasileira()
            valor.text = valorEmMoeda
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = CupcakeItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = cupcakes[position]
        holder.vincula(produto)
    }

    override fun getItemCount(): Int = cupcakes.size

    fun atualiza(produtos: List<Cupcake>) {
        this.cupcakes.clear()
        this.cupcakes.addAll(produtos)
        notifyDataSetChanged()
    }
}