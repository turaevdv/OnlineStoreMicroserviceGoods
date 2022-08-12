package ru.turaev.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.turaev.goods.model.DebitingInvoice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DebitingInvoiceRepository extends JpaRepository<DebitingInvoice, Long> {
    List<DebitingInvoice> findByIsConfirmedIsFalseAndIsDeletedIsFalse();
    Optional<DebitingInvoice> findByIdAndIsDeletedIsFalse(long id);
}
