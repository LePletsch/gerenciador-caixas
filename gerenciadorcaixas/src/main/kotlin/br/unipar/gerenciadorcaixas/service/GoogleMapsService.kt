package br.unipar.gerenciadorcaixas.service

// Importa a classe que guarda latitude e longitude
import br.unipar.gerenciadorcaixas.model.Coordenadas
// Importa o LoggerFactory do SLF4J, usado para criar o logger
import org.slf4j.LoggerFactory
// Importa @Value, que injeta valores do application.properties
import org.springframework.beans.factory.annotation.Value
// Importa @Service, que registra a classe como um bean do Spring
import org.springframework.stereotype.Service
// Importa o RestTemplate, cliente HTTP do Spring
import org.springframework.web.client.RestTemplate
// Importa o construtor de URLs, que cuida de encoding (acentos, espaços, &)
import org.springframework.web.util.UriComponentsBuilder

// Marca a classe como serviço (camada de regras de negócio)
@Service
// Construtor primário: o Spring injeta os parâmetros automaticamente
class GoogleMapsService(
    // Lê a propriedade google.maps.api.key do application.properties.
    // O "\$" é necessário em Kotlin para o "$" não virar template de string
    @Value("\${google.maps.api.key}")
    // "private val": propriedade privada e imutável (não precisa ser var)
    private val apiKey: String,

    // Lê a propriedade google.maps.url (endereço da Geocoding API)
    @Value("\${google.maps.url}")
    // Também privada e imutável
    private val url: String
) {
    // Cria o logger desta classe, usado no lugar de println
    private val logger = LoggerFactory.getLogger(GoogleMapsService::class.java)
    // Cria o cliente HTTP que fará a chamada ao Google
    private val restTemplate = RestTemplate()

    // Recebe um endereço em texto e devolve Coordenadas, ou null se falhar.
    // O "?" após Coordenadas indica que o retorno pode ser nulo
    fun obterCoordenadas(endereco: String): Coordenadas? {
        // "return try": o resultado do try/catch vira o retorno da função
        return try {
            // Começa a montar a URL a partir da base (ex.: .../geocode/json)
            val uri = UriComponentsBuilder.fromUriString(url)
                // Adiciona o parâmetro ?address=<endereço>
                .queryParam("address", endereco)
                // Adiciona o parâmetro &key=<chave da API>
                .queryParam("key", apiKey)
                // Constrói o objeto UriComponents com os parâmetros
                .build()
                // Aplica o encoding (espaços, acentos e caracteres especiais)
                .encode()
                // Converte para java.net.URI, que o RestTemplate aceita
                .toUri()

            // Faz o GET e converte o JSON em Map.
            // Se o corpo vier vazio (null), encerra a função devolvendo null
            val resposta = restTemplate.getForObject(uri, Map::class.java)
                ?: return null

            // Lê o campo "status" do JSON; "as?" faz cast seguro (null se não for String)
            val status = resposta["status"] as? String
            // O Google devolve HTTP 200 mesmo em erro, então checamos o status do JSON
            if (status != "OK") {
                // Registra um aviso com o status recebido (ZERO_RESULTS, REQUEST_DENIED...)
                logger.warn("Geocoding retornou status '{}' para o endereço '{}'", status, endereco)
                // Sem resultado válido, devolve null
                return null
            }

            // Pega a lista "results" do JSON (cast seguro para List)
            val results = resposta["results"] as? List<*>
            // Pega o primeiro resultado; se a lista estiver vazia ou o tipo for outro, devolve null
            val primeiro = results?.firstOrNull() as? Map<*, *> ?: return null
            // Dentro do resultado, pega o objeto "geometry"
            val geometry = primeiro["geometry"] as? Map<*, *> ?: return null
            // Dentro de geometry, pega o objeto "location" (onde estão lat e lng)
            val location = geometry["location"] as? Map<*, *> ?: return null

            // Lê "lat" como Number (o JSON pode trazer Int ou Double) e converte para Double
            val lat = (location["lat"] as? Number)?.toDouble() ?: return null
            // Faz o mesmo para "lng" (longitude)
            val lng = (location["lng"] as? Number)?.toDouble() ?: return null

            // Último valor do bloco try: é o que a função retorna em caso de sucesso
            Coordenadas(lat, lng)
            // Captura qualquer exceção (sem internet, timeout, JSON inesperado, etc.)
        } catch (e: Exception) {
            // Registra o erro no log com a mensagem da exceção
            logger.error("Erro ao consultar a API do Google Maps: {}", e.message)
            // Em caso de erro, devolve null
            null
        }
    }
}