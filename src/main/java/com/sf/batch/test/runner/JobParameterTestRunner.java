package com.sf.batch.test.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JobParameterTestRunner implements CommandLineRunner {

    @Autowired
    private JobLauncher launcher;
    
    
    @Autowired
    @Qualifier("jobParameterTestJob")
    private Job job;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("----------------------------------------");
        
        launcher.run(job, new JobParametersBuilder()
                .addString("content", "fuck")
                .toJobParameters());
        
        launcher.run(job, new JobParametersBuilder()
                .addString("content", "fuck2")
                .toJobParameters());
        
        log.info("----------------------------------------");
    }

}
