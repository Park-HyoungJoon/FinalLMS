package com.edo.util.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
@MappedSuperclass
public abstract class BaseFileEntity {
    @Column(length = 255)
    public String fileName;
    @Column(length = 255)
    private String fileLoc;
    @Column(length = 255)
    private String thumbnail;
    private Long fileSize;
    @Column(length = 1)
    private String fileType;
}
