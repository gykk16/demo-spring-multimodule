package io.glory.pips.app.service;

import lombok.Getter;

@Getter
public enum PrivacyInfoSearchRange {

    ALL("전체"),
    PERSONAL_DATA("개인 신상 정보"),
    BANK_ACCOUNT("계좌 정보");

    private final String description;

    PrivacyInfoSearchRange(String description) {
        this.description = description;
    }

}
