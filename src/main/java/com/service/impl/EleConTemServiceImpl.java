package com.service.impl;

import com.dao.EleConTemDao;
import com.entity.EleConTem;
import com.service.EleConTemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (EleConTem)表服务实现类
 *
 * @author makejava
 * @since 2021-02-01 09:53:23
 */
@Service("eleConTemService")
public class EleConTemServiceImpl implements EleConTemService {
    @Resource
    private EleConTemDao eleConTemDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EleConTem queryById(Integer id) {
        return this.eleConTemDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<EleConTem> queryAllByLimit(int offset, int limit) {
        return this.eleConTemDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param eleConTem 实例对象
     * @return 实例对象
     */
    @Override
    public EleConTem insert(EleConTem eleConTem) {
        this.eleConTemDao.insert(eleConTem);
        return eleConTem;
    }

    /**
     * 修改数据
     *
     * @param eleConTem 实例对象
     * @return 实例对象
     */
    @Override
    public EleConTem update(EleConTem eleConTem) {
        this.eleConTemDao.update(eleConTem);
        return this.queryById(eleConTem.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.eleConTemDao.deleteById(id) > 0;
    }

    @Override
    public List<EleConTem> queryAll(EleConTem eleConTem) {
        return this.eleConTemDao.queryAll(eleConTem);
    }

    @Override
    public List<EleConTem> queryByConsNo(List<String> consNos) {
        return this.eleConTemDao.queryByConsNo(consNos);
    }


}