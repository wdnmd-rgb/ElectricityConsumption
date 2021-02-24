package com.service;

import com.entity.EleConTem;

import java.util.List;

/**
 * (EleConTem)表服务接口
 *
 * @author makejava
 * @since 2021-02-01 09:53:23
 */
public interface EleConTemService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EleConTem queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<EleConTem> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param eleConTem 实例对象
     * @return 实例对象
     */
    EleConTem insert(EleConTem eleConTem);

    /**
     * 修改数据
     *
     * @param eleConTem 实例对象
     * @return 实例对象
     */
    EleConTem update(EleConTem eleConTem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<EleConTem> queryAll(EleConTem eleConTem);

    List<EleConTem> queryByConsNo(List<String> consNos);



}