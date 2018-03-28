package com.ipf.automaticcarsgame.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
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

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "AudityEntity{" +
                "insertDate=" + insertDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
