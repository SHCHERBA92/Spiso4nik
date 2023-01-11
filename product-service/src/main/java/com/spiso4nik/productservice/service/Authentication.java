package com.spiso4nik.productservice.service;

public interface Authentication<T> {
    T getAuthenticationFromStore();
}
