package io.glory.mcore.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * 유효기간 계산기
 */
public class ExpireDateCalculator {

    private ExpireDateCalculator() {
    }

    /**
     * 유효기간 만료 여부를 반환 한다
     * <ul>
     *     <li>expireDate = 오늘, false</li>
     *     <li>expireDate = 내일, false</li>
     *     <li>expireDate = 어제, true</li>
     * </ul>
     *
     * @param expireDate 유효기간
     */
    public static boolean isExpired(LocalDate expireDate) {
        return isExpired(expireDate, LocalDate.now());
    }

    /**
     * 비교 대상 날짜 부터 유효기간이 만료 여부를 반환 한다
     *
     * @param expireDate 유효기간
     * @param targetDate 비교 대상 날짜
     */
    public static boolean isExpired(LocalDate expireDate, LocalDate targetDate) {
        return expireDate.isBefore(targetDate);
    }

    /**
     * 유효기간이 만료된 날짜 일수를 반환 한다
     * <ul>
     *     <li>expireDate = 오늘, 0</li>
     *     <li>expireDate = 내일, -1</li>
     *     <li>expireDate = 어제, 1</li>
     * </ul>
     *
     * @param expireDate 유효기간
     */
    public static int expiredDays(LocalDate expireDate) {
        return expiredDays(expireDate, LocalDate.now());
    }

    /**
     * 비교 대상 날짜 부터 유효기간이 만료된 날짜 일수를 반환 한다
     *
     * @param expireDate 유효기간
     * @param targetDate 비교 대상 날짜
     */
    public static int expiredDays(LocalDate expireDate, LocalDate targetDate) {
        return (int)ChronoUnit.DAYS.between(expireDate, targetDate);
    }

    /**
     * 유효기간 까지 남은 일수를 반환 한다
     * <ul>
     *     <li>expireDate = 오늘, 0</li>
     *     <li>expireDate = 내일, 1</li>
     *     <li>expireDate = 어제, -1</li>
     * </ul>
     *
     * @param expireDate 유효기간
     */
    public static int daysToExpireDate(LocalDate expireDate) {
        return daysToExpireDate(expireDate, LocalDate.now());
    }

    /**
     * 비교 대상 날짜 부터 유효기간 까지 남은 일수를 반환 한다
     *
     * @param expireDate 유효기간
     * @param targetDate 비교 대상 날짜
     */
    public static int daysToExpireDate(LocalDate expireDate, LocalDate targetDate) {
        return (int)ChronoUnit.DAYS.between(targetDate, expireDate);
    }

}
