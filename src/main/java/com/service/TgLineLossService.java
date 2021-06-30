package com.service;

import com.entity.*;

import java.util.List;

public interface TgLineLossService {
    List<TgLineLoss> queryTgLineLoss( String tgNo,String eventTime);
    List<TgResult> queryTgResult(String tgNo,String date);
    List<TgResult> queryTgResult2(String tgNo);
    List<ConsEle> queryConsEle(String tgNo,String eventTime);
    List<MonitoringTg> queryMonitoringTg(String tgNo,Integer pageNum,Integer pageSize);
    int selectMonitoringTgNum(String tgNo);
    boolean addMonitoringTg(String tgNo);
    int selectTgNum(String tgNo);
    List<TgReport> queryTgReport(String tgNo,String date,Integer pageNum,Integer pageSize);
    int selectTgReportNum(String tgNo,String date);
    List<OrgReport> queryOrgReport(OrgReport orgReport,Integer pageNum,Integer pageSize);
    int selectOrgReportNum(OrgReport orgReport);
    List<TgConsReport> queryTgConsReport(TgConsReport tgConsReport,Integer pageNum,Integer pageSize);
    int selectTgConsReportNum(TgConsReport tgConsReport);
}
