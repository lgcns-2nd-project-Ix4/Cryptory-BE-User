package com.cryptory.be.openapi.service;

import com.cryptory.be.chart.exception.ChartErrorCode;
import com.cryptory.be.chart.exception.ChartException;
import com.cryptory.be.coin.exception.CoinErrorCode;
import com.cryptory.be.coin.exception.CoinException;
import com.cryptory.be.openapi.client.UpbitClient;
import com.cryptory.be.openapi.dto.Candle;
import com.cryptory.be.openapi.dto.Market;
import com.cryptory.be.openapi.dto.Ticker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpbitService {

    private final UpbitClient upbitClient;

    private final static String START_UNIT = "KRW";

    // 코인이 너무 많아서 KRW로 거래되는 코인들만 조회
    public List<Market> getCoinsFromUpbit() {
        try {
            return upbitClient.getCoins().stream()
                    .filter(market -> market.getMarket().startsWith("KRW"))
                    .toList();
        } catch (Exception e) {
            throw new CoinException(CoinErrorCode.COIN_LOAD_FAILED);
        }
    }

    // 코인 차트 가져오기
    public List<Candle> getCharts(String market) {
        try {
            if (!market.startsWith(START_UNIT)) {
                throw new ChartException(ChartErrorCode.TICKER_INVALID_REQUEST);
            }

            return upbitClient.getCharts(market);
        } catch (Exception e) {
            throw new ChartException(ChartErrorCode.CHART_LOAD_FAILED);
        }
    }

    // 코인들 현재가 가져오기
    public List<Ticker> getTickers(String... market) {
        try {
            for (String m : market) {
                if (!m.startsWith(START_UNIT)) {
                    throw new ChartException(ChartErrorCode.TICKER_INVALID_REQUEST);
                }
            }

            return upbitClient.getTickers(market);
        } catch (Exception e) {
            throw new ChartException(ChartErrorCode.TICKER_LOAD_FAILED);
        }
    }

}
