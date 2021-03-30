package com.dao;

import com.entity.Electrics;
import com.util.DataSource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DataSource(value = "datasource2")
public interface ElectricsDao {
    int insertBatch(@Param("entities") List<Electrics> electrics);
}
