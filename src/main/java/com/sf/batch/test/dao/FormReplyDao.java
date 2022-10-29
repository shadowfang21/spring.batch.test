package com.sf.batch.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sf.batch.test.entity.FormReplyEntity;

@Repository
public interface FormReplyDao extends JpaRepository<FormReplyEntity, Integer>{

}
