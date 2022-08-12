package ru.turaev.goods.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Накладная для добавления
 */
@Data
@Entity
@Table(name = "adding_invoices")
public class AddingInvoice extends Invoice {
}
