package io.glory.docs.common.codes;

import static io.glory.testsupport.restdocs.CustomResponseFieldsSnippet.customResponseFields;
import static io.glory.testsupport.restdocs.CustomResponseFieldsSnippet.responseEnumConvertFieldDescriptor;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import io.glory.coreweb.response.ApiResponseEntity;
import io.glory.testsupport.restdocs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

class EnumCodeDocsTest extends RestDocsSupport {

    @Override
    protected Object initController() {
        return new EnumCodeController();
    }

    @DisplayName("공통 응답 코드")
    @Test
    void responseCode() throws Exception {
        // given
        ResultActions result = mockMvc.perform(get("/docs/enums-response-code")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        MvcResult mvcResult = result.andReturn();
        ResponseEnumDocs responseEnums = getResponseEnumsData(mvcResult);

        final String SNIPPET_NAME = "common-response-code";
        result.andExpect(status().isOk())
                .andDo(restDocs.document(
                        /* 공통 코드 */
                        customResponseFields(SNIPPET_NAME,
                                beneathPath("data.successCode").withSubsectionId("success"),
                                attributes(key("code").value("description")),
                                responseEnumConvertFieldDescriptor(responseEnums.getSuccessCode())
                        ),
                        customResponseFields(SNIPPET_NAME,
                                beneathPath("data.errorCode").withSubsectionId("error"),
                                attributes(key("code").value("description")),
                                responseEnumConvertFieldDescriptor(responseEnums.getErrorCode())
                        ),
                        customResponseFields(SNIPPET_NAME,
                                beneathPath("data.externalErrorCode").withSubsectionId("externalErrorCode"),
                                attributes(key("code").value("description")),
                                responseEnumConvertFieldDescriptor(responseEnums.getExternalErrorCode())
                        )
                        /* 응답 코드 */
                        // todo
                ));
    }

    @DisplayName("쿠폰 상태 코드")
    @Test
    void enumCode() throws Exception {
        // given
        ResultActions result = mockMvc.perform(get("/docs/enums-code")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        MvcResult mvcResult = result.andReturn();
        EnumDocs enumDocs = getEnumsData(mvcResult);

        final String SNIPPET_NAME = "common-enum-code";
        result.andExpect(status().isOk())
        // todo
        ;
    }

    private EnumDocs getEnumsData(MvcResult result) throws IOException {
        ApiResponseEntity<EnumDocs> apiResponseEntity = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });

        return apiResponseEntity.getData();
    }

    private ResponseEnumDocs getResponseEnumsData(MvcResult result) throws IOException {
        ApiResponseEntity<ResponseEnumDocs> apiResponseEntity = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });

        return apiResponseEntity.getData();
    }

}
