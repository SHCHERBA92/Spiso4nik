package com.spiso4nik.productservice.service.magnit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spiso4nik.productservice.dto.ProductToBroker;
import com.spiso4nik.productservice.service.ProductList;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductMagnit implements ProductList<Flux<ProductToBroker>> {

    private final ObjectMapper objectMapper;
    private final WebClient.Builder webClient;

    public ProductMagnit(ObjectMapper objectMapper,
                         WebClient.Builder webClient) {
        this.objectMapper = objectMapper;
        this.webClient = webClient;
    }

    @Override
    public Flux<ProductToBroker> getProductsByName(String nameProduct) {
        return webClient.build().get()
                .uri(uriBuilder -> uriBuilder.scheme("https")
                        .host("dostavka.magnit.ru")
                        .path("/api/catalog/product-list/search")
                        .queryParam("q", nameProduct)
                        .queryParam("is_count", true)
                        .queryParam("division", 230026) // разные коды для разных городов
                        .queryParam("shop_code", 230026) // разные коды для разных городов
                        .queryParam("useSiteXmlIdService", "express")
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .map(s -> {
                    List<ProductToBroker> productToBrokers = null;
                    try {
                        List<JsonNode> jsonNodesItems = (List<JsonNode>) objectMapper.readerForListOf(JsonNode.class).readValue(objectMapper.readTree(s).findValue("items"));

                        productToBrokers = jsonNodesItems.stream().map(jsonNode -> {
                            String picture = "https://img-dostavka.magnit.ru/resize/184x184" + jsonNode.get("picture").asText();
                            JsonNode jsonNodeOffer = jsonNode.findValue("offers").get(0);
                            String name = jsonNodeOffer.get("name").asText();
                            double price = jsonNodeOffer.get("currentPrice").asDouble();
                            int quantity = jsonNodeOffer.get("quantity").asInt();

                            return new ProductToBroker(String.valueOf(quantity), name, price, picture);
                        }).collect(Collectors.toList());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return productToBrokers;
                }).flatMapIterable(productToBrokers -> productToBrokers);
    }
}
