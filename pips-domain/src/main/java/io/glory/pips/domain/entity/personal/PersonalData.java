package io.glory.pips.domain.entity.personal;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import io.glory.pips.domain.entity.base.BaseEntity;
import io.glory.pips.domain.entity.privacy.PrivacyInfo;
import org.hibernate.annotations.Comment;

/**
 * 개인 식별 정보
 */
@Entity
@Table(name = "personal_data")
@SequenceGenerator(
        name = "seq_personal_data_id_generator",
        sequenceName = "seq_personal_data_id",
        initialValue = 1,
        allocationSize = 10
)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class PersonalData extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_personal_data_id_generator")
    private Long id;

    @Comment("이름")
    private String name;

    @Comment("생년월일")
    private LocalDate birthDate;

    @Comment("휴대폰 번호")
    private String mobileNo;

    @Comment("연락처")
    private String phoneNo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "privacy_info_id")
    @ToString.Exclude
    private PrivacyInfo privacyInfo;

    @Builder
    private PersonalData(String name, LocalDate birthDate, String mobileNo, String phoneNo, PrivacyInfo privacyInfo) {
        this.name = name;
        this.birthDate = birthDate;
        this.mobileNo = mobileNo;
        this.phoneNo = phoneNo;
        this.privacyInfo = privacyInfo;
    }
}
