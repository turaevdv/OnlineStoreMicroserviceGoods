package ru.turaev.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.turaev.goods.model.DebitingInvoice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DebitingInvoiceRepository extends JpaRepository<DebitingInvoice, Long> {
    List<DebitingInvoice> findByIsConfirmedIsFalseAndIsDeletedIsFalse();
    Optional<DebitingInvoice> findByIdAndIsDeletedIsFalse(long id);

    @Query("select d from DebitingInvoice d where d.storehouseId = :id and d.invoiceDate between :begin and :end and d.isConfirmed = true ORDER BY d.invoiceDate")
    List<DebitingInvoice> getAllConfirmedDebitingInvoicesOfStorehouseByPeriod(@Param("id") long id, @Param("begin") LocalDate begin, @Param("end") LocalDate end);
}
