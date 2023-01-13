package com.spiso4nik.productservice.controller;

import com.spiso4nik.productservice.dto.ProductToBroker;
import com.spiso4nik.productservice.service.auchan.ProductAuchan;
import com.spiso4nik.productservice.service.magnit.ProductMagnit;
import com.spiso4nik.productservice.service.perecrestok.AuthenticationPerecrestok;
import com.spiso4nik.productservice.service.perecrestok.ProductPerecrestok;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class TestController {
    private final AuthenticationPerecrestok authenticationPerecrestok;
    private final ProductPerecrestok productPerecrestok;
    private final ProductMagnit productMagnit;
    private final ProductAuchan productAuchan;

    public TestController(AuthenticationPerecrestok authenticationPerecrestok,
                          ProductPerecrestok productPerecrestok,
                          ProductMagnit productMagnit,
                          ProductAuchan productAuchan) {
        this.authenticationPerecrestok = authenticationPerecrestok;
        this.productPerecrestok = productPerecrestok;
        this.productMagnit = productMagnit;
        this.productAuchan = productAuchan;
    }

    @GetMapping("perecrestok")
    public void getSomeThing() {
        authenticationPerecrestok.getAuthenticationFromStore();
    }

    @GetMapping("perecrestok/products")
    public Flux<ProductToBroker> getProduct(@RequestParam(name = "name") String nameProduct) {
        Flux<ProductToBroker> productsByName = productPerecrestok.getProductsByName(nameProduct);
        return productsByName;
    }

    @GetMapping("magnit/products")
    public Flux<ProductToBroker> getProductMagnit(@RequestParam(name = "name") String nameProduct) {
        var productsByName = productMagnit.getProductsByName(nameProduct);
        return productsByName;
    }

    @GetMapping("auchan/products")
    public Flux<ProductToBroker> getProductAuchan(@RequestParam(name = "name") String nameProduct) {
        var productsByName = productAuchan.getProductsByName(nameProduct);
        return productsByName;
    }
}
