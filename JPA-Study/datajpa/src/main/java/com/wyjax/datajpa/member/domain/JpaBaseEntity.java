package com.wyjax.datajpa.member.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

// jpa에서는 진짜 상속관계가 있고, 속성만 상속하는게 있다.
@Getter
@MappedSuperclass // >> 속성만 상속
public class JpaBaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    // JPA가 제공한다. persist하기 전에 이벤트 발생
    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    // update 되기 전에 실행
    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
