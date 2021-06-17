package com.entity;

public class TgLineLoss {
    private String tgNo;
    private Double ppq;
    private Double upq;
    private Double lossPq;
    private Double lossPer;
    private Double ppqTol;
    private Double upqTol;
    private Double lossPqTol;
    private Double lossPerTol;
    private String eventTime;
    private int remark;

    public String getTgNo() {
        return tgNo;
    }

    public void setTgNo(String tgNo) {
        this.tgNo = tgNo;
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

    public Double getLossPer() {
        return lossPer;
    }

    public void setLossPer(Double lossPer) {
        this.lossPer = lossPer;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public int getRemark() {
        return remark;
    }

    public void setRemark(int remark) {
        this.remark = remark;
    }

    public Double getPpqTol() {
        return ppqTol;
    }

    public void setPpqTol(Double ppqTol) {
        this.ppqTol = ppqTol;
    }

    public Double getUpqTol() {
        return upqTol;
    }

    public void setUpqTol(Double upqTol) {
        this.upqTol = upqTol;
    }

    public Double getLossPqTol() {
        return lossPqTol;
    }

    public void setLossPqTol(Double lossPqTol) {
        this.lossPqTol = lossPqTol;
    }

    public Double getLossPerTol() {
        return lossPerTol;
    }

    public void setLossPerTol(Double lossPerTol) {
        this.lossPerTol = lossPerTol;
    }

    @Override
    public String toString() {
        return "TgLineLoss{" +
                "tgNo='" + tgNo + '\'' +
                ", ppq=" + ppq +
                ", upq=" + upq +
                ", lossPq=" + lossPq +
                ", lossPer=" + lossPer +
                ", ppqTol=" + ppqTol +
                ", upqTol=" + upqTol +
                ", lossPqTol=" + lossPqTol +
                ", lossPerTol=" + lossPerTol +
                ", eventTime='" + eventTime + '\'' +
                ", remark=" + remark +
                '}';
    }
}
