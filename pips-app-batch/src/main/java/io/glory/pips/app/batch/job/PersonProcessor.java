package io.glory.pips.app.batch.job;

import io.glory.pips.app.batch.domain.Person;
import io.glory.pips.domain.entity.personal.PersonalData;
import org.springframework.batch.item.ItemProcessor;

public class PersonProcessor implements ItemProcessor<Person, PersonalData> {

    @Override
    public PersonalData process(Person item) throws Exception {
        return item.toPersonalDataEntity();
    }

}
