package io.glory.pips.domain.repository.personal;

import java.util.Optional;

import io.glory.pips.domain.entity.personal.PersonalData;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

    @Query("""
            select pd
              from PersonalData pd
              left join fetch pd.privacyInfo pi
             where pi.id = :id
            """)
    @EntityGraph(attributePaths = {"privacyInfo"})
    Optional<PersonalData> fetchByPrivacyInfoId(Long id);

}