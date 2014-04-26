package cn.liuyb.app.common.dao;

import cn.liuyb.app.common.domain.BaseEntity;

public interface EntityDao<T extends BaseEntity> {

    public abstract void create(T entity);

    public abstract void update(T entity);

    public abstract void delete(T entity);

    public abstract T find(Object id);
    
    public abstract void flush();

}