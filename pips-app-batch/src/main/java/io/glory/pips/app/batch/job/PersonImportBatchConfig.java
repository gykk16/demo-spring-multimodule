package io.glory.pips.app.batch.job;

import lombok.RequiredArgsConstructor;

import io.glory.pips.app.batch.domain.Person;
import io.glory.pips.domain.entity.personal.PersonalData;
import io.glory.pips.domain.repository.personal.PersonalDataRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class PersonImportBatchConfig {

    private static final String JOB_NAME = "PERSON_IMPORT";

    private final JobRepository              jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    private final PersonalDataRepository personalDataRepository;

    private final PersonImportJobParameter jobParameter;

    @Bean(JOB_NAME + "_jobParameter")
    @JobScope
    public PersonImportJobParameter jobParameter(@Value("#{jobParameters[filePath]}") String filePath) {
        return new PersonImportJobParameter(filePath);
    }

    @Bean(name = JOB_NAME + "_reader")
    @StepScope
    public FlatFileItemReader<Person> reader() {
        FlatFileItemReader<Person> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(jobParameter.getFilePath()));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1); /* skip header */
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    @Bean(name = JOB_NAME + "_writer")
    public RepositoryItemWriter<PersonalData> writer() {
        RepositoryItemWriter<PersonalData> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(personalDataRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    @Bean
    public PersonProcessor processor() {
        return new PersonProcessor();
    }

    @Bean(name = JOB_NAME + "_step")
    @JobScope
    public Step step() {
        return new StepBuilder(JOB_NAME + "_step", jobRepository)
                .<Person, PersonalData>chunk(100, platformTransactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean(name = JOB_NAME)
    public Job job() {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(step())
                // .next()
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(5);
        return taskExecutor;
    }

    private LineMapper<Person> lineMapper() {
        final String DELIMITER_COMMA = ",";

        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(DELIMITER_COMMA);
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "name", "birthdate", "mobileno", "phoneno");

        BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Person.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

}
