package ru.turaev.goods.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Класс описывает товар, который когда-либо находился на складе
 */
@Data
@Entity
@Table(name = "products")
@Where(clause = "is_produced = true")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String type;

    @Column(nullable = false)
    private int price;

    @Column(name = "producing_country", nullable = false)
    private String producingCountry;

    @Column(nullable = false)
    private double weight;

    private String material;

    /** Будет ли товар поставляться в дальнейшем */
    @Column(name = "is_produced", nullable = false)
    private boolean isProduced;
}
