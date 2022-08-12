package ru.turaev.goods.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Накладная
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoices")
@Inheritance(strategy = InheritanceType.JOINED)
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** Список товаров, для каждого из которых указано количество единиц */
    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "invoice_id", referencedColumnName = "id", nullable = false)
    private List<GoodsAndQuantity> goodsAndQuantities;

    @Column(name = "storehouse_id", nullable = false)
    private long storehouseId;

    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

    @Column(name = "invoice_time", nullable = false)
    @DateTimeFormat(pattern = "kk:mm:ss")
    private LocalTime invoiceTime;
}
