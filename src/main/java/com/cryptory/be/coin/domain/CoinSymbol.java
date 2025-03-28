package com.cryptory.be.coin.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coin_symbols")
public class CoinSymbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;
    @Column(columnDefinition = "TEXT")
    private String logoUrl;

    @Builder
    public CoinSymbol(String color, String logoUrl) {
        this.color = color;
        this.logoUrl = logoUrl;
    }
}
