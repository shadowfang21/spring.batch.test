package com.sf.batch.test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="FORM_REPLY")
public class FormReplyEntity {
    
    @Id
    @Column(name="REPLY_ID")
    private Integer replyId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="POST_ID")
    private FormPostEntity formPost;
    
}
