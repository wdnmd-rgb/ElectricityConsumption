package com.service;

import com.entity.AreaIds;
import com.entity.EleConWeibiao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (EleConWeibiao)表服务接口
 *
 * @author makejava
 * @since 2021-02-03 09:22:34
 */
public interface EleConWeibiaoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EleConWeibiao queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<EleConWeibiao> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param eleConWeibiao 实例对象
     * @return 实例对象
     */
    EleConWeibiao insert(EleConWeibiao eleConWeibiao);

    /**
     * 修改数据
     *
     * @param eleConWeibiao 实例对象
     * @return 实例对象
     */
    EleConWeibiao update(EleConWeibiao eleConWeibiao);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<String> selectIdByConsNo(List<String> consNos);

    List<EleConWeibiao> queryAllByConsNo(List<String> consNos);

    List<EleConWeibiao> queryAllResult();

    Map<String,EleConWeibiao> queryByRid(List<String> idsList);

    String queryAreaName(String areaName);
    String queryByAreaNo(String areaCode);

    String queryByTgOrg(String tgNo,String orgNo);
    List<AreaIds> queryAreaByTgOrg(String tgNo, String orgNo);
    List<String> queryByConsNo(String consNo);
    Map<String,EleConWeibiao> queryAllByTgOrg(@Param("tgNo") String tgNo, @Param("orgNo") String orgNo);

    int insertBatch(@Param("list") List<String> list);


}