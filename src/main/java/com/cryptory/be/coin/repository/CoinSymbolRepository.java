package com.cryptory.be.coin.repository;

import com.cryptory.be.coin.domain.CoinSymbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinSymbolRepository extends JpaRepository<CoinSymbol, Long> {
}
