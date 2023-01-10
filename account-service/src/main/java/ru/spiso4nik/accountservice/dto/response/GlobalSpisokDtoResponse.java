package ru.spiso4nik.accountservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.spiso4nik.accountservice.models.account.AccountModel;
import ru.spiso4nik.accountservice.models.orderList.GoodsModel;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GlobalSpisokDtoResponse {
    private LocalDate createAt;

    private LocalDate dateTo;

//    private String storeName;

    private String nameOfShopList;

    private List<GoodsModel> goodsModels;

    private AccountModel accountModel;
}
