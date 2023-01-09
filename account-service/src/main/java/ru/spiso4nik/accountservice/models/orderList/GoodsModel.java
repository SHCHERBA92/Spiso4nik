package ru.spiso4nik.accountservice.models.orderList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "goods")
public class GoodsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "good_model", sequenceName = "sequence_good_model")
    private Long id;

    @Column(name = "good_name")
    private String name;

    @Column(name = "good_image")
    private String img;

    @Column(name = "good_price")
    private BigDecimal price;

    @Column(name = "good_count")
    private Integer count;

    @Column(name = "good_status")
    @Enumerated(EnumType.STRING)
    private RoleOfStatus roleOfStatus;

    @ManyToOne
    @JoinColumn(name = "list_of_goods_id")
    private GlobalSpisokModel globalSpisokModel;
}
