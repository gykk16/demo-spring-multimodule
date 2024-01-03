package io.glory.pips.domain.query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import com.querydsl.core.annotations.QueryProjection;
import io.glory.pips.data.constants.bank.BankCode;

@Getter
@ToString
public class PrivacyInfoAllDto {

    private Long          id;
    private UUID          dataUuid;
    //
    private String        name;
    private String        mobileNo;
    private String        phoneNo;
    private LocalDate     birthDate;
    //
    private BankCode      bankCode;
    private String        accountNo;
    private String        holder;
    //
    private LocalDateTime regDt;

    @QueryProjection
    @Builder
    public PrivacyInfoAllDto(Long id, UUID dataUuid,
            String name, String mobileNo, String phoneNo, LocalDate birthDate,
            BankCode bankCode, String accountNo, String holder,
            LocalDateTime regDt) {
        this.id = id;
        this.dataUuid = dataUuid;
        this.name = name;
        this.mobileNo = mobileNo;
        this.phoneNo = phoneNo;
        this.birthDate = birthDate;
        this.bankCode = bankCode;
        this.accountNo = accountNo;
        this.holder = holder;
        this.regDt = regDt;
    }

}
