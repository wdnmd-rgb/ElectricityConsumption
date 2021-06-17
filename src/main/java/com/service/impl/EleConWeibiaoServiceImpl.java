package com.service.impl;

import com.dao.EleConWeibiaoDao;
import com.entity.AreaIds;
import com.entity.EleConWeibiao;
import com.service.EleConWeibiaoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (EleConWeibiao)表服务实现类
 *
 * @author makejava
 * @since 2021-02-03 09:22:34
 */
@Service("eleConWeibiaoService")
public class EleConWeibiaoServiceImpl implements EleConWeibiaoService {
    @Resource
    private EleConWeibiaoDao eleConWeibiaoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EleConWeibiao queryById(Integer id) {
        return this.eleConWeibiaoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<EleConWeibiao> queryAllByLimit(int offset, int limit) {
        return this.eleConWeibiaoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param eleConWeibiao 实例对象
     * @return 实例对象
     */
    @Override
    public EleConWeibiao insert(EleConWeibiao eleConWeibiao) {
        this.eleConWeibiaoDao.insert(eleConWeibiao);
        return eleConWeibiao;
    }

    /**
     * 修改数据
     *
     * @param eleConWeibiao 实例对象
     * @return 实例对象
     */
    @Override
    public EleConWeibiao update(EleConWeibiao eleConWeibiao) {
        this.eleConWeibiaoDao.update(eleConWeibiao);
        return this.queryById(eleConWeibiao.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.eleConWeibiaoDao.deleteById(id) > 0;
    }

    @Override
    public List<String> selectIdByConsNo(List<String> consNos) {
        return this.eleConWeibiaoDao.selectIdByConsNo(consNos);
    }

    @Override
    public List<EleConWeibiao> queryAllByConsNo(List<String> consNos) {
        return this.eleConWeibiaoDao.queryAllByConsNo(consNos);
    }

    @Override
    public List<EleConWeibiao> queryAllResult() {
        return this.eleConWeibiaoDao.queryAllResult();
    }

    @Override
    public Map<String, EleConWeibiao> queryByRid(List<String> idsList) {
        return this.eleConWeibiaoDao.queryByRid(idsList);
    }

    @Override
    public String queryAreaName(String areaName) {
        return this.eleConWeibiaoDao.queryAreaName(areaName);
    }

    @Override
    public String queryByAreaNo(String areaCode) {
        return this.eleConWeibiaoDao.queryByAreaNo(areaCode);
    }

    @Override
    public String queryByTgOrg(String tgNo, String orgNo) {
        return this.eleConWeibiaoDao.queryByTgOrg(tgNo, orgNo);
    }


    @Override
    public List<AreaIds> queryAreaByTgOrg(String tgNo, String orgNo) {
        return this.eleConWeibiaoDao.queryAreaByTgOrg(tgNo,orgNo);
    }

    @Override
    public List<String> queryByConsNo(String consNo) {
        return this.eleConWeibiaoDao.queryByConsNo(consNo);
    }

    @Override
    public Map<String,EleConWeibiao> queryAllByTgOrg(String tgNo, String orgNo) {
        return this.eleConWeibiaoDao.queryAllByTgOrg(tgNo, orgNo);
    }

    @Override
    public int insertBatch(List<String> list) {
        return this.eleConWeibiaoDao.insertBatch(list);
    }

    @Override
    public String queryAreaCode(String consNo) {
        return this.eleConWeibiaoDao.queryAreaCode(consNo);
    }


}