package com.service;

import com.entity.EleCon;

import java.util.List;

/**
 * (EleCon)表服务接口
 *
 * @author makejava
 * @since 2021-02-03 09:39:43
 */
public interface EleConService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EleCon queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<EleCon> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param eleCon 实例对象
     * @return 实例对象
     */
    EleCon insert(EleCon eleCon);

    /**
     * 修改数据
     *
     * @param eleCon 实例对象
     * @return 实例对象
     */
    EleCon update(EleCon eleCon);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<EleCon> queryByRid(List<String> ids);

    int insertResult(List<EleCon> eleConList);
    boolean  deleteResult();

}