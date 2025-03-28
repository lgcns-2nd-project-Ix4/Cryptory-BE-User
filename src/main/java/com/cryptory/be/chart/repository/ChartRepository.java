package com.cryptory.be.chart.repository;

import com.cryptory.be.chart.domain.Chart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChartRepository extends JpaRepository<Chart, Long> {
    List<Chart> findAllByCoinId(Long coinId);

    //Optional<Chart> findByDateAndCoinId(String date, Long coinId);

    @Query("SELECT c FROM Chart c WHERE c.date LIKE :dateStr AND c.coin.id = :coinId")
    Optional<Chart> findByDateAndCoinId(@Param("dateStr") String dateStr, @Param("coinId") Long coinId);

}
