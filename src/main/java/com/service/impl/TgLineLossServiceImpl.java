package com.service.impl;

import com.dao.TgLineLossDao;
import com.entity.ConsEle;
import com.entity.MonitoringTg;
import com.entity.TgLineLoss;
import com.entity.TgResult;
import com.github.pagehelper.PageHelper;
import com.service.TgLineLossService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("tgLineLossService")
public class TgLineLossServiceImpl implements TgLineLossService {
    @Resource
    private TgLineLossDao tgLineLossDao;
    @Override
    public List<TgLineLoss> queryTgLineLoss(String tgNo, String eventTime) {
        return tgLineLossDao.queryTgLineLoss(tgNo,eventTime);
    }

    @Override
    public List<TgResult> queryTgResult(String tgNo,String date) {
        return tgLineLossDao.queryTgResult(tgNo,date);
    }

    @Override
    public List<TgResult> queryTgResult2(String tgNo) {
        return tgLineLossDao.queryTgResult2(tgNo);
    }

    @Override
    public List<ConsEle> queryConsEle(String tgNo, String eventTime) {
        return tgLineLossDao.queryConsEle(tgNo,eventTime);
    }

    @Override
    public List<MonitoringTg> queryMonitoringTg(String tgNo,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return tgLineLossDao.queryMonitoringTg(tgNo);
    }

    @Override
    public int selectMonitoringTgNum(String tgNo) {
        return tgLineLossDao.selectMonitoringTgNum(tgNo);
    }

    @Override
    public boolean addMonitoringTg(String tgNo) {
        return tgLineLossDao.addMonitoringTg(tgNo)>0;
    }

    @Override
    public int selectTgNum(String tgNo) {
        return tgLineLossDao.selectTgNum(tgNo);
    }
}
