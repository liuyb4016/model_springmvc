package cn.liuyb.app.common.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.liuyb.app.common.domain.BaseEntity;

public abstract class AbstractFlushModeCommitDao<T extends BaseEntity> extends AbstractJpaDao<T> {
	
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;
	
	@Override
    protected EntityManager getEntityManager() {
        return super.getEntityManager();
    }
	
	protected AbstractFlushModeCommitDao(Class<T> entityClass) {
		super(entityClass);
	}

}
