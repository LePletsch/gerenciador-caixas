package br.unipar.gerenciadorcaixas.model

import enumeradores.Pessoas
import enumeradores.Setor
import enumeradores.Turno
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal

class Funcionario (
    @Id
    @GeneratedValue(GenerationType.SEQUENCE)
    override val id: Long? = null,

    cpf: String,
    val setor: Setor,
    val salario : BigDecimal,
    val turno : Turno,
    nome: String,
    email: String,
    telefone: String,
    tipo : Pessoas

) : Pessoa (id, nome, cpf, email, telefone, tipo)
