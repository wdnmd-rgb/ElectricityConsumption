package com.entity;

import java.io.Serializable;

/**
 * (EleConWeibiao)实体类
 *
 * @author makejava
 * @since 2021-02-03 09:22:33
 */
public class EleConWeibiao implements Serializable {
    private static final long serialVersionUID = -68624834588706970L;

    private Integer id;

    private String rid;

    private String areaName;

    private String consNo;

    private String consName;

    private String eventTime;

    private Double papRDiff;

    private Double tFactor;

    private Double ele;


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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getConsNo() {
        return consNo;
    }

    public void setConsNo(String consNo) {
        this.consNo = consNo;
    }

    public String getConsName() {
        return consName;
    }

    public void setConsName(String consName) {
        this.consName = consName;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public Double getPapRDiff() {
        return papRDiff;
    }

    public void setPapRDiff(Double papRDiff) {
        this.papRDiff = papRDiff;
    }

    public Double getTFactor() {
        return tFactor;
    }

    public void setTFactor(Double tFactor) {
        this.tFactor = tFactor;
    }

    public Double gettFactor() {
        return tFactor;
    }

    public void settFactor(Double tFactor) {
        this.tFactor = tFactor;
    }

    public Double getEle() {
        return ele;
    }

    public void setEle(Double ele) {
        this.ele = ele;
    }

    @Override
    public String toString() {
        return "EleConWeibiao{" +
                "id=" + id +
                ", rid='" + rid + '\'' +
                ", areaName='" + areaName + '\'' +
                ", consNo='" + consNo + '\'' +
                ", consName='" + consName + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", papRDiff=" + papRDiff +
                ", tFactor=" + tFactor +
                ", ele=" + ele +
                '}';
    }
}