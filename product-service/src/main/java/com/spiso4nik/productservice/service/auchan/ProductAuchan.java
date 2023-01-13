package com.spiso4nik.productservice.service.auchan;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spiso4nik.productservice.dto.ProductToBroker;
import com.spiso4nik.productservice.service.ProductList;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductAuchan implements ProductList<Flux<ProductToBroker>> {

    private final ObjectMapper objectMapper;
    private final WebClient.Builder webClient;

    public ProductAuchan(ObjectMapper objectMapper,
                         WebClient.Builder webClient) {
        this.objectMapper = objectMapper;
        this.webClient = webClient;
    }

    @Override
    public Flux<ProductToBroker> getProductsByName(String nameProduct) {
        return webClient.build().get()
                .uri(uriBuilder -> uriBuilder.scheme("https")
                        .host("sort.diginetica.net")
                        .path("/search")
                        .queryParam("st", nameProduct)
                        .queryParam("apiKey", "06U4652632")
                        .queryParam("strategy", List.of("vectors_extended", "zero_queries"))
                        .queryParam("fullData", true)
                        .queryParam("withCorrection", true)
                        .queryParam("withFacets", true)
                        .queryParam("treeFacets", true)
                        .queryParam("regionId", 1)  // Выбор от магазина, от этого так же зависит количество
                        .queryParam("useCategoryPrediction", false)
                        .queryParam("size", 20)
                        .queryParam("offset", 0)
                        .queryParam("showUnavailable", false)
                        .queryParam("unavailableMultiplier", 0.2)
                        .queryParam("preview", false)
                        .queryParam("withSku", false)
                        .queryParam("sort", "DEFAULT") //DEFAULT  PRICE_ASC
                        .build()
                ).retrieve()
                .bodyToMono(String.class)
                .map(str -> {
                    List<ProductToBroker> productToBrokers = null;
                    try {
                        List<JsonNode> products = (List<JsonNode>) objectMapper.readerForListOf(JsonNode.class).readValue(objectMapper.readTree(str).findValue("products"));
                        productToBrokers = products.stream().map(jsonNode -> {
                            String name = jsonNode.get("name").asText();
                            double price = jsonNode.get("price").asDouble();
                            String imageUrl = jsonNode.get("image_url").asText();
                            String count = null;
                            try {
                                count = ((List<JsonNode>) objectMapper.readerForListOf(JsonNode.class).readValue(jsonNode.findValue("attributes").get("доступное количество"))).stream()
                                        .map(JsonNode::asText)
                                        .map(s -> {
                                            String[] strings = s.split(":");
                                            if (Integer.parseInt(strings[0]) == 1) {   // фильтр будет по магазину
                                                return strings[1];
                                            }
                                            return null;
                                        })
                                        .filter(Objects::nonNull)
                                        .findFirst().get();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return new ProductToBroker(count, name, price, imageUrl);
                        }).collect(Collectors.toList());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return productToBrokers;
                }).flatMapIterable(productToBrokers -> productToBrokers);
    }
}
