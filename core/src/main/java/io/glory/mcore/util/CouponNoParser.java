package io.glory.mcore.util;

/**
 * 쿠폰번호 파서
 */
public class CouponNoParser {

    private static final String DEFAULT_DELIMITER = "-";

    private CouponNoParser() {
    }

    /**
     * 쿠폰번호에 구분자(-)를 추가 한다
     *
     * @param couponNo 쿠폰번호
     * @return 구분자가 추가된 쿠폰번호
     */
    public static String addDelimiter(String couponNo) {
        return addDelimiter(couponNo, DEFAULT_DELIMITER);
    }

    /**
     * 쿠폰번호에 구분자를 추가 한다
     *
     * @param couponNo  쿠폰번호
     * @param delimiter 구분자
     * @return 구분자가 추가된 쿠폰번호
     */
    public static String addDelimiter(String couponNo, String delimiter) {
        int blockSize = getBlockSize(couponNo.length());
        return addDelimiter(couponNo, delimiter, blockSize);
    }

    private static String addDelimiter(String value, String delimiter, int blockSize) {
        String cleanValue = value.strip().replace(" ", "");
        int blocks = (cleanValue.length() + blockSize - 1) / blockSize;

        StringBuilder sb = new StringBuilder(cleanValue);
        for (int i = 1; i < blocks; i++) {
            sb.insert(i * blockSize + (i - 1), delimiter);
        }

        return sb.toString();
    }

    private static int getBlockSize(int length) {
        if (length > 16) {
            return 5;
        }
        if (length % 5 == 0) {
            return 5;
        }
        if (length % 4 == 0) {
            return 4;
        }
        if (length % 3 == 0) {
            return 3;
        }
        return 4;
    }

}
