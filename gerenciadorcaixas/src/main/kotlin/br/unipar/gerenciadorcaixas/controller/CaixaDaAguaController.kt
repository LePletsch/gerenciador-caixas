package br.unipar.gerenciadorcaixas.controller

import br.unipar.gerenciadorcaixas.model.CaixaDaAgua
import br.unipar.gerenciadorcaixas.repository.CaixaDaAguaRepository
import br.unipar.gerenciadorcaixas.service.CaixaDAguaService
import enumeradores.Cor
import enumeradores.Formato
import enumeradores.Marcas
import enumeradores.Material
import enumeradores.Modelo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.math.BigDecimal

@Controller
@CrossOrigin(origins = ["*"])
class CaixaDaAguaController (
    private val servico : CaixaDAguaService
){

    @GetMapping("/caixa/listar")
    fun listar()
            : ResponseEntity<List<CaixaDaAgua>>{
        return servico.listar()
    }

    @GetMapping("/caixa/buscar/{id}")
    fun buscar(@PathVariable id : Long) : ResponseEntity<CaixaDaAgua>{
        return servico.buscar(id)
    }

    @PostMapping("/caixa/salvar")
    fun salvar(@RequestBody caixa :CaixaDaAgua) : ResponseEntity<String>{
        return servico.salvar(caixa)
    }

    @GetMapping("/caixa/excluir/{id}")
    fun excluir(@PathVariable id : Long) : ResponseEntity<Void>{
        return servico.excluir(id)
    }


}
