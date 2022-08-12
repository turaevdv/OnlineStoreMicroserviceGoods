package ru.turaev.goods.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Накладная для списания
 */
@Data
@Entity
@Table(name = "debiting_invoices")
public class DebitingInvoice extends Invoice {
    @Column(name = "is_confirmed", nullable = false)
    private boolean isConfirmed;

    @Column(name = "is_deleted", nullable = false)
     private boolean isDeleted;
}
