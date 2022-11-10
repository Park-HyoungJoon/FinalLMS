package com.edo.util.item;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
public abstract class BaseTimeEntity {
    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    @CreatedDate
    @Column( columnDefinition = "timestamp")
    private LocalDateTime regTime;

    @LastModifiedDate
    @Column(columnDefinition = "timestamp")
    private LocalDateTime updateTime;

//    regTime 출력
    @PrePersist
    public void createRegTime() {
        this.regTime = LocalDateTime.now();
    }
}
