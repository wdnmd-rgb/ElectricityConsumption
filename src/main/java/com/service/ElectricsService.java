package com.service;

import com.entity.Electrics;

import java.util.List;

public interface ElectricsService {
    int insertBatch(List<Electrics> electrics);
}
