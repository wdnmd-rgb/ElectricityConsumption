package com.service;

import com.entity.ConsEle;
import com.entity.TgLineLoss;
import com.entity.TgResult;

import java.util.List;

public interface TgLineLossService {
    List<TgLineLoss> queryTgLineLoss( String tgNo,String eventTime);
    List<TgResult> queryTgResult(String tgNo,String eventTime);
    List<ConsEle> queryConsEle(String tgNo,String eventTime);
}
