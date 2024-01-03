package io.glory.pips.domain.entity.bank;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import io.glory.pips.data.constants.bank.BankCode;
import io.glory.pips.domain.entity.base.BaseEntity;
import io.glory.pips.domain.entity.privacy.PrivacyInfo;
import org.hibernate.annotations.Comment;

/**
 * 개인 식별 정보
 */
@Entity
@Table(name = "bank_account")
@SequenceGenerator(
        name = "seq_bank_account_id_generator",
        sequenceName = "seq_bank_account_id",
        initialValue = 1,
        allocationSize = 10
)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class BankAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_bank_account_id_generator")
    private Long id;

    @Comment("은행 코드")
    @Enumerated(EnumType.STRING)
    private BankCode bankCode;

    @Comment("계좌번호")
    private String accountNo;

    @Comment("예금주")
    private String holder;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "privacy_info_id")
    @ToString.Exclude
    private PrivacyInfo privacyInfo;

    public BankAccount(BankCode bankCode, String accountNo, String holder, PrivacyInfo privacyInfo) {
        this.bankCode = bankCode;
        this.accountNo = accountNo;
        this.holder = holder;
        this.privacyInfo = privacyInfo;
    }

}
