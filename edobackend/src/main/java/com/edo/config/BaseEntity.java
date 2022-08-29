package com.edo.config;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
public abstract class BaseEntity  {
    private String FileName;
    private String FileLoc;
    private String Thumbnail;
    private String FileSize;
    private String FileType;
}
