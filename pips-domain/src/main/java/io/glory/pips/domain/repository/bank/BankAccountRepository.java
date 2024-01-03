package io.glory.pips.domain.repository.bank;

import java.util.Optional;

import io.glory.pips.domain.entity.bank.BankAccount;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Query("""
            select ba
              from BankAccount ba
              left join fetch ba.privacyInfo pi
             where pi.id = :id
            """)
    @EntityGraph(attributePaths = {"privacyInfo"})
    Optional<BankAccount> fetchByPrivacyInfoId(Long id);

}