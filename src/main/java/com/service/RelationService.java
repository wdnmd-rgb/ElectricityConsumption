package com.service;

import com.entity.Relation;

import java.util.List;

/**
 * (Relation)表服务接口
 *
 * @author makejava
 * @since 2021-03-22 16:30:13
 */
public interface RelationService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Relation queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Relation> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param relation 实例对象
     * @return 实例对象
     */
    Relation insert(Relation relation);

    /**
     * 修改数据
     *
     * @param relation 实例对象
     * @return 实例对象
     */
    Relation update(Relation relation);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    Relation queryByTgNo(String tgNo);

}