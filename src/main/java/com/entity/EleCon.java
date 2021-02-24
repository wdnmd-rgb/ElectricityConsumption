package com.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (EleCon)实体类
 *
 * @author makejava
 * @since 2021-02-03 09:39:42
 */
public class EleCon implements Serializable {
    private static final long serialVersionUID = 586304514921855914L;

    private Integer id;

    private String rid;

    private Date eventTime;

    private Double papRDiff;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Double getPapRDiff() {
        return papRDiff;
    }

    public void setPapRDiff(Double papRDiff) {
        this.papRDiff = papRDiff;
    }

}