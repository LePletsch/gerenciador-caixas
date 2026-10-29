package br.unipar.gerenciadorcaixas.service

import br.unipar.gerenciadorcaixas.model.CaixaDaAgua
import br.unipar.gerenciadorcaixas.repository.CaixaDaAguaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Service
class CaixaDAguaService (
    val repository : CaixaDaAguaRepository
){

    fun listar()
            : ResponseEntity<List<CaixaDaAgua>>{
        val caixas = repository.findAll()
        return ResponseEntity.status( HttpStatus.OK)
            .body(caixas)
    }

    fun buscar(id : Long) : ResponseEntity<CaixaDaAgua>{
        val resposta = repository.findById(id).orElseThrow {
            NoSuchElementException("O $id digitado não é válido!")
        }
        return ResponseEntity.status( HttpStatus.OK)
            .body(resposta)
    }

    fun salvar(caixa :CaixaDaAgua) : ResponseEntity<String>{
        repository.save(caixa)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Caixa da água salva com sucesso!")
    }

    fun excluir(id : Long) : ResponseEntity<Void>{
        repository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}