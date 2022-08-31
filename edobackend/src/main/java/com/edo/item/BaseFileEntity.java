package com.edo.item;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
public abstract class BaseFileEntity {
    @Column(length = 255)
    private String FileName;
    @Column(length = 255)
    private String FileLoc;
    @Column(length = 255)
    private String Thumbnail;
    private Long FileSize;
    @Column(length = 1)
    private String FileType;
}
