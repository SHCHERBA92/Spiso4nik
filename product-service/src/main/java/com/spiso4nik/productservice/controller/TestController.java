package com.spiso4nik.productservice.controller;

import com.spiso4nik.productservice.dto.ProductPerecrestokToBroker;
import com.spiso4nik.productservice.dto.ProductsPerecrestokResponse;
import com.spiso4nik.productservice.service.perecrestok.AuthenticationPerecrestok;
import com.spiso4nik.productservice.service.perecrestok.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
    private final AuthenticationPerecrestok authenticationPerecrestok;
    private final Product product;

    public TestController(AuthenticationPerecrestok authenticationPerecrestok,
                          Product product) {
        this.authenticationPerecrestok = authenticationPerecrestok;
        this.product = product;
    }

    @GetMapping("perecrestok")
    public void getSomeThing(){
        authenticationPerecrestok.getAuthenticationFromStore();
    }

    @GetMapping("perecrestok/products")
    public Flux<ProductPerecrestokToBroker> getProduct(@RequestParam(name = "name") String nameProduct){
        Flux<ProductPerecrestokToBroker> productsByName = product.getProductsByName(nameProduct);
        return productsByName;
    }
}
