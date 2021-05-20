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

    private String tgNo;

    private String tgName;

    private String orgNo;

    private String orgName;

    private Double tFactor;

    private String assetNo;

    private String tmnlAssetNo;

    private String mpSn;

    private String cisTmnlAssetNo;

    private String ct;

    private String pt;

    private String typeCode;




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

    public String getTmnlAssetNo() {
        return tmnlAssetNo;
    }

    public void setTmnlAssetNo(String tmnlAssetNo) {
        this.tmnlAssetNo = tmnlAssetNo;
    }

    public String getMpSn() {
        return mpSn;
    }

    public void setMpSn(String mpSn) {
        this.mpSn = mpSn;
    }

    public String getCisTmnlAssetNo() {
        return cisTmnlAssetNo;
    }

    public void setCisTmnlAssetNo(String cisTmnlAssetNo) {
        this.cisTmnlAssetNo = cisTmnlAssetNo;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    @Override
    public String toString() {
        return "EleConWeibiao{" +
                "id=" + id +
                ", rid='" + rid + '\'' +
                ", areaName='" + areaName + '\'' +
                ", consNo='" + consNo + '\'' +
                ", consName='" + consName + '\'' +
                ", tgNo='" + tgNo + '\'' +
                ", tgName='" + tgName + '\'' +
                ", orgNo='" + orgNo + '\'' +
                ", orgName='" + orgName + '\'' +
                ", tFactor=" + tFactor +
                ", assetNo='" + assetNo + '\'' +
                ", tmnlAssetNo='" + tmnlAssetNo + '\'' +
                ", mpSn='" + mpSn + '\'' +
                ", cisTmnlAssetNo='" + cisTmnlAssetNo + '\'' +
                ", ct='" + ct + '\'' +
                ", pt='" + pt + '\'' +
                ", typeCode='" + typeCode + '\'' +
                '}';
    }
}