package com.entity;

public class TgConsReport {
    private String cityName;
    private String countyName;
    private String orgNo;
    private String orgName;
    private String tgName;
    private String tgNo;
    private String tgTypeName;
    private String tgClass;
    private String tgClassComment;
    private String mergeLinelossRate;
    private String linelossRateInterval;
    private String consNo;
    private String rid;
    private String consName;
    private Double tFactor;
    private String assetNo;
    private Double ele;
    private Double pearson;
    private String dateDay;
    private String eleArray;
    private String tgEleArray;
    private String dateDayStart;
    private Double lossEle;
    private Double lossPerAvg;
    private String timeArray;
    private String maxIndex;


    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
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

    public String getTgTypeName() {
        return tgTypeName;
    }

    public void setTgTypeName(String tgTypeName) {
        this.tgTypeName = tgTypeName;
    }

    public String getTgClass() {
        return tgClass;
    }

    public void setTgClass(String tgClass) {
        this.tgClass = tgClass;
    }

    public String getTgClassComment() {
        return tgClassComment;
    }

    public void setTgClassComment(String tgClassComment) {
        this.tgClassComment = tgClassComment;
    }

    public String getMergeLinelossRate() {
        return mergeLinelossRate;
    }

    public void setMergeLinelossRate(String mergeLinelossRate) {
        this.mergeLinelossRate = mergeLinelossRate;
    }

    public String getLinelossRateInterval() {
        return linelossRateInterval;
    }

    public void setLinelossRateInterval(String linelossRateInterval) {
        this.linelossRateInterval = linelossRateInterval;
    }

    public String getConsNo() {
        return consNo;
    }

    public void setConsNo(String consNo) {
        this.consNo = consNo;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getConsName() {
        return consName;
    }

    public void setConsName(String consName) {
        this.consName = consName;
    }

    public Double gettFactor() {
        return tFactor;
    }

    public void settFactor(Double tFactor) {
        this.tFactor = tFactor;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    public Double getEle() {
        return ele;
    }

    public void setEle(Double ele) {
        this.ele = ele;
    }

    public Double getPearson() {
        return pearson;
    }

    public void setPearson(Double pearson) {
        this.pearson = pearson;
    }

    public String getDateDay() {
        return dateDay;
    }

    public void setDateDay(String dateDay) {
        this.dateDay = dateDay;
    }

    public String getEleArray() {
        return eleArray;
    }

    public void setEleArray(String eleArray) {
        this.eleArray = eleArray;
    }

    public String getTgEleArray() {
        return tgEleArray;
    }

    public void setTgEleArray(String tgEleArray) {
        this.tgEleArray = tgEleArray;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDateDayStart() {
        return dateDayStart;
    }

    public void setDateDayStart(String dateDayStart) {
        this.dateDayStart = dateDayStart;
    }

    public Double getLossEle() {
        return lossEle;
    }

    public void setLossEle(Double lossEle) {
        this.lossEle = lossEle;
    }

    public Double getLossPerAvg() {
        return lossPerAvg;
    }

    public void setLossPerAvg(Double lossPerAvg) {
        this.lossPerAvg = lossPerAvg;
    }

    public String getTimeArray() {
        return timeArray;
    }

    public void setTimeArray(String timeArray) {
        this.timeArray = timeArray;
    }

    public String getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(String maxIndex) {
        this.maxIndex = maxIndex;
    }

    @Override
    public String toString() {
        return "TgConsReport{" +
                "cityName='" + cityName + '\'' +
                ", countyName='" + countyName + '\'' +
                ", orgNo='" + orgNo + '\'' +
                ", orgName='" + orgName + '\'' +
                ", tgName='" + tgName + '\'' +
                ", tgNo='" + tgNo + '\'' +
                ", tgTypeName='" + tgTypeName + '\'' +
                ", tgClass='" + tgClass + '\'' +
                ", tgClassComment='" + tgClassComment + '\'' +
                ", mergeLinelossRate='" + mergeLinelossRate + '\'' +
                ", linelossRateInterval='" + linelossRateInterval + '\'' +
                ", consNo='" + consNo + '\'' +
                ", rid='" + rid + '\'' +
                ", consName='" + consName + '\'' +
                ", tFactor=" + tFactor +
                ", assetNo='" + assetNo + '\'' +
                ", ele=" + ele +
                ", pearson=" + pearson +
                ", dateDay='" + dateDay + '\'' +
                ", eleArray='" + eleArray + '\'' +
                ", tgEleArray='" + tgEleArray + '\'' +
                ", dateDayStart='" + dateDayStart + '\'' +
                ", lossEle=" + lossEle +
                ", lossPerAvg=" + lossPerAvg +
                ", timeArray='" + timeArray + '\'' +
                ", maxIndex='" + maxIndex + '\'' +
                '}';
    }
}
