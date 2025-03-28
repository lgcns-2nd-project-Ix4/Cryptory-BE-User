package com.cryptory.be.global.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateFormat{

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter INPUT_TIME_FORMAT = DateTimeFormatter.ofPattern("HHmmss");
    private static final DateTimeFormatter OUTPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }

    // 최근 거래 시간인 timestamp 포맷팅
    public static String formatTimestamp(long timestamp) {
        ZonedDateTime dateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.of("UTC"));
        return dateTime.format(TIMESTAMP_FORMATTER);
    }

    public static String formatNewsDate(String inputDate) throws ParseException {
        // 입력 날짜 형식 (영어 요일, 월 이름을 포함하므로 Locale.ENGLISH 지정)
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

        // 출력 날짜 형식 (yyyy-MM-dd)
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 입력 문자열을 Date 객체로 파싱
        Date date = inputFormat.parse(inputDate);

        // Date 객체를 원하는 형식의 문자열로 변환
        return outputFormat.format(date);
    }

    public static String formatTradeTime(String tradeDate, String tradeTime) {
        LocalDate date = LocalDate.parse(tradeDate, INPUT_DATE_FORMAT);
        LocalTime time = LocalTime.parse(tradeTime, INPUT_TIME_FORMAT);

        LocalDateTime dateTime = LocalDateTime.of(date, time);

        return dateTime.format(OUTPUT_DATE_FORMAT); // "yyyy-MM-dd HH:mm" 형식으로 변환
    }
}
