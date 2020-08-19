package com.java.moudle.common.service.impl;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.java.until.dba.BaseDao;
import com.java.until.dba.BaseDomain;

public class BaseServiceImpl<R extends BaseDao,T extends BaseDomain> {

    @Inject
    protected R dao;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(String id) {
        return (T) dao.get(id);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param
     */
    @Transactional(readOnly = false)
    public void save(T entity) {

        dao.save(entity);
    }
    /**
     * 删除数据
     *
     * @param
     */
    @Transactional(readOnly = false)
    public void delete(String id) {
        dao.delete(id);
    }

}
