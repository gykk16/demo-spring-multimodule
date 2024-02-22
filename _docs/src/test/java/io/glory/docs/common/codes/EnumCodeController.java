package io.glory.docs.common.codes;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.glory.coreweb.response.ApiResponseEntity;
import io.glory.mcore.code.ErrorCode;
import io.glory.mcore.code.ExternalErrorCode;
import io.glory.mcore.code.ResponseCode;
import io.glory.mcore.code.SuccessCode;
import io.glory.pips.data.constants.BaseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnumCodeController {

    @GetMapping("/docs/enums-response-code")
    public ResponseEntity<ApiResponseEntity<ResponseEnumDocs>> responseCodes() {

        /* 공통 코드 */
        Map<String, List<String>> successCode = getResponseEnumDocs(SuccessCode.values());
        Map<String, List<String>> errorCode = getResponseEnumDocs(ErrorCode.values());
        Map<String, List<String>> externalErrorCode = getResponseEnumDocs(ExternalErrorCode.values());

        /* 응답 코드 */

        return ApiResponseEntity.of(SuccessCode.SUCCESS,
                ResponseEnumDocs.builder()
                        /* 공통 코드 */
                        .successCode(successCode)
                        .errorCode(errorCode)
                        .externalErrorCode(externalErrorCode)
                        /* 응답 코드 */
                        .build()
        );
    }

    @GetMapping("/docs/enums-code")
    public ResponseEntity<ApiResponseEntity<EnumDocs>> statusCodes() {

        // todo:

        return ApiResponseEntity.of(SuccessCode.SUCCESS,
                EnumDocs.builder()
                        // todo
                        .build()
        );
    }

    private Map<String, List<String>> getResponseEnumDocs(ResponseCode[] enumCode) {
        return Arrays.stream(enumCode)
                .collect(Collectors.toMap(ResponseCode::getCode,
                        code -> List.of(String.valueOf(code.getStatusCode()), code.getMessage()),
                        (a, b) -> b, LinkedHashMap::new)
                );
    }

    private Map<String, List<String>> getStatusEnumDocs(BaseCode[] enumCode) {
        return Arrays.stream(enumCode)
                .collect(Collectors.toMap(BaseCode::toString,
                        code -> List.of(code.getDescription()),
                        (a, b) -> b, LinkedHashMap::new)
                );
    }

}
