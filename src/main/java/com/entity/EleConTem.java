package com.entity;

import java.io.Serializable;

/**
 * (EleConTem)实体类
 *
 * @author makejava
 * @since 2021-02-01 09:53:21
 */
public class EleConTem implements Serializable {
    private static final long serialVersionUID = 884514753849743987L;

    private Integer id;

    private String rid;

    private String consNo;

    private String consName;

    private String areaName;

    private String eventTime;

    private Double papR;

    private Double papRPre;

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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public Double getPapR() {
        return papR;
    }

    public void setPapR(Double papR) {
        this.papR = papR;
    }

    public Double getPapRPre() {
        return papRPre;
    }

    public void setPapRPre(Double papRPre) {
        this.papRPre = papRPre;
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

    public Double getEle() {
        return ele;
    }

    public void setEle(Double ele) {
        this.ele = ele;
    }

    @Override
    public String toString() {
        return "EleConTem{" +
                "id=" + id +
                ", rid='" + rid + '\'' +
                ", consNo='" + consNo + '\'' +
                ", consName='" + consName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", papR=" + papR +
                ", papRPre=" + papRPre +
                ", papRDiff=" + papRDiff +
                ", tFactor=" + tFactor +
                ", ele=" + ele +
                '}';
    }
}