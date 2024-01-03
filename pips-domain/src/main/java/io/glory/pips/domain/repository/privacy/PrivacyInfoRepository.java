package io.glory.pips.domain.repository.privacy;

import io.glory.pips.domain.entity.privacy.PrivacyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivacyInfoRepository extends JpaRepository<PrivacyInfo, Long> {

}