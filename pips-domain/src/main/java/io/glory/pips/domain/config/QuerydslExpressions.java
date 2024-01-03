package io.glory.pips.domain.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.core.util.StringUtils;

/**
 * Querydsl 검색 조건 생성 유틸리티
 */
public class QuerydslExpressions {

    private QuerydslExpressions() {
    }

    /**
     * equals 조건
     *
     * @param path  대상 필드 (column)
     * @param right 비교 값
     * @return 검색 조건
     */
    public static BooleanExpression eq(StringPath path, String right) {
        return StringUtils.isNullOrEmpty(right) ? path.eq(right) : null;
    }

    /**
     * equals 조건 (boolean)
     *
     * @param path  대상 필드 (column)
     * @param right 비교 값
     * @return 검색 조건
     */
    public static BooleanExpression eq(BooleanPath path, Boolean right) {
        return right != null ? path.eq(right) : null;
    }

    /**
     * equals 조건 (enum)
     *
     * @param path  대상 필드 (column)
     * @param right 비교 값
     * @return 검색 조건
     */
    public static <S extends Enum<S>> BooleanExpression eq(EnumPath<S> path, S right) {
        return right != null ? path.eq(right) : null;
    }

    /**
     * equals 조건 (number)
     *
     * @param path  대상 필드 (column)
     * @param right 비교 값
     * @return 검색 조건
     */
    public static <S extends Number & Comparable<?>> BooleanExpression eq(NumberPath<S> path, S right) {
        return right != null ? path.eq(right) : null;
    }

    /**
     * greater than 조건 (number)
     *
     * @param path  대상 필드 (column)
     * @param right 비교 값
     * @param <S>   number type
     * @return 검색 조건
     */
    public static <S extends Number & Comparable<?>> BooleanExpression gt(NumberPath<S> path, S right) {
        return right != null ? path.gt(right) : null;
    }

    /**
     * greater than or equals 조건 (number)
     *
     * @param path  대상 필드 (column)
     * @param right 비교 값
     * @param <S>   number type
     * @return 검색 조건
     */
    public static <S extends Number & Comparable<?>> BooleanExpression gte(NumberPath<S> path, S right) {
        return right != null ? path.goe(right) : null;
    }

    /**
     * less than 조건 (number)
     *
     * @param path  대상 필드 (column)
     * @param right 비교 값
     * @param <S>   number type
     * @return 검색 조건
     */
    public static <S extends Number & Comparable<?>> BooleanExpression lt(NumberPath<S> path, S right) {
        return right != null ? path.lt(right) : null;
    }

    /**
     * less than or equals 조건 (number)
     *
     * @param path  대상 필드 (column)
     * @param right 비교 값
     * @param <S>   number type
     * @return 검색 조건
     */
    public static <S extends Number & Comparable<?>> BooleanExpression lte(NumberPath<S> path, S right) {
        return right != null ? path.loe(right) : null;
    }

    /**
     * starts with 조건
     *
     * @param path  대상 필드 (column)
     * @param right 비교 값
     * @return 검색 조건
     */
    public static BooleanExpression startsWith(StringPath path, String right) {
        return StringUtils.isNullOrEmpty(right) ? path.startsWith(right) : null;
    }

    /**
     * contains 조건
     *
     * @param path  대상 필드 (column)
     * @param right 비교 값
     * @return 검색 조건
     */
    public static BooleanExpression contains(StringPath path, String right) {
        return StringUtils.isNullOrEmpty(right) ? path.contains(right) : null;
    }

    /**
     * in 조건
     *
     * @param path  대상 필드 (column)
     * @param right 비교 값
     * @return 검색 조건
     */
    public static BooleanExpression in(StringPath path, Collection<String> right) {
        return right != null ? path.in(right) : null;
    }

    /**
     * in 조건 (enum)
     *
     * @param path  대상 필드 (column)
     * @param right 비교 값
     * @return 검색 조건
     */
    public static <S extends Enum<S>> BooleanExpression in(EnumPath<S> path, Collection<S> right) {
        return right != null ? path.in(right) : null;
    }

    /**
     * is true 조건
     *
     * @param path 대상 필드 (column)
     * @return 검색 조건
     */
    public static BooleanExpression isTrue(BooleanPath path) {
        return path.isTrue();
    }

    /**
     * is false 조건
     *
     * @param path 대상 필드 (column)
     * @return 검색 조건
     */
    public static BooleanExpression isFalse(BooleanPath path) {
        return path.isFalse();
    }

    /**
     * 날짜 검색 조건
     *
     * @param path      대상 필드 (column)
     * @param startDate 비교 시작일
     * @param endDate   비교  종료일
     * @return 검색 조건
     */
    public static BooleanExpression dateBetween(DateTimePath<LocalDate> path, LocalDate startDate, LocalDate endDate) {

        // 시작, 종료 검색 일자가 비어 있으면
        if (startDate == null && endDate == null) {
            return null; // fast exit
        }

        BooleanExpression booleanExpression = null;

        // 시작, 종료 검색 일자가 모두 있으면
        if (startDate != null && endDate != null) {
            booleanExpression = path.between(startDate, endDate);
        }
        // 시작일 있으면 세팅
        else if (startDate != null) {
            booleanExpression = path.after(startDate);
        }
        // 종료일 있으면 세팅
        else {
            booleanExpression = path.before(endDate);
        }

        return booleanExpression;
    }

    /**
     * 일시 검색 조건
     *
     * @param path      대상 필드 (column)
     * @param startDate 비교 시작일
     * @param endDate   비교 종료일
     * @return 검색 조건
     */
    public static BooleanExpression dateTimeBetween(DateTimePath<LocalDateTime> path,
            LocalDate startDate, LocalDate endDate) {

        // 시작, 종료 검색 일자가 비어 있으면
        if (startDate == null && endDate == null) {
            return null; // fast exit
        }

        BooleanExpression booleanExpression = null;

        // 시작, 종료 검색 일자가 모두 있으면
        if (startDate != null && endDate != null) {
            booleanExpression = path.between(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        }
        // 시작일 있으면 세팅
        else if (startDate != null) {
            booleanExpression = path.after(startDate.atStartOfDay());
        }
        // 종료일 있으면 세팅
        else {
            booleanExpression = path.before(endDate.plusDays(1).atStartOfDay());
        }

        return booleanExpression;
    }

}
