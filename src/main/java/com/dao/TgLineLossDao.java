package com.dao;

import com.entity.ConsEle;
import com.entity.MonitoringTg;
import com.entity.TgLineLoss;
import com.entity.TgResult;
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
}
