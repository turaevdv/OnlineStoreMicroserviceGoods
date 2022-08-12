package ru.turaev.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.turaev.goods.model.Accounting;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountingRepository extends JpaRepository<Accounting, Long> {
    @Query("SELECT a FROM Accounting a WHERE a.product.id = :productId AND a.storehouseId = :storehouseId")
    Optional<Accounting> findAccountingByProductIdAndStorehouseId(long productId, long storehouseId);

    @Query("SELECT a FROM Accounting a WHERE a.product.id = :productId")
    List<Accounting> findAllByProductId(long productId);

    @Query("SELECT a FROM Accounting a WHERE a.storehouseId = :storehouseId")
    List<Accounting> findAllByStorehouseId(long storehouseId);
}
