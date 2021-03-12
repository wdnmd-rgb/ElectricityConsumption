package com.entity;

public class Cons {
    private String consName;
    private String areaName;
    private String voltage;
    private String consNo;

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

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getConsNo() {
        return consNo;
    }

    public void setConsNo(String consNo) {
        this.consNo = consNo;
    }

    @Override
    public String toString() {
        return "Cons{" +
                "consName='" + consName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", voltage='" + voltage + '\'' +
                ", consNo='" + consNo + '\'' +
                '}';
    }
}
