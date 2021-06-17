package com.entity;

public class TgResult {
    private String orgName;
    private String tgName;
    private String tgNo;
    private String eventTime;
    private Double ppq;
    private Double upq;
    private Double lossPq;
    private Double rate;
    private int count;
    private int realCount;
    private int remark0;
    private int remark1;
    private int remark2;
    private int remark3;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getTgName() {
        return tgName;
    }

    public void setTgName(String tgName) {
        this.tgName = tgName;
    }

    public String getTgNo() {
        return tgNo;
    }

    public void setTgNo(String tgNo) {
        this.tgNo = tgNo;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRealCount() {
        return realCount;
    }

    public void setRealCount(int realCount) {
        this.realCount = realCount;
    }

    public int getRemark0() {
        return remark0;
    }

    public void setRemark0(int remark0) {
        this.remark0 = remark0;
    }

    public int getRemark1() {
        return remark1;
    }

    public void setRemark1(int remark1) {
        this.remark1 = remark1;
    }

    public int getRemark2() {
        return remark2;
    }

    public void setRemark2(int remark2) {
        this.remark2 = remark2;
    }

    public int getRemark3() {
        return remark3;
    }

    public void setRemark3(int remark3) {
        this.remark3 = remark3;
    }

    public Double getPpq() {
        return ppq;
    }

    public void setPpq(Double ppq) {
        this.ppq = ppq;
    }

    public Double getUpq() {
        return upq;
    }

    public void setUpq(Double upq) {
        this.upq = upq;
    }

    public Double getLossPq() {
        return lossPq;
    }

    public void setLossPq(Double lossPq) {
        this.lossPq = lossPq;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "TgResult{" +
                "orgName='" + orgName + '\'' +
                ", tgName='" + tgName + '\'' +
                ", tgNo='" + tgNo + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", ppq=" + ppq +
                ", upq=" + upq +
                ", lossPq=" + lossPq +
                ", rate=" + rate +
                ", count=" + count +
                ", realCount=" + realCount +
                ", remark0=" + remark0 +
                ", remark1=" + remark1 +
                ", remark2=" + remark2 +
                ", remark3=" + remark3 +
                '}';
    }
}
