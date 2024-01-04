package io.glory.pips.domain.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuerydslExpressionsTest {

    private StringPath          stringPath;
    private BooleanPath         booleanPath;
    private EnumPath<TestEnum>  enumPath;
    private NumberPath<Integer> integerPath;
    private NumberPath<Long>    longPath;

    @BeforeEach
    void setUp() {
        stringPath = mock(StringPath.class);
        booleanPath = mock(BooleanPath.class);
        enumPath = mock(EnumPath.class);
        integerPath = mock(NumberPath.class);
        longPath = mock(NumberPath.class);
    }

    @DisplayName("문자열 equals 검색 조건 생성")
    @Test
    void when_eqString_expect_BooleanExpression() throws Exception {
        // given
        String right = "right";
        when(stringPath.eq(anyString())).thenReturn(mock(BooleanExpression.class));

        // when
        BooleanExpression result = QuerydslExpressions.eq(stringPath, right);

        // then
        assertThat(result).isNotNull();
        verify(stringPath).eq(right);
    }

    @DisplayName("문자열 equals 검색 조건 생성 - right 값이 null")
    @Test
    void when_eqStringRightIsBlank_expect_Null() throws Exception {
        // given
        String right = "";

        // when
        BooleanExpression result = QuerydslExpressions.eq(stringPath, right);
        BooleanExpression result2 = QuerydslExpressions.eq(stringPath, null);

        // then
        assertThat(result).isNull();
        assertThat(result2).isNull();
    }

    @DisplayName("boolean equals 검색 조건 생성")
    @Test
    void when_eqBoolean_expect_BooleanExpression() throws Exception {
        // given
        boolean right = true;
        when(booleanPath.eq(anyBoolean())).thenReturn(mock(BooleanExpression.class));

        // when
        BooleanExpression result = QuerydslExpressions.eq(booleanPath, right);

        // then
        assertThat(result).isNotNull();
        verify(booleanPath).eq(right);
    }

    @DisplayName("boolean equals 검색 조건 생성 - right 값이 null")
    @Test
    void when_eqBooleanRightIsNull_expect_Null() throws Exception {
        // given

        // when
        BooleanExpression result = QuerydslExpressions.eq(booleanPath, null);

        // then
        assertThat(result).isNull();
    }

    @DisplayName("enum equals 검색 조건 생성")
    @Test
    void when_eqEnum_expect_BooleanExpression() throws Exception {
        // given
        TestEnum right = TestEnum.TEST;
        when(enumPath.eq(any(TestEnum.class))).thenReturn(mock(BooleanExpression.class));

        // when
        BooleanExpression result = QuerydslExpressions.eq(enumPath, right);

        // then
        assertThat(result).isNotNull();
        verify(enumPath).eq(right);
    }

    @DisplayName("enum equals 검색 조건 생성 - right 값이 null")
    @Test
    void when_eqEnumRightIsNull_expect_Null() throws Exception {
        // given

        // when
        BooleanExpression result = QuerydslExpressions.eq(enumPath, null);

        // then
        assertThat(result).isNull();
    }

    @DisplayName("int equals 검색 조건 생성")
    @Test
    void when_eqInt_expect_BooleanExpression() throws Exception {
        // given
        int right = 1;
        when(integerPath.eq(anyInt())).thenReturn(mock(BooleanExpression.class));

        // when
        BooleanExpression result = QuerydslExpressions.eq(integerPath, right);

        // then
        assertThat(result).isNotNull();
        verify(integerPath).eq(right);
    }

    @DisplayName("long equals 검색 조건 생성")
    @Test
    void when_eqLong_expect_BooleanExpression() throws Exception {
        // given
        long right = 1L;
        when(longPath.eq(anyLong())).thenReturn(mock(BooleanExpression.class));

        // when
        BooleanExpression result = QuerydslExpressions.eq(longPath, right);

        // then
        assertThat(result).isNotNull();
        verify(longPath).eq(right);
    }

    @DisplayName("number greater than 검색 조건 생성")
    @Test
    void when_gtNumber_expect_BooleanExpression() throws Exception {
        // given
        long right = 1L;
        when(longPath.gt(anyLong())).thenReturn(mock(BooleanExpression.class));

        // when
        BooleanExpression result = QuerydslExpressions.gt(longPath, right);

        // then
        assertThat(result).isNotNull();
        verify(longPath).gt(right);
    }

    @DisplayName("number greater than equals 검색 조건 생성")
    @Test
    void when_gteNumber_expect_BooleanExpression() throws Exception {
        // given
        long right = 1L;
        when(longPath.goe(anyLong())).thenReturn(mock(BooleanExpression.class));

        // when
        BooleanExpression result = QuerydslExpressions.gte(longPath, right);

        // then
        assertThat(result).isNotNull();
        verify(longPath).goe(right);
    }

    @DisplayName("number less than 검색 조건 생성")
    @Test
    void when_ltNumber_expect_BooleanExpression() throws Exception {
        // given
        long right = 1L;
        when(longPath.lt(anyLong())).thenReturn(mock(BooleanExpression.class));

        // when
        BooleanExpression result = QuerydslExpressions.lt(longPath, right);

        // then
        assertThat(result).isNotNull();
        verify(longPath).lt(right);
    }

    @DisplayName("number less than equals 검색 조건 생성")
    @Test
    void when_lteNumber_expect_BooleanExpression() throws Exception {
        // given
        long right = 1L;
        when(longPath.loe(anyLong())).thenReturn(mock(BooleanExpression.class));

        // when
        BooleanExpression result = QuerydslExpressions.lte(longPath, right);

        // then
        assertThat(result).isNotNull();
        verify(longPath).loe(right);
    }

    @DisplayName("문자열 starts with 검색 조건 생성")
    @Test
    void when_startsWithString_expect_BooleanExpression() throws Exception {
        // given
        String right = "right";
        when(stringPath.startsWith(anyString())).thenReturn(mock(BooleanExpression.class));

        // when
        BooleanExpression result = QuerydslExpressions.startsWith(stringPath, right);

        // then
        assertThat(result).isNotNull();
        verify(stringPath).startsWith(right);
    }

    @DisplayName("문자열 contains 검색 조건 생성")
    @Test
    void when_containsString_expect_BooleanExpression() throws Exception {
        // given
        String right = "right";
        when(stringPath.contains(anyString())).thenReturn(mock(BooleanExpression.class));

        // when
        BooleanExpression result = QuerydslExpressions.contains(stringPath, right);

        // then
        assertThat(result).isNotNull();
        verify(stringPath).contains(right);
    }

    @DisplayName("문자열 in 검색 조건 생성")
    @Test
    void when_inString_expect_BooleanExpression() throws Exception {
        // given
        String right = "right";
        List<String> rightList = List.of(right);
        when(stringPath.in(rightList)).thenReturn(mock(BooleanExpression.class));

        // when
        BooleanExpression result = QuerydslExpressions.in(stringPath, rightList);

        // then
        assertThat(result).isNotNull();
        verify(stringPath).in(rightList);
    }

    @DisplayName("enum in 검색 조건 생성")
    @Test
    void when_inEnum_expect_BooleanExpression() throws Exception {
        // given
        TestEnum right = TestEnum.TEST;
        List<TestEnum> rightList = List.of(right);
        when(enumPath.in(rightList)).thenReturn(mock(BooleanExpression.class));

        // when
        BooleanExpression result = QuerydslExpressions.in(enumPath, rightList);

        // then
        assertThat(result).isNotNull();
        verify(enumPath).in(rightList);
    }

    @DisplayName("is true 검색 조건 생성")
    @Test
    void when_isTrue_expect_BooleanExpression() throws Exception {
        // given
        when(booleanPath.isTrue()).thenReturn(mock(BooleanExpression.class));

        // when
        BooleanExpression result = QuerydslExpressions.isTrue(booleanPath);

        // then
        assertThat(result).isNotNull();
        verify(booleanPath).isTrue();
    }

    @DisplayName("is false 검색 조건 생성")
    @Test
    void when_isFalse_expect_BooleanExpression() throws Exception {
        // given
        when(booleanPath.isFalse()).thenReturn(mock(BooleanExpression.class));

        // when
        BooleanExpression result = QuerydslExpressions.isFalse(booleanPath);

        // then
        assertThat(result).isNotNull();
        verify(booleanPath).isFalse();
    }

    private enum TestEnum {
        TEST;
    }

}