package com.ipf.automaticcarsgame.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.ZonedDateTime;

@MappedSuperclass
public class AudityEntity {
    @Column(name = "INSERT_DATE")
    private ZonedDateTime insertDate;

    @Column(name = "UPDATE_DATE")
    private ZonedDateTime updateDate;

    public ZonedDateTime getInsertDate() {
        return insertDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    @PreUpdate
    private void preUpdate() {
        this.updateDate = ZonedDateTime.now();
    }

    @PrePersist
    private void prePersist() {
        this.insertDate = ZonedDateTime.now();
    }

    @Override
    public String toString() {
        return "AudityEntity{" +
                "insertDate=" + insertDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
