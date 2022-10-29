package com.sf.batch.test.job.parameter;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@JobScope
@Component
public class EloanJobParameter {

    @Value("#{jobParameters['content']}")
    private String content;

    public String getContent() {
        return content;
    }
    
}
