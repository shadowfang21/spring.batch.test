package com.sf.batch.test.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.sf.batch.test.dao.FormPostDao;
import com.sf.batch.test.dao.FormReplyDao;
import com.sf.batch.test.entity.FormPostEntity;
import com.sf.batch.test.entity.FormReplyEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Configuration
public class FlowDecisionJobConfig {
    
    @Autowired
    protected JobBuilderFactory jobBuilderFactory;

    @Autowired
    protected StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private FormReplyDao dao;
    
    @Autowired
    private FormPostDao postDao;
    
    @Component
    public class MyDecider implements JobExecutionDecider {
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            return new FlowExecutionStatus("A");
        }
    }
    
    @Bean
    public JobExecutionDecider decider() {
        return new MyDecider();
    }
    
    @Bean
    public Job testFlowDecisionJob() {
        return this.jobBuilderFactory.get("testFlowDecisionJob")
                    .start(testFlowDecisionStep())
                    .next(decider()).on("A").to(testFlowDecisionStepA())
                    .from(decider()).on("COMPLETED").end().end()
                    .build();
    }
    
    @Bean
    public Step testFlowDecisionStep() {
        return stepBuilderFactory.get("testFlowDecisionStep")
                .tasklet((s,c) -> {
                    
                    FormPostEntity p = new FormPostEntity();
                    p.setPostId(1);
                    p.setMessage("123");
                    postDao.save(p);
                    
                    FormReplyEntity r = new FormReplyEntity();
                    r.setFormPost(p);
                    r.setReplyId(1);
                    dao.save(r);
                    
                    dao.findAll().forEach(b -> log.info("{}", b.toString()));
                    
                    log.info("execute step");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    
    @Bean
    public Step testFlowDecisionStepA() {
        return stepBuilderFactory.get("testFlowDecisionStepA")
                .tasklet((s,c) -> {
                    log.info("{}", dao.findById(1));
                    log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    
    @Bean
    public Step testFlowDecisionStepB() {
        return stepBuilderFactory.get("testFlowDecisionStepB")
                .tasklet((s,c) -> {
                    log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
