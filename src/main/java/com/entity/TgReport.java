package com.entity;

public class TgReport {
    private String tgNo;
    private Double ppq;
    private Double upq;
    private Double lossPq;
    private Double lossPer;
    private String dateDay;
    private int count;
    private int realCount;
    private int suspicious;

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

    public String getDateDay() {
        return dateDay;
    }

    public void setDateDay(String dateDay) {
        this.dateDay = dateDay;
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

    public int getSuspicious() {
        return suspicious;
    }

    public void setSuspicious(int suspicious) {
        this.suspicious = suspicious;
    }
}
