package ru.spiso4nik.accountservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDtoRequest {
    private String name;

    private String img;

    private BigDecimal price;

    private Integer count;
}
