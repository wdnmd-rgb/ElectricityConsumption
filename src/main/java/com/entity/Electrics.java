package com.entity;

public class Electrics {
    private String rid;
    private String consNo;
    private String consName;
    private String areaName;
    private Double tFactor;
    private String eventTime;
    private Double papR;
    private String papRDiff;
    private String ele;
    private String tgNo;

    private String tgName;

    private String orgNo;

    private String orgName;

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

    public Double gettFactor() {
        return tFactor;
    }

    public void settFactor(Double tFactor) {
        this.tFactor = tFactor;
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

    public String getPapRDiff() {
        return papRDiff;
    }

    public void setPapRDiff(String papRDiff) {
        this.papRDiff = papRDiff;
    }

    public String getEle() {
        return ele;
    }

    public void setEle(String ele) {
        this.ele = ele;
    }

    public String getTgNo() {
        return tgNo;
    }

    public void setTgNo(String tgNo) {
        this.tgNo = tgNo;
    }

    public String getTgName() {
        return tgName;
    }

    public void setTgName(String tgName) {
        this.tgName = tgName;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Override
    public String toString() {
        return "Electrics{" +
                "rid='" + rid + '\'' +
                ", consNo='" + consNo + '\'' +
                ", consName='" + consName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", tFactor=" + tFactor +
                ", eventTime='" + eventTime + '\'' +
                ", papR=" + papR +
                ", papRDiff='" + papRDiff + '\'' +
                ", ele='" + ele + '\'' +
                ", tgNo='" + tgNo + '\'' +
                ", tgName='" + tgName + '\'' +
                ", orgNo='" + orgNo + '\'' +
                ", orgName='" + orgName + '\'' +
                '}';
    }
}