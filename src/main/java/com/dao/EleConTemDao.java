package com.dao;

import com.entity.EleConTem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (EleConTem)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-01 09:53:22
 */
public interface EleConTemDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EleConTem queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<EleConTem> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param eleConTem 实例对象
     * @return 对象列表
     */
    List<EleConTem> queryAll(EleConTem eleConTem);

    /**
     * 新增数据
     *
     * @param eleConTem 实例对象
     * @return 影响行数
     */
    int insert(EleConTem eleConTem);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<EleConTem> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<EleConTem> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<EleConTem> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<EleConTem> entities);

    /**
     * 修改数据
     *
     * @param eleConTem 实例对象
     * @return 影响行数
     */
    int update(EleConTem eleConTem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<EleConTem> queryByConsNo(List<String> consNos);



}