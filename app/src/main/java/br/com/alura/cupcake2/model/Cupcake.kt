package br.com.alura.cupcake2.model

import java.math.BigDecimal

class Cupcake (
    val sabor: String,
    val listaIngredientes: List<String>,
    val listaAlergenicos: List<String>,
    val precoOriginal: BigDecimal,
    val porcentagemDesconto: BigDecimal = 0.toBigDecimal(),
    val destacado: Boolean = false
)