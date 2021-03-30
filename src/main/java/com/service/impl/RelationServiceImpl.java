package com.service.impl;

import com.dao.RelationDao;
import com.entity.Relation;
import com.service.RelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Relation)表服务实现类
 *
 * @author makejava
 * @since 2021-03-22 16:30:14
 */
@Service("relationService")
public class RelationServiceImpl implements RelationService {
    @Resource
    private RelationDao relationDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Relation queryById(Integer id) {
        return this.relationDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Relation> queryAllByLimit(int offset, int limit) {
        return this.relationDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param relation 实例对象
     * @return 实例对象
     */
    @Override
    public Relation insert(Relation relation) {
        this.relationDao.insert(relation);
        return relation;
    }

    /**
     * 修改数据
     *
     * @param relation 实例对象
     * @return 实例对象
     */
    @Override
    public Relation update(Relation relation) {
        this.relationDao.update(relation);
        return this.queryById(relation.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.relationDao.deleteById(id) > 0;
    }

    @Override
    public Relation queryByTgNo(String tgNo) {
        return this.relationDao.queryByTgNo(tgNo);
    }
}