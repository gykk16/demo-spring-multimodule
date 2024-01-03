package io.glory.pips.data.constants.bank;

import lombok.Getter;

import io.glory.pips.data.constants.BaseCode;

/**
 * 은행 코드
 */
@Getter
public enum BankCode implements BaseCode {

    TEST_BANK("0000", "테스트 은행");

    private final String code;
    private final String description;

    BankCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
