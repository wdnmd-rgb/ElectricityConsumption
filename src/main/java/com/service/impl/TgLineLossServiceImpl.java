package com.service.impl;

import com.dao.TgLineLossDao;
import com.entity.ConsEle;
import com.entity.TgLineLoss;
import com.entity.TgResult;
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
    public List<TgResult> queryTgResult(String tgNo, String eventTime) {
        return tgLineLossDao.queryTgResult(tgNo,eventTime);
    }

    @Override
    public List<ConsEle> queryConsEle(String tgNo, String eventTime) {
        return tgLineLossDao.queryConsEle(tgNo,eventTime);
    }
}
