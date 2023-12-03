package br.com.alura.cupcake2.model

import java.math.BigDecimal

class Pedido (
    val id: Long = 0L,
    val cupcakes: List<Cupcake>,
    val entregarEmCasa: Boolean,
    val custoDeEntrega: BigDecimal,
    val porcentagemDesconto: BigDecimal,
    val pessoaId: Long = 0L
)