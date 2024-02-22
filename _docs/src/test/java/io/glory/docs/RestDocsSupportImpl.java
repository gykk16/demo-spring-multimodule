package io.glory.docs;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import io.glory.coreweb.filter.trace.TraceIdGeneratorTraceFilter;
import io.glory.mcore.util.idgenerator.TraceIdGenerator;
import io.glory.testsupport.restdocs.RestDocsSupport;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

public abstract class RestDocsSupportImpl extends RestDocsSupport {

    protected static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter(
            UTF_8.name(), true);

    protected static final TraceIdGeneratorTraceFilter TRACE_ID_GENERATOR_TRACE_FILTER = new TraceIdGeneratorTraceFilter(
            new TraceIdGenerator(1, 1));

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(initController())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .addFilters(CHARACTER_ENCODING_FILTER, TRACE_ID_GENERATOR_TRACE_FILTER)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider)
                        .uris().withScheme("https").withHost("www.example.com").withPort(443))
                .alwaysDo(print())
                .alwaysDo(restDocs)
                .build();
    }

}
