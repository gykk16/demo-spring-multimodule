package io.glory.pips.app.batch.job;

import lombok.Getter;

@Getter
public class PersonImportJobParameter {

    private final String filePath;

    public PersonImportJobParameter(String filePath) {
        this.filePath = filePath;
    }

}
