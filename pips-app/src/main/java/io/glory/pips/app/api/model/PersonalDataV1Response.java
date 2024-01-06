package io.glory.pips.app.api.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public record PersonalDataV1Response(
        @JsonProperty("name") String name,
        @JsonProperty("mobileNo") String mobileNo,
        @JsonProperty("phoneNo") String phoneNo,
        @JsonProperty("birthDate")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonSerialize(using = LocalDateSerializer.class)
        LocalDate birthDate
) {

}
