package io.glory.pips.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.tuple;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.glory.pips.app.service.exception.PrivacyInfoErrorCode;
import io.glory.pips.app.service.exception.PrivacyInfoException;
import io.glory.pips.app.service.model.PrivacyInfoServiceRequest;
import io.glory.pips.data.constants.bank.BankCode;
import io.glory.pips.domain.entity.bank.BankAccount;
import io.glory.pips.domain.entity.personal.PersonalData;
import io.glory.pips.domain.entity.privacy.PrivacyInfo;
import io.glory.pips.domain.query.PrivacyInfoAllDto;
import io.glory.pips.domain.repository.bank.BankAccountRepository;
import io.glory.pips.domain.repository.personal.PersonalDataRepository;
import io.glory.pips.domain.repository.privacy.PrivacyInfoRepository;
import io.glory.testsupport.integrated.IntegratedTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PrivacyInfoServiceTest extends IntegratedTestSupport {

    @Autowired
    private PrivacyInfoService     privacyInfoService;
    @Autowired
    private PrivacyInfoRepository  privacyInfoRepository;
    @Autowired
    private PersonalDataRepository personalDataRepository;
    @Autowired
    private BankAccountRepository  bankAccountRepository;

    @AfterEach
    void tearDown() {
        bankAccountRepository.deleteAllInBatch();
        personalDataRepository.deleteAllInBatch();
        privacyInfoRepository.deleteAllInBatch();
    }

    @DisplayName("개인정보 id 로 조회 한다")
    @Test
    void when_fetchPrivacyInfo_expect_PrivacyInfoAllDto() throws Exception {
        // given
        String name = "홍길동";
        String mobileNo = "01012345678";
        String phoneNo = "021234567";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        BankCode bankCode = BankCode.TEST_BANK;
        String accountNo = "1234567890";

        Long id = savePrivacyInfo(1, name, mobileNo, phoneNo, birthDate, bankCode, accountNo).get(0);

        // when
        PrivacyInfoAllDto privacyInfoAllDto = privacyInfoService.fetchPrivacyInfo(id);

        // then
        System.out.println("==> privacyInfoAllDto = " + privacyInfoAllDto);
        assertThat(privacyInfoAllDto).isNotNull()
                .extracting("id", "name", "mobileNo", "phoneNo",
                        "birthDate", "bankCode", "accountNo")
                .contains(id, name, mobileNo, phoneNo,
                        birthDate, BankCode.TEST_BANK, accountNo);
        assertThat(privacyInfoAllDto.getRegDt()).isNotNull();
    }

    @DisplayName("개인정보 id 로 조회 결과가 없으면 DATA_NOT_FOUND 예외를 던진다")
    @Test
    void when_fetchPrivacyInfoIsEmpty_throw_DATA_NOT_FOUND() throws Exception {
        // given
        long nonExistId = 1L;

        // when
        assertThatThrownBy(() -> privacyInfoService.fetchPrivacyInfo(nonExistId))
                // then
                .isInstanceOf(PrivacyInfoException.class)
                .hasMessageContaining(PrivacyInfoErrorCode.DATA_NOT_FOUND.getCode());
    }

    @DisplayName("개인정보 id 리스트로 조회 한다")
    @Test
    void when_fetchPrivacyInfos_expect_PrivacyInfoAllDtoList() throws Exception {
        // given
        String name = "홍길동";
        String mobileNo = "01012345678";
        String phoneNo = "021234567";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        BankCode bankCode = BankCode.TEST_BANK;
        String accountNo = "1234567890";

        List<Long> ids = savePrivacyInfo(3, name, mobileNo, phoneNo, birthDate, bankCode, accountNo);

        // when
        List<PrivacyInfoAllDto> privacyInfoAllDtos = privacyInfoService.fetchPrivacyInfos(ids);

        // then
        assertThat(privacyInfoAllDtos).isNotNull()
                .hasSize(3)
                .extracting("id", "name", "mobileNo", "phoneNo",
                        "birthDate", "bankCode", "accountNo")
                .containsExactlyInAnyOrder(
                        tuple(ids.get(0), name, mobileNo, phoneNo,
                                birthDate, BankCode.TEST_BANK, accountNo),
                        tuple(ids.get(1), name, mobileNo, phoneNo,
                                birthDate, BankCode.TEST_BANK, accountNo),
                        tuple(ids.get(2), name, mobileNo, phoneNo,
                                birthDate, BankCode.TEST_BANK, accountNo)
                );
    }

    @DisplayName("개인정보 id 리스트로 조회 결과가 없으면 DATA_NOT_FOUND 예외를 던진다")
    @Test
    void when_fetchPrivacyInfosIsEmpty_throw_DATA_NOT_FOUND() throws Exception {
        // given
        List<Long> nonExistIds = List.of(1L, 2L, 3L);

        // when
        assertThatThrownBy(() -> privacyInfoService.fetchPrivacyInfos(nonExistIds))
                // then
                .isInstanceOf(PrivacyInfoException.class)
                .hasMessageContaining(PrivacyInfoErrorCode.DATA_NOT_FOUND.getCode());
    }

    @DisplayName("개인정보 를 저장한다")
    @Test
    void when_savePrivacyInfo_expect_DbInsert() throws Exception {
        // given
        String name = "홍길동";
        String mobileNo = "01012345678";
        String phoneNo = "021234567";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        BankCode bankCode = BankCode.TEST_BANK;
        String accountNo = "1234567890";
        PrivacyInfoServiceRequest serviceRequest = new PrivacyInfoServiceRequest(
                name, mobileNo, phoneNo, birthDate, bankCode, accountNo, name);

        // when
        Long savedId = privacyInfoService.savePrivacyInfo(serviceRequest);

        // then
        privacyInfoRepository.findById(savedId).ifPresentOrElse(privacyInfo -> {
                    assertThat(privacyInfo).isNotNull();
                    assertThat(privacyInfo.getRegDt()).isNotNull();
                    assertThat(privacyInfo.getDataUuid()).isNotNull();
                },
                () -> fail("저장된 개인정보가 없습니다."));
        personalDataRepository.fetchByPrivacyInfoId(savedId).ifPresentOrElse(personalData -> {
                    assertThat(personalData).isNotNull();
                    assertThat(personalData.getName()).isEqualTo(name);
                    assertThat(personalData.getMobileNo()).isEqualTo(mobileNo);
                    assertThat(personalData.getPhoneNo()).isEqualTo(phoneNo);
                    assertThat(personalData.getBirthDate()).isEqualTo(birthDate);
                },
                () -> fail("저장된 개인정보가 없습니다."));
        bankAccountRepository.fetchByPrivacyInfoId(savedId).ifPresentOrElse(bankAccount -> {
                    assertThat(bankAccount).isNotNull();
                    assertThat(bankAccount.getBankCode()).isEqualTo(bankCode);
                    assertThat(bankAccount.getAccountNo()).isEqualTo(accountNo);
                    assertThat(bankAccount.getHolder()).isEqualTo(name);
                },
                () -> fail("저장된 개인정보가 없습니다."));
    }

    private List<Long> savePrivacyInfo(int count, String name, String mobileNo, String phoneNo, LocalDate birthDate,
            BankCode bankCode, String accountNo) {

        List<Long> ids = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            PrivacyInfo privacyInfo = new PrivacyInfo("ownerId");
            BankAccount bankAccount = new BankAccount(bankCode, accountNo, name, privacyInfo);
            PersonalData personalData = PersonalData.builder()
                    .name(name)
                    .mobileNo(mobileNo)
                    .phoneNo(phoneNo)
                    .birthDate(birthDate)
                    .privacyInfo(privacyInfo)
                    .build();
            PrivacyInfo savedInfo = privacyInfoRepository.save(privacyInfo);
            bankAccountRepository.save(bankAccount);
            personalDataRepository.save(personalData);
            ids.add(savedInfo.getId());
        }
        return ids;
    }

}