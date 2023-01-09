package ru.spiso4nik.accountservice.models.orderList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.spiso4nik.accountservice.models.account.AccountModel;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "list_of_goods")
public class GlobalSpisokModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "GlobalSpisokModel", sequenceName = "GlobalSpisokModel_seq")
    private Long id;

    @Column(name = "create_date")
    private LocalDate createAt;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @Column(unique = false, name = "name_store")
    private String storeName;

    @Column(unique = false, name = "name_shop_list")
    private String nameOfShopList;

    @OneToMany(mappedBy = "globalSpisokModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<GoodsModel> goodsModels;

    @ManyToOne
    @JoinColumn(name = "account_id")
    AccountModel accountModel;
}
