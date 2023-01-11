package com.spiso4nik.productservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsPerecrestokResponse implements Serializable {
    @JsonProperty("content")
    ContentProductPerecrestok contentProductPerecrestok;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class ContentProductPerecrestok{
        @JsonProperty("products")
        private List<ProductPerecrestokResponse> productPerecrestokResponse;

        @Setter
        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        static class ProductPerecrestokResponse{
            private String balanceState;
            private String title;
            private PriceTag priceTag;
            private Image image;

            @Setter
            @Getter
            @AllArgsConstructor
            @NoArgsConstructor
            static class PriceTag{
                private int price;
            }

            @Setter
            @Getter
            @AllArgsConstructor
            @NoArgsConstructor
            static class Image{
                private String cropUrlTemplate;
            }
        }
    }
}
