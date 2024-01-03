package io.glory.pips.data.constants;

import lombok.Getter;

@Getter
public enum SystemAlias implements BaseCode {

    PRIVACY_APP("privacy-app", "개인정보 처리 시스템"),
    ALL("*", "모든 서비스"),
    ;

    private final String systemAlias;
    private final String description;

    SystemAlias(String systemAlias, String description) {
        this.systemAlias = systemAlias;
        this.description = description;
    }
}
