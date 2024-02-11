package io.glory.pips.app.api;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

import io.glory.coreweb.aop.security.SecuredIp;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch/person")
@RequiredArgsConstructor
public class PersonController {

    private final JobLauncher jobLauncher;
    private final Job         job;

    @PostMapping
    @SecuredIp
    public void importCsvToDb() {
        final String FILE_PATH = "pips-app-batch/src/main/resources/people.csv";

        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("startAt", LocalDateTime.now())
                .addString("filePath", FILE_PATH)
                .toJobParameters();

        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
