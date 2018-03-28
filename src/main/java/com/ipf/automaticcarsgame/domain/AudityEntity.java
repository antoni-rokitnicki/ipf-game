package com.ipf.automaticcarsgame.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@MappedSuperclass
public class AudityEntity {
    @Column(name = "INSERT_DATE")
    private Date insertDate;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    public Date getInsertDate() {
        return insertDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    @PreUpdate
    private void preUpdate() {
        this.updateDate = new Date();
    }

    @PrePersist
    private void prePersist() {
        this.insertDate = new Date();
    }

    @Override
    public String toString() {
        return "AudityEntity{" +
                "insertDate=" + insertDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
