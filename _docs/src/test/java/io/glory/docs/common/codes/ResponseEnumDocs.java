package io.glory.docs.common.codes;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseEnumDocs {

    /* 공통 코드 */
    Map<String, List<String>> successCode;
    Map<String, List<String>> errorCode;
    Map<String, List<String>> externalErrorCode;
    /* 응답 코드 */

}
