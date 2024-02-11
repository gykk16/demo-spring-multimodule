package io.glory.pips.app.batch.domain;

import lombok.Getter;
import lombok.Setter;

import io.glory.mcore.util.datetime.DateFormatter;
import io.glory.pips.domain.entity.personal.PersonalData;

@Getter
@Setter
public class Person {

    private Long   id;
    private String name;
    private String birthDate;
    private String mobileNo;
    private String phoneNo;

    public PersonalData toPersonalDataEntity() {
        return PersonalData.builder()
                .name(name)
                .birthDate(DateFormatter.formatDateStr(birthDate))
                .mobileNo(mobileNo)
                .phoneNo(phoneNo)
                .build();
    }

}
