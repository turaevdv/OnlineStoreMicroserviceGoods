package ru.turaev.goods.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Таблица, в которой хранятся данные о количестве товара на складах,
 * а также о том, производится ли товар в настоящее время (если нет, товар считать удалённым)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounting")
public class Accounting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column(name = "storehouse_id", nullable = false)
    private long storehouseId;

    @Column(nullable = false)
    private int quantity;
}
