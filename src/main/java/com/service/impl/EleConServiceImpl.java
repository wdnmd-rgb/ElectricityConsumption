package com.service.impl;

import com.dao.EleConDao;
import com.entity.EleCon;
import com.service.EleConService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (EleCon)表服务实现类
 *
 * @author makejava
 * @since 2021-02-03 09:39:44
 */
@Service("eleConService")
public class EleConServiceImpl implements EleConService {
    @Resource
    private EleConDao eleConDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EleCon queryById(Integer id) {
        return this.eleConDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<EleCon> queryAllByLimit(int offset, int limit) {
        return this.eleConDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param eleCon 实例对象
     * @return 实例对象
     */
    @Override
    public EleCon insert(EleCon eleCon) {
        this.eleConDao.insert(eleCon);
        return eleCon;
    }

    /**
     * 修改数据
     *
     * @param eleCon 实例对象
     * @return 实例对象
     */
    @Override
    public EleCon update(EleCon eleCon) {
        this.eleConDao.update(eleCon);
        return this.queryById(eleCon.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.eleConDao.deleteById(id) > 0;
    }

    @Override
    public List<EleCon> queryByRid(List<String> ids) {
        return this.eleConDao.queryByRid(ids);
    }

    @Override
    public int insertResult(List<EleCon> eleConList) {
        return this.eleConDao.insertResult(eleConList);
    }

    @Override
    public boolean deleteResult() {
        return this.eleConDao.deleteResult() > 0;
    }
}