package com.service;

import com.entity.ConsEle;
import com.entity.MonitoringTg;
import com.entity.TgLineLoss;
import com.entity.TgResult;

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
}
