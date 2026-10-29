package br.unipar.gerenciadorcaixas.controller

import br.unipar.gerenciadorcaixas.model.Pessoa
import br.unipar.gerenciadorcaixas.service.PessoaService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
@CrossOrigin(origins = ["*"])
class PessoaController(
    private val servico : PessoaService
) {

    @GetMapping("/pessoa/listar")
    fun listar()
            : ResponseEntity<List<Pessoa>>{
        return servico.listar()
    }

    @GetMapping("/pessoa/buscar/{id}")
    fun buscar(@PathVariable id : Long) : ResponseEntity<Pessoa>{
        return servico.buscar(id)
    }

    @PostMapping("/pessoa/salvar")
    fun salvar(@RequestBody pessoa : Pessoa) : ResponseEntity<String>{
        return servico.salvar(pessoa)
    }

    @GetMapping("/pessoa/excluir/{id}")
    fun excluir(@PathVariable id : Long) : ResponseEntity<Void>{
        return servico.excluir(id)
    }
}