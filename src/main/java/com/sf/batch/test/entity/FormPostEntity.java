package com.sf.batch.test.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy = "formPost")
    private List<FormReplyEntity> replies;
}
