package com.spiso4nik.productservice.service.perecrestok;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spiso4nik.productservice.dto.ProductPerecrestokToBroker;
import com.spiso4nik.productservice.dto.ProductsPerecrestokResponse;
import com.spiso4nik.productservice.service.ProductList;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Product implements ProductList {

    private final AuthenticationPerecrestok authenticationPerecrestok;
    private final WebClient.Builder webClient;
    private final ObjectMapper objectMapper;

    private final String urlApi = "https://www.perekrestok.ru/api/customer/1.4.1.0/catalog/search/all?textQuery=";
    private final String urlSufix = "&entityTypes[]=category&entityTypes[]=product";
    private final MultiValueMap<String, String> authenticationFromStore;

    public Product(AuthenticationPerecrestok authenticationPerecrestok,
                   WebClient.Builder webClient,
                   ObjectMapper objectMapper) {
        this.authenticationPerecrestok = authenticationPerecrestok;
        this.webClient = webClient;
        authenticationFromStore = authenticationPerecrestok.getAuthenticationFromStore();
        this.objectMapper = objectMapper;
    }


    @Override
    public Flux<ProductPerecrestokToBroker> getProductsByName(String nameProduct) {
        String strUrl = urlApi + nameProduct + urlSufix;

//        var stringMono = webClient.build()
//                .get()
//                .uri(strUrl)
////                .accept(MediaType.APPLICATION_JSON_UTF8)
//                .cookies(stringStringMultiValueMap -> stringStringMultiValueMap.putAll(authenticationFromStore))
//                .header("auth", "Bearer " + authenticationFromStore.getFirst("accessToken"))
//                .retrieve()
//                .bodyToMono(ProductsPerecrestokResponse.class);

        return webClient.build()
                .get()
                .uri(strUrl)
//                .accept(MediaType.APPLICATION_JSON_UTF8)
                .cookies(stringStringMultiValueMap -> stringStringMultiValueMap.putAll(authenticationFromStore))
                .header("auth", "Bearer " + authenticationFromStore.getFirst("accessToken"))
                .retrieve()
                .bodyToMono(String.class)
                .map(s -> {
                    List<ProductPerecrestokToBroker> productPerecrestokToBrokers = null;
                    try {
                        List<JsonNode> jsonNodes = objectMapper.readTree(s).findParent("content").findParent("products").findParents("category");
                         productPerecrestokToBrokers = jsonNodes.stream().map(jsonNode -> {
                            String title = jsonNode.get("title").asText();
                            String balanceState = jsonNode.get("balanceState").asText();
                            String image = String.format(jsonNode.findValue("image").get("cropUrlTemplate").asText(), "400x400-fit");
                            double price = jsonNode.findValue("priceTag").get("price").asDouble() / 100;
                            return new ProductPerecrestokToBroker(balanceState, title, price, image);
                        }).collect(Collectors.toList());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return productPerecrestokToBrokers;
                }).flatMapIterable(productPerecrestokToBrokers -> productPerecrestokToBrokers);
    }
}
