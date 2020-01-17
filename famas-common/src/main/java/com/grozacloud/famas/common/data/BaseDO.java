package com.grozacloud.famas.common.data;

import com.grozacloud.famas.common.utils.SnowFlakeUtil;

import java.sql.Timestamp;

/**
 * @author james mu
 * @date 2020/1/16 16:51
 */
public class BaseDO {

    private String id;

    private Timestamp dateCreate;

    private Timestamp dateUpdate;

    public BaseDO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public BaseDO(String id, Timestamp dateCreate, Timestamp dateUpdate) {
        this.id = SnowFlakeUtil.getId();
        this.dateCreate = dateCreate;
        this.dateUpdate = dateUpdate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Base [id=");
        builder.append(id);
        builder.append(", dateCreate=");
        builder.append(dateCreate);
        builder.append(", dateUpdate=");
        builder.append(dateUpdate);
        builder.append("]");
        return builder.toString();
    }
}
