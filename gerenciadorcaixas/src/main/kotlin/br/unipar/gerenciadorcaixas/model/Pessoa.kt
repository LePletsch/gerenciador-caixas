package br.unipar.gerenciadorcaixas.model

import enumeradores.Pessoas
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
open class Pessoa(
    @Id
    @GeneratedValue(GenerationType.SEQUENCE)
    val id: Long? = null,
    val nome: String,
    val cpf: String,
    val email: String,
    val telefone: String,
    val tipo : Pessoas

)