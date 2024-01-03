package io.glory.mcore.util;


/**
 * 번호 유틸
 */
public class PhoneUtil {

    private PhoneUtil() {
    }

    /**
     * 번호 유효성 검사
     *
     * @param number 번호
     * @return 유효성 여부
     */
    public static boolean isValidNo(String number) {
        return isValidMobileNo(number) || isValidPhoneNo(number);
    }

    /**
     * 휴대폰 번호 유효성 검사
     *
     * @param number 휴대폰 번호
     * @return 유효성 여부
     */
    public static boolean isValidMobileNo(String number) {
        return number.matches(RegexConst.MOBILE_NO_REGEX);
    }

    /**
     * 전화번호 유효성 검사
     *
     * @param number 전화번호
     * @return 유효성 여부
     */
    public static boolean isValidPhoneNo(String number) {
        return number.matches(RegexConst.PHONE_NO_REGEX);
    }

    /**
     * 번호에서 구분자를 제거한다
     *
     * @param number 번호
     * @return 구분자가 제거된 번호
     */
    public static String removeDelimiter(String number) {
        if (number == null) {
            return "";
        }
        return number.replaceAll(RegexConst.ALLOW_ONLY_NUMBER_REGEX, "");
    }

}
