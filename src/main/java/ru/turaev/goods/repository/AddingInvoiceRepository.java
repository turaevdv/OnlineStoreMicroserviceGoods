package ru.turaev.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.turaev.goods.model.AddingInvoice;

@Repository
public interface AddingInvoiceRepository extends JpaRepository<AddingInvoice, Long> {
}
