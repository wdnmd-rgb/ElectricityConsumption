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
    List<OrgReport> queryOrgReport(String orgNo,Integer pageNum,Integer pageSize);
    int selectOrgReportNum(String orgNo);
    List<TgConsReport> queryTgConsReport(TgConsReport tgConsReport,Integer pageNum,Integer pageSize);
    List<TgConsReport> queryTgConsReport(TgConsReport tgConsReport);
    int selectTgConsReportNum(TgConsReport tgConsReport);
    List<TgLossReport> queryTgLossReport(TgLossReport tgLossReport,Integer pageNum,Integer pageSize);
    int selectTgLossReportNum(TgLossReport tgLossReport);
    List<ExcConsReport> queryExcConsReport(ExcConsReport excConsReport,Integer pageNum,Integer pageSize);
    int selectExcConsReportNum(ExcConsReport excConsReport);
    List<Relation> queryRelation(Relation relation);
}
