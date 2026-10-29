package br.unipar.gerenciadorcaixas.model

import enumeradores.Pessoas
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Cliente (
    @Id
    @GeneratedValue(GenerationType.SEQUENCE)
    override val id: Long? = null,

    cpf: String,
    nome: String,
    email: String,
    telefone: String,
    tipo : Pessoas

) : Pessoa (id,nome, cpf, email, telefone, tipo) {
}
