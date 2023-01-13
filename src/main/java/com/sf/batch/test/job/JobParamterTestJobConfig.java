package com.sf.batch.test.job;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.sf.batch.test.entity.FormReplyEntity;
import com.sf.batch.test.job.parameter.EloanJobParameter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Configuration
public class JobParamterTestJobConfig {

    @Autowired
    protected JobBuilderFactory jobBuilderFactory;

    @Autowired
    protected StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    
    @Autowired
    private EloanJobParameter parameter;
    
    @Bean
    public Job jobParameterTestJob() {
        return this.jobBuilderFactory.get("jobParameterTestJob")
                    .start(jobParameterTestStep())
                    .build();
    }

    
    @Bean
    public Step jobParameterTestStep() {
        return stepBuilderFactory.get("jobParameterTestStep")
                .<FormReplyEntity, FormReplyEntity>chunk(100)
                .reader(jobParameterTestReader())
                .writer(jobParameterTestWriter())
                .build();
    }
    
    @Bean
    public ItemWriter<FormReplyEntity> jobParameterTestWriter() {
        return (items) -> {
            log.info(parameter.getContent());
            items.forEach(b -> log.info("{}", b.toString()));
        };
    }
    
    @Bean
    public JpaPagingItemReader<FormReplyEntity> jobParameterTestReader() {
        return new JpaPagingItemReaderBuilder<FormReplyEntity>()
                .entityManagerFactory(entityManagerFactory)
                .name("reader")
                .queryString("select entity from FormReplyEntity entity")
                .pageSize(100)
                .build();
    }
}
