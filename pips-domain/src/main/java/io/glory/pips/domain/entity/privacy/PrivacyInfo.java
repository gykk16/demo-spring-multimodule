package io.glory.pips.domain.entity.privacy;

import static lombok.AccessLevel.PROTECTED;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

import io.glory.pips.domain.entity.base.BaseEntity;
import org.hibernate.annotations.Comment;

/**
 * 개인정보 마스터 테이블
 */
@Entity
@Table(name = "privacy_info")
@SequenceGenerator(
        name = "seq_privacy_info_id_generator",
        sequenceName = "seq_privacy_info_id",
        initialValue = 1,
        allocationSize = 10
)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class PrivacyInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_privacy_info_id_generator")
    private Long id;

    @Comment("개인정보 고유 uuid")
    private UUID dataUuid;

    @Comment("정보 소유자 id")
    private String ownerId;

    public PrivacyInfo(String ownerId) {
        this.dataUuid = UUID.randomUUID();
        this.ownerId = ownerId;
    }

}
