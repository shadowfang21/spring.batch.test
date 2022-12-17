package com.sf.batch.test.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.sf.batch.test.BaseTestConfiguration;
import com.sf.batch.test.entity.FormPostEntity;

import lombok.extern.slf4j.Slf4j;

@SpringJUnitConfig
@SpringBootTest(classes={BaseTestConfiguration.class})
@Transactional
@Slf4j
class FormPostDaoIT {
    
    @Autowired
    private FormReplyDao dao;
    
    @Autowired
    private FormPostDao postDao;
    
    @Test
    void test() {
        FormPostEntity p = new FormPostEntity();
        p.setPostId(1);
        p.setMessage("123");
        postDao.save(p);
        
//        Optional.of(new FormReplyEntity())
//            .ifPresent(r -> {
//                r.setFormPost(p);
//                r.setReplyId(1);
//                dao.save(r);
//            });
//        
//        Optional.of(new FormReplyEntity())
//            .ifPresent(r -> {
//                r.setFormPost(p);
//                r.setReplyId(2);
//                dao.save(r);
//            });
//        
//        Optional.of(new FormReplyEntity())
//            .ifPresent(r -> {
//                r.setFormPost(p);
//                r.setReplyId(3);
//                dao.save(r);
//            });
        
        dao.findAll().forEach(b -> log.info("{}", b.toString()));
    }

}
