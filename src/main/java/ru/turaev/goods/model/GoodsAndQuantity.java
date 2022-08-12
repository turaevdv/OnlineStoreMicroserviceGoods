package ru.turaev.goods.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Класс, который нужен для отображения количества единиц товара одного наименования
 */
@Data
@Entity
@Table(name = "goods_and_quantity")
public class GoodsAndQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;
}
