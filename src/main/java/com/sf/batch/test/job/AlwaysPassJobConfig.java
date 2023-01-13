package com.sf.batch.test.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AlwaysPassJobConfig {
    @Autowired
    protected JobBuilderFactory jobBuilderFactory;

    @Autowired
    protected StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job anotherJob() {
        return this.jobBuilderFactory.get("AlwaysPassJobConfig")
                    .start(alwaysPassJobConfigStep1())
                    .next(alwaysPassJobConfigStep3()).on(ExitStatus.FAILED.getExitCode())
                        .to(alwaysPassJobConfigStep2())
                    .from(alwaysPassJobConfigStep3()).on("*").to(alwaysPassJobConfigStep2())
                    .next(alwaysPassJobConfigStep4())
                    .end()
                    .build();
    }
    
    @Bean
    public Step alwaysPassJobConfigStep1() {
        return stepBuilderFactory.get("AlwaysPassJobConfigStep1")
//                .listener(passListener())
                .tasklet((s,c) -> {
                    log.info("=========================step1=====================");
                    
                    "haha".substring(0, 100);
                    return RepeatStatus.FINISHED;
                })
                .exceptionHandler((context, e) -> {
                    log.info(e.getMessage());
                    context.setCompleteOnly();
//                    context.
                })
                .build();
    }
    
    @Bean
    public Step alwaysPassJobConfigStep3() {
        return stepBuilderFactory.get("AlwaysPassJobConfigStep3")
//                .listener(passListener())
                .tasklet((s,c) -> {
                    log.info("=========================step3=====================");
                    
                    "haha".substring(0, 100);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    
    @Bean
    public Step alwaysPassJobConfigStep2() {
        return stepBuilderFactory.get("AlwaysPassJobConfigStep2")
                .tasklet((s,c) -> {
                    log.info("=========================step2=====================");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    
    @Bean
    public Step alwaysPassJobConfigStep4() {
        return stepBuilderFactory.get("AlwaysPassJobConfigStep4")
                .tasklet((s,c) -> {
                    log.info("=========================step4=====================");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
