package com.fertilizeo.repository;

import com.fertilizeo.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory , Long > {
    @Query("SELECT sh FROM StockHistory sh WHERE sh.stock.idstock = :stockId")
    List<StockHistory> findByStockId(@Param("stockId") Long stockId);
}
