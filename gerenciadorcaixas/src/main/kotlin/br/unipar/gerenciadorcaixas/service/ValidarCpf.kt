package br.unipar.gerenciadorcaixas.service

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ValidarCpf(
    //Para conectar na API precisa
    //chave token
    private val token : String = "642b1e6c6713a2b8ac2062916cc37fdc",
    //url
    private val url : String = "https://api.cpf-brasil.org/"
) {
    //template
    private val restTemplate = RestTemplate()

    fun validarCpf(cpf: String) : Boolean {
        try {
            val cpfLimpo = cpf.replace(Regex("[^0-9]"), "")
            if (cpfLimpo.length != 11) {
                return false
            }

            val header = HttpHeaders().apply {
                set("X-API-Key", token)
                set("Content-Type", "application/json")
            }

            val conn = HttpEntity<Void>(header)

            val urlCompleta = "$url?cpf=$cpfLimpo"

            val resposta : ResponseEntity<String> = restTemplate.exchange(urlCompleta,
                HttpMethod.GET, conn, String::class.java)

            println("Conexão bem sucedida, resposta: $resposta")
            return resposta.statusCode == HttpStatus.OK
        }catch (e : Exception){
            println("Erro ao validar cpf: ${e.message}")
        }
        return false
    }
}