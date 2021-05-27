package com.dao;

import com.entity.ConsEle;
import com.entity.TgLineLoss;
import com.entity.TgResult;
import com.util.DataSource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DataSource(value = "datasource1")
public interface TgLineLossDao {
    List<TgLineLoss> queryTgLineLoss(@Param("tgNo") String tgNo,@Param("eventTime") String eventTime);
    List<TgResult> queryTgResult(@Param("tgNo") String tgNo, @Param("eventTime") String eventTime);
    List<ConsEle> queryConsEle(@Param("tgNo") String tgNo, @Param("eventTime") String eventTime);
}
