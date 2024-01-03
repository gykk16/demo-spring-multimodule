package io.glory.testsupport.restdocs;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.operation.preprocess.UriModifyingOperationPreprocessor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * RestDocs 지원 클래스
 */
@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocsSupport {

    protected static ObjectMapper                   objectMapper = new ObjectMapper();
    protected        RestDocumentationResultHandler restDocs     = write();
    protected        MockMvc                        mockMvc;

    protected abstract Object initController();

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(initController())
                // .setControllerAdvice(new LegacyErrorHandler(), new LssendJsonErrorHandler())
                // .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .addFilters(new CharacterEncodingFilter(UTF_8.name(), true))
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider)
                        .uris().withScheme("https").withHost("tncorp.smilecon.co.kr").withPort(443))
                .alwaysDo(print())
                .alwaysDo(restDocs)
                .build();
    }

    private RestDocumentationResultHandler write() {
        return MockMvcRestDocumentation.document(
                "{class-name}/{method-name}",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
        );
    }

    private static UriModifyingOperationPreprocessor getHost() {
        return Preprocessors.modifyUris()
                .scheme("https")
                .host("tncorp.smilecon.co.kr");
    }

}
