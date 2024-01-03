package io.glory.mcore.util.datetime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateFormatterTest {

    @DisplayName("날짜를 yyyy-MM-dd 형식으로 변환 한다")
    @Test
    void when_FormatDate_expect_FormattedDate() throws Exception {
        // given
        LocalDate date = LocalDate.of(2023, 9, 1);

        // when
        String formattedDate = DateFormatter.formatDate(date);

        // then
        assertThat(formattedDate).isEqualTo("2023-09-01");
    }

    @DisplayName("날짜를 절달한 포멧 형식으로 변환 한다")
    @Test
    void when_FormatDateWithPattern_expect_FormattedDate() throws Exception {
        // given
        LocalDate date = LocalDate.of(2023, 9, 1);
        String pattern = "yyyy/MM/dd";

        // when
        String formattedDate = DateFormatter.formatDate(date, pattern);

        // then
        assertThat(formattedDate).isEqualTo("2023/09/01");
    }

    @DisplayName("null 날짜(LocalDate)를 변환 하면 빈 문자열을 반환 한다")
    @Test
    void when_FormatNullDate_expect_BlankString() throws Exception {
        // given

        // when
        String formattedDate = DateFormatter.formatDate(null);

        // then
        assertThat(formattedDate).isEmpty();
    }

    @DisplayName("일시를 yyyy-MM-dd HH:mm:ss 형식으로 변환 한다")
    @Test
    void when_FormatDateTime_expect_FormattedDateTime() throws Exception {
        // given
        LocalDateTime dateTime = LocalDate.of(2023, 9, 1).atStartOfDay();

        // when
        String formattedDate = DateFormatter.formatDateTime(dateTime);

        // then
        assertThat(formattedDate).isEqualTo("2023-09-01 00:00:00");
    }

    @DisplayName("일시를 전달한 형식으로 변환 한다")
    @Test
    void when_FormatDateTimeWithPattern_expect_FormattedDateTime() throws Exception {
        // given
        LocalDateTime dateTime = LocalDate.of(2023, 9, 1).atStartOfDay();
        String pattern = "yyyy/MM/dd HH:mm:ss";

        // when
        String formattedDate = DateFormatter.formatDateTime(dateTime, pattern);

        // then
        assertThat(formattedDate).isEqualTo("2023/09/01 00:00:00");
    }

    @DisplayName("null 일시(LocalDateTime)를 변환 하면 null 을 반환 한다")
    @Test
    void when_FormatNullDateTime_expect_BlankString() throws Exception {
        // given

        // when
        String formattedDate = DateFormatter.formatDateTime(null);

        // then
        assertThat(formattedDate).isNull();
    }

    @DisplayName("시간을 전달한 형식으로 반환 한다")
    @Test
    void when_getTimeStr_expect_Time() throws Exception {
        // given
        String format = "HHmmss";

        // when
        String timeStr = DateFormatter.getTimeStr();
        String timeFormatStr = DateFormatter.getTimeStr(format);

        // then
        System.out.println("==> timeStr = " + timeStr);
        System.out.println("==> timeFormatStr = " + timeFormatStr);
        assertThat(timeStr).hasSize(8);
        assertThat(timeFormatStr).hasSize(6);
    }

    @DisplayName("날짜를 전달한 형식으로 반환 한다")
    @Test
    void when_getDateStr_expect_Date() throws Exception {
        // given
        String format = "yyyyMMdd";

        // when
        String dateStr = DateFormatter.getDateStr();
        String dateFormatStr = DateFormatter.getDateStr(format);

        // then
        System.out.println("==> dateStr = " + dateStr);
        System.out.println("==> dateFormatStr = " + dateFormatStr);
        assertThat(dateStr).hasSize(10);
        assertThat(dateFormatStr).hasSize(8);
    }

    @DisplayName("일시를 전달한 형식으로 반환 한다")
    @Test
    void when_getDateTimeStr_expect_Datetime() throws Exception {
        // given
        String format = "yyyyMMddHHmmss";

        // when
        String dateTimeStr = DateFormatter.getDateTimeStr();
        String dateTimeFormatStr = DateFormatter.getDateTimeStr(format);

        // then
        System.out.println("==> dateTimeStr = " + dateTimeStr);
        System.out.println("==> dateTimeFormatStr = " + dateTimeFormatStr);
        assertThat(dateTimeStr).hasSize(19);
        assertThat(dateTimeFormatStr).hasSize(14);
    }

}