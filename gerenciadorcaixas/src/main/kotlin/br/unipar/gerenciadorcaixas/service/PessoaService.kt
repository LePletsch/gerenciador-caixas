package br.unipar.gerenciadorcaixas.service

import br.unipar.gerenciadorcaixas.model.CaixaDaAgua
import br.unipar.gerenciadorcaixas.model.Pessoa
import br.unipar.gerenciadorcaixas.repository.PessoaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class PessoaService(
    val repository: PessoaRepository,
    private val validar : ValidarCpf
) {

    fun listar()
            : ResponseEntity<List<Pessoa>>{
        val pessoa = repository.findAll()
        return ResponseEntity.status( HttpStatus.OK)
            .body(pessoa)
    }

    fun buscar(id : Long) : ResponseEntity<Pessoa>{
        val resposta = repository.findById(id).orElseThrow {
            NoSuchElementException("O $id digitado não é válido!")
        }
        return ResponseEntity.status( HttpStatus.OK)
            .body(resposta)
    }

    fun salvar(pessoa : Pessoa) : ResponseEntity<String>{
        if (validar.validarCpf(pessoa.cpf)){
            println("CPF Válido!")
            repository.save(pessoa)
            return ResponseEntity.status(HttpStatus.CREATED)
                .body("Cadastro salvo com sucesso!")
        }else{
            println("CPF Inválido!")
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }

    }

    fun excluir(id : Long) : ResponseEntity<Void>{
        repository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}