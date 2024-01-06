package io.glory.pips.app.api;

import java.util.List;

import jakarta.validation.constraints.Size;

import lombok.RequiredArgsConstructor;

import io.glory.coreweb.response.ApiResponseEntity;
import io.glory.coreweb.response.CollectionResObj;
import io.glory.mcore.code.SuccessCode;
import io.glory.pips.app.api.model.PrivacyInfoV1Request;
import io.glory.pips.app.api.model.PrivacyInfoV1Response;
import io.glory.pips.app.service.PrivacyInfoService;
import io.glory.pips.domain.query.PrivacyInfoAllDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pips/v1/p-info")
@RequiredArgsConstructor
public class PrivacyInfoV1Controller {

    private final PrivacyInfoService privacyInfoService;

    /**
     * 개인정보 조회
     *
     * @param id 개인정보 id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseEntity<PrivacyInfoV1Response>> fetchPrivacyInfo(@PathVariable Long id) {

        PrivacyInfoAllDto privacyInfoAllDto = privacyInfoService.fetchPrivacyInfo(id);
        return ApiResponseEntity.of(SuccessCode.SUCCESS, PrivacyInfoV1Response.of(privacyInfoAllDto));
    }

    /**
     * 개인정보 목록 조회
     *
     * @param ids 개인정보 id 목록
     */
    @PostMapping("/list")
    public ResponseEntity<ApiResponseEntity<CollectionResObj<?>>> fetchPrivacyInfos(
            @RequestBody @Size(min = 1, max = 10) List<Long> ids) {

        List<PrivacyInfoV1Response> responseList = privacyInfoService.fetchPrivacyInfos(ids)
                .stream()
                .map(PrivacyInfoV1Response::of)
                .toList();
        var collectionResObj = new CollectionResObj<>(responseList);
        return ApiResponseEntity.of(SuccessCode.SUCCESS, collectionResObj);
    }

    /**
     * 개인정보 저장
     *
     * @param request 개인정보 저장/수정 요청 Spec
     */
    @PostMapping
    public ResponseEntity<ApiResponseEntity<Long>> savePrivacyInfo(
            @RequestBody PrivacyInfoV1Request request) {

        Long privacyInfoId = privacyInfoService.savePrivacyInfo(request.toServiceRequest());
        return ApiResponseEntity.of(SuccessCode.CREATED, privacyInfoId);
    }

    /**
     * 개인정보 수정
     *
     * @param id      개인정보 id
     * @param request 개인정보 저장/수정 요청 Spec
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseEntity<Long>> updatePrivacyInfo(
            @PathVariable Long id, @RequestBody PrivacyInfoV1Request request) {

        Long privacyInfoId = privacyInfoService.updatePrivacyInfo(id, request.toServiceRequest());
        return ApiResponseEntity.of(SuccessCode.SUCCESS, privacyInfoId);
    }

    /**
     * 개인정보 삭제
     *
     * @param id 개인정보 id
     */
    @PostMapping("/{id}/delete")
    public ResponseEntity<ApiResponseEntity<Long>> deletePrivacyInfo(@PathVariable Long id) {

        Long privacyInfoId = privacyInfoService.deletePrivacyInfo(id);
        return ApiResponseEntity.of(SuccessCode.SUCCESS, privacyInfoId);
    }

}
