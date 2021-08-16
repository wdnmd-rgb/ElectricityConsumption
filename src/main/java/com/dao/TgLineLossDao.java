package com.dao;

import com.entity.*;
import com.util.DataSource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DataSource(value = "datasource1")
public interface TgLineLossDao {
    List<TgLineLoss> queryTgLineLoss(@Param("tgNo") String tgNo,@Param("eventTime") String eventTime);
    List<TgResult> queryTgResult(@Param("tgNo") String tgNo,@Param("date")String date);
    List<TgResult> queryTgResult2(String tgNo);
    List<ConsEle> queryConsEle(@Param("tgNo") String tgNo, @Param("eventTime") String eventTime);
    List<MonitoringTg> queryMonitoringTg(String tgNo);
    int selectMonitoringTgNum(String tgNo);
    int addMonitoringTg(String tgNo);
    int selectTgNum(String tgNo);
    List<TgReport> queryTgReport(@Param("tgNo") String tgNo,@Param("date")String date);
    int selectTgReportNum(@Param("tgNo") String tgNo,@Param("date")String date);
    List<OrgReport> queryOrgReport(String orgNo);
    int selectOrgReportNum(String orgNo);
    List<TgConsReport> queryTgConsReport(TgConsReport tgConsReport);
    int selectTgConsReportNum(TgConsReport tgConsReport);
}
