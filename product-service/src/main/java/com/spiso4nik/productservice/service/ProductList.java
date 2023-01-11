package com.spiso4nik.productservice.service;

public interface ProductList<T> {
    T getProductsByName(String nameProduct);
}
