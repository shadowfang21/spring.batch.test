package com.sf.batch.test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "FORM_POST")
public class FormPostEntity {
    
    @Id
    @Column(name="POST_ID")
    private Integer postId;
    
    @Column(name="MESSAGE")
    private String message;
}
