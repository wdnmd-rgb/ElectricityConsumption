package com.dao;

import com.entity.AreaIds;
import com.entity.EleConWeibiao;
import com.util.DataSource;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (EleConWeibiao)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-03 09:22:33
 */
@DataSource(value = "datasource1")
public interface EleConWeibiaoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EleConWeibiao queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<EleConWeibiao> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param eleConWeibiao 实例对象
     * @return 对象列表
     */
    List<EleConWeibiao> queryAll(EleConWeibiao eleConWeibiao);

    /**
     * 新增数据
     *
     * @param eleConWeibiao 实例对象
     * @return 影响行数
     */
    int insert(EleConWeibiao eleConWeibiao);



    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<EleConWeibiao> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<EleConWeibiao> entities);

    /**
     * 修改数据
     *
     * @param eleConWeibiao 实例对象
     * @return 影响行数
     */
    int update(EleConWeibiao eleConWeibiao);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<String> selectIdByConsNo(List<String> consNos);

    List<EleConWeibiao> queryAllByConsNo(List<String> consNos);

    List<EleConWeibiao> queryAllResult();

    @MapKey("rid")
    Map<String,EleConWeibiao> queryByRid(List<String> idsList);

    String queryAreaName(String areaName);

    String queryByAreaNo(String areaCode);

    String queryByTgOrg(@Param("tgNo") String tgNo,@Param("orgNo") String orgNo);

    List<AreaIds> queryAreaByTgOrg(@Param("tgNo") String tgNo, @Param("orgNo") String orgNo);
    List<String> queryByConsNo(String consNo);
    @MapKey("rid")
    Map<String,EleConWeibiao> queryAllByTgOrg(@Param("tgNo") String tgNo,@Param("orgNo") String orgNo);

    int insertBatch(@Param("list") List<String> list);

    String queryAreaCode(String consNo);




}