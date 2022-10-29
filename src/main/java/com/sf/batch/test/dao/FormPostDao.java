package com.sf.batch.test.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sf.batch.test.entity.FormPostEntity;

@Repository
public interface FormPostDao extends JpaRepository<FormPostEntity, Integer>{
    
    Page<FormPostEntity> findAllByPostId(String postId, Pageable page);
}
