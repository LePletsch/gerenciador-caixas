package br.unipar.gerenciadorcaixas.controller

// Importa o modelo que será devolvido como JSON
import br.unipar.gerenciadorcaixas.model.Coordenadas
// Importa o serviço que fala com o Google Maps
import br.unipar.gerenciadorcaixas.service.GoogleMapsService
// Importa ResponseEntity, que permite controlar status HTTP e corpo da resposta
import org.springframework.http.ResponseEntity
// Importa as anotações web (@RestController, @GetMapping, etc.)
import org.springframework.web.bind.annotation.*

// Marca a classe como controller REST: os retornos viram JSON automaticamente
@RestController
// Todas as rotas desta classe começam com /maps
@RequestMapping("/maps")
// O Spring injeta o GoogleMapsService pelo construtor
class GoogleMapsController(
    // "private val": só esta classe usa o serviço, e ele não muda
    private val googleMapsService: GoogleMapsService
) {

    // Responde a requisições GET em /maps/coordenadas
    @GetMapping("/coordenadas")
    // @RequestParam lê o parâmetro da URL: /maps/coordenadas?endereco=...
    // O retorno é uma resposta HTTP contendo um objeto Coordenadas
    fun buscarCoordenadas(@RequestParam endereco: String): ResponseEntity<Coordenadas> {
        // Chama o serviço, que devolve Coordenadas ou null
        val resultado = googleMapsService.obterCoordenadas(endereco)
        // Verifica se o serviço encontrou as coordenadas
        return if (resultado != null) {
            // Encontrou: responde 200 OK com as coordenadas em JSON
            ResponseEntity.ok(resultado)
        } else {
            // Não encontrou (ou deu erro): responde 404 Not Found sem corpo
            ResponseEntity.notFound().build()
        }
    }
}