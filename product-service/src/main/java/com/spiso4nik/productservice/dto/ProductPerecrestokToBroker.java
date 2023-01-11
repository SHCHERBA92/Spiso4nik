package com.spiso4nik.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPerecrestokToBroker {
    String balanceState;
    String title;
    double priceTag;
    String image;
}
