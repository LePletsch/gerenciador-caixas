package br.unipar.gerenciadorcaixas.model

import enumeradores.Cor
import enumeradores.Formato
import enumeradores.Marcas
import enumeradores.Material
import enumeradores.Modelo
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal

@Entity

 class CaixaDaAgua (
    @Id
    @GeneratedValue(GenerationType.SEQUENCE)
    val id: Long? = null,

    val marca : Marcas,
    val modelo : Modelo,
    val dimensao : MutableList<Double>,
    val cor : Cor,
    val material : Material,
    val formato : Formato,
    val preco : BigDecimal

)