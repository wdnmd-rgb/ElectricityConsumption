package com.dao;

import com.entity.Relation;
import com.util.DataSource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Relation)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-22 16:30:13
 */
@DataSource(value = "datasource1")
public interface RelationDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Relation queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Relation> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param relation 实例对象
     * @return 对象列表
     */
    List<Relation> queryAll(Relation relation);

    /**
     * 新增数据
     *
     * @param relation 实例对象
     * @return 影响行数
     */
    int insert(Relation relation);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Relation> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Relation> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Relation> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Relation> entities);

    /**
     * 修改数据
     *
     * @param relation 实例对象
     * @return 影响行数
     */
    int update(Relation relation);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    Relation queryByTgNo(String tgNo);

}