package com.service.impl;

import com.dao.ElectricsDao;
import com.entity.Electrics;
import com.service.ElectricsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("electricsService")
public class ElectricsServiceImpl implements ElectricsService {

    @Resource
    private ElectricsDao electricsDao;

    @Override
    public int insertBatch(List<Electrics> electrics) {
        return this.electricsDao.insertBatch(electrics);
    }
}
