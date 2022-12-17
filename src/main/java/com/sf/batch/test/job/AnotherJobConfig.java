package com.sf.batch.test.job;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sf.batch.test.dao.FormPostDao;
import com.sf.batch.test.dao.FormReplyDao;
import com.sf.batch.test.entity.FormPostEntity;
import com.sf.batch.test.entity.FormReplyEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AnotherJobConfig {
    @Autowired
    protected JobBuilderFactory jobBuilderFactory;

    @Autowired
    protected StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    
    @Autowired
    private FormReplyDao dao;
    
    @Autowired
    private FormPostDao postDao;
    
    @Bean
    public Job anotherJob() {
        return this.jobBuilderFactory.get("anotherJobConfig")
                    .start(anotherStep())
                    .next(jpaPageStep())
                    .build();
    }
    
    @Bean
    public Step anotherStep() {
        return stepBuilderFactory.get("anotherStep")
                .tasklet((s,c) -> {
                    
                    FormPostEntity p = new FormPostEntity();
                    p.setPostId(1);
                    p.setMessage("123");
                    postDao.save(p);
                    
//                    Optional.of(new FormReplyEntity())
//                        .ifPresent(r -> {
//                            r.setFormPost(p);
//                            r.setReplyId(1);
//                            dao.save(r);
//                        });
//                    
//                    Optional.of(new FormReplyEntity())
//                        .ifPresent(r -> {
//                            r.setFormPost(p);
//                            r.setReplyId(2);
//                            dao.save(r);
//                        });
//                    
//                    Optional.of(new FormReplyEntity())
//                        .ifPresent(r -> {
//                            r.setFormPost(p);
//                            r.setReplyId(3);
//                            dao.save(r);
//                        });
                    
                    dao.findAll().forEach(b -> log.info("{}", b.toString()));
                    
                    log.info("execute step");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    
    
    @Bean
    public Step jpaPageStep() {
        return stepBuilderFactory.get("jpaPageStep")
                .<FormReplyEntity, FormReplyEntity>chunk(1)
                .reader(reader())
                .writer(items -> items.forEach(b -> log.info("{}", b.toString())))
                .build();
    }
    
    @StepScope
    @Bean
    public JpaPagingItemReader<FormReplyEntity> reader() {
        
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("postId", 1);
        
        return new JpaPagingItemReaderBuilder<FormReplyEntity>()
                .entityManagerFactory(entityManagerFactory)
                .name("reader")
                .parameterValues(parameterValues)
                .queryString("select entity from FormReplyEntity entity where formPost.postId = :postId")
                .pageSize(1)
                .build();
    }
}
