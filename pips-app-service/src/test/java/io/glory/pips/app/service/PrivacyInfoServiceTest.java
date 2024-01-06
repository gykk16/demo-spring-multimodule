package io.glory.pips.app.service;

import static org.assertj.core.api.Assertions.*;

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

    private static final String    NAME       = "홍길동";
    private static final String    MOBILE_NO  = "01012345678";
    private static final String    PHONE_NO   = "021234567";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1990, 1, 1);
    private static final BankCode  BANK_CODE  = BankCode.TEST_BANK;
    private static final String    ACCOUNT_NO = "1234567890";

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
        Long id = savePrivacyInfo(1, NAME, MOBILE_NO, PHONE_NO, BIRTH_DATE, BANK_CODE, ACCOUNT_NO).get(0);

        // when
        PrivacyInfoAllDto privacyInfoAllDto = privacyInfoService.fetchPrivacyInfo(id);

        // then
        System.out.println("==> privacyInfoAllDto = " + privacyInfoAllDto);
        assertThat(privacyInfoAllDto).isNotNull()
                .extracting("id", "name", "mobileNo", "phoneNo",
                        "birthDate", "bankCode", "accountNo")
                .contains(id, NAME, MOBILE_NO, PHONE_NO,
                        BIRTH_DATE, BankCode.TEST_BANK, ACCOUNT_NO);
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
        List<Long> ids = savePrivacyInfo(3, NAME, MOBILE_NO, PHONE_NO, BIRTH_DATE, BANK_CODE, ACCOUNT_NO);

        // when
        List<PrivacyInfoAllDto> privacyInfoAllDtos = privacyInfoService.fetchPrivacyInfos(ids);

        // then
        assertThat(privacyInfoAllDtos).isNotNull()
                .hasSize(3)
                .extracting("id", "name", "mobileNo", "phoneNo",
                        "birthDate", "bankCode", "accountNo")
                .containsExactlyInAnyOrder(
                        tuple(ids.get(0), NAME, MOBILE_NO, PHONE_NO,
                                BIRTH_DATE, BankCode.TEST_BANK, ACCOUNT_NO),
                        tuple(ids.get(1), NAME, MOBILE_NO, PHONE_NO,
                                BIRTH_DATE, BankCode.TEST_BANK, ACCOUNT_NO),
                        tuple(ids.get(2), NAME, MOBILE_NO, PHONE_NO,
                                BIRTH_DATE, BankCode.TEST_BANK, ACCOUNT_NO)
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
        PrivacyInfoServiceRequest serviceRequest = new PrivacyInfoServiceRequest(
                NAME, MOBILE_NO, PHONE_NO, BIRTH_DATE, BANK_CODE, ACCOUNT_NO, NAME);

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
                    assertThat(personalData.getName()).isEqualTo(NAME);
                    assertThat(personalData.getMobileNo()).isEqualTo(MOBILE_NO);
                    assertThat(personalData.getPhoneNo()).isEqualTo(PHONE_NO);
                    assertThat(personalData.getBirthDate()).isEqualTo(BIRTH_DATE);
                },
                () -> fail("저장된 개인정보가 없습니다."));
        bankAccountRepository.fetchByPrivacyInfoId(savedId).ifPresentOrElse(bankAccount -> {
                    assertThat(bankAccount).isNotNull();
                    assertThat(bankAccount.getBankCode()).isEqualTo(BANK_CODE);
                    assertThat(bankAccount.getAccountNo()).isEqualTo(ACCOUNT_NO);
                    assertThat(bankAccount.getHolder()).isEqualTo(NAME);
                },
                () -> fail("저장된 개인정보가 없습니다."));
    }

    @DisplayName("개인정보 를 수정한다")
    @Test
    void when_updatePrivacyInfo_expect_DbUpdate() throws Exception {
        // given
        Long pInfoId = savePrivacyInfo(1, NAME, MOBILE_NO, PHONE_NO, BIRTH_DATE, BANK_CODE, ACCOUNT_NO).get(0);

        String newName = "김말자";
        String newMobileNo = "01000000000";
        String newPhoneNo = "0200000000";
        LocalDate newBirthDate = LocalDate.of(2000, 1, 1);
        BankCode newBankCode = BankCode.TEST_BANK;
        String newAccountNo = "0987654321";
        String newHolder = "김철수";

        PrivacyInfoServiceRequest serviceRequest = new PrivacyInfoServiceRequest(
                newName, newMobileNo, newPhoneNo, newBirthDate, newBankCode, newAccountNo, newHolder);

        // when
        Long updatedPInfoId = privacyInfoService.updatePrivacyInfo(pInfoId, serviceRequest);

        // then
        assertThat(updatedPInfoId).isEqualTo(pInfoId);
        personalDataRepository.fetchByPrivacyInfoId(updatedPInfoId).ifPresentOrElse(
                personalData -> assertThat(personalData).isNotNull()
                        .extracting("name", "mobileNo", "phoneNo", "birthDate")
                        .containsExactly(newName, newMobileNo, newPhoneNo, newBirthDate),
                () -> fail("저장된 개인정보가 없습니다."));
        bankAccountRepository.fetchByPrivacyInfoId(updatedPInfoId).ifPresentOrElse(
                bankAccount -> assertThat(bankAccount).isNotNull()
                        .extracting("bankCode", "accountNo", "holder")
                        .containsExactly(newBankCode, newAccountNo, newHolder),
                () -> fail("저장된 개인정보가 없습니다."));
    }

    @DisplayName("개인정보 를 수정할 때 개인정보가 없으면 DATA_NOT_FOUND 예외를 던진다")
    @Test
    void when_NoDataToUpdate_throw_DATA_NOT_FOUND() throws Exception {
        // given
        long nonExistId = 1L;
        PrivacyInfoServiceRequest serviceRequest = new PrivacyInfoServiceRequest(
                "김말자", "01000000000", "0200000000", LocalDate.of(2000, 1, 1),
                BankCode.TEST_BANK, "0987654321", "김말자");

        // when
        assertThatThrownBy(() -> privacyInfoService.updatePrivacyInfo(nonExistId, serviceRequest))
                // then
                .isInstanceOf(PrivacyInfoException.class)
                .hasMessageContaining(PrivacyInfoErrorCode.DATA_NOT_FOUND.getCode());
    }

    @DisplayName("개인정보 를 삭제 한다 (soft delete)")
    @Test
    void when_deletePrivacyInfo_expect_DbSoftDelete() throws Exception {
        // given
        Long pInfoId = savePrivacyInfo(1, NAME, MOBILE_NO, PHONE_NO, BIRTH_DATE, BANK_CODE, ACCOUNT_NO).get(0);

        // when
        Long deletedPInfoId = privacyInfoService.deletePrivacyInfo(pInfoId);

        // then
        assertThat(deletedPInfoId).isEqualTo(pInfoId);
        assertThatCode(() -> privacyInfoRepository.findById(deletedPInfoId))
                .isNull();
    }

    @DisplayName("개인정보 를 삭제할 때 개인정보가 없으면 DATA_NOT_FOUND 예외를 던진다")
    @Test
    void when_NoDataToDelete_throw_DATA_NOT_FOUND() throws Exception {
        // given
        long nonExistId = 1L;

        // when
        assertThatThrownBy(() -> privacyInfoService.deletePrivacyInfo(nonExistId))
                // then
                .isInstanceOf(PrivacyInfoException.class)
                .hasMessageContaining(PrivacyInfoErrorCode.DATA_NOT_FOUND.getCode());
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