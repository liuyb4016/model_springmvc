package cn.liuyb.app.common.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;

import cn.liuyb.app.common.domain.BaseEntity;
import cn.liuyb.app.common.utils.Slf4jLogUtils;
public abstract class AbstractJpaDao<T extends BaseEntity> implements EntityDao<T> {

    protected final Logger logger = Slf4jLogUtils.getLogger(getClass());

    @PersistenceContext(unitName="acme")
    protected EntityManager entityManager;

    private final Class<T> entityClass;

    protected AbstractJpaDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void flush() {
    	getEntityManager().flush();
    }
    /*
     * (non-Javadoc)
     * 
     * @see cn.com.robusoft.mdesk.common.dao.EntityDao#create(T)
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.robusoft.mdesk.common.dao.EntityDao#update(T)
     */
    public void update(T entity) {
        getEntityManager().merge(entity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.robusoft.mdesk.common.dao.EntityDao#delete(T)
     */
    public void delete(T entity) {
        getEntityManager().remove(find(entity.getId()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.robusoft.mdesk.common.dao.EntityDao#find(java.lang.Object)
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> findRange(int[] range) {
        @SuppressWarnings("rawtypes")
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    protected T findByUniqueProperty(String namedQuery, String propertyName, Object propertyValue) {
        return findByUniqueProperties(namedQuery, new Property(propertyName, propertyValue));
    }

    protected T findByUniqueProperty(String namedQuery, Object propertyValue) {
        return findByUniqueProperties(namedQuery, propertyValue);
    }

    @SuppressWarnings("unchecked")
    protected T findByUniqueProperties(String namedQuery, Object... propertyValues) {
        try {
            return (T) createNamedQuery(namedQuery, propertyValues).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    protected T findByUniqueProperties(String namedQuery, Property... properties) {
        return findByUniqueProperties(namedQuery, (Object[]) properties);
    }

    protected List<T> findByProperty(String namedQuery, String propertyName, Object propertyValue) {
        return findByProperties(namedQuery, new Property(propertyName, propertyValue));
    }

    protected List<T> findByProperty(String namedQuery, String propertyName, int startPosition, int maxResult, Object propertyValue) {
        return findByProperties(namedQuery, startPosition, maxResult, new Property(propertyName, propertyValue));
    }
    
    protected List<T> findByProperty(String namedQuery, Object propertyValue) {
        return findByProperties(namedQuery, propertyValue);
    }

    protected List<T> findByProperty(String namedQuery, int startPosition, int maxResult, Object propertyValue) {
        return findByProperties(namedQuery, startPosition, maxResult, propertyValue);
    }
    
    @SuppressWarnings("unchecked")
    protected List<T> findByProperties(String namedQuery, Object... propertyValues) {
        return createNamedQuery(namedQuery, propertyValues).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    protected List<T> findByProperties(String namedQuery, int startPosition, int maxResult, Object... propertyValues) {
        return createNamedQuery(namedQuery, startPosition, maxResult, propertyValues).getResultList();
    }
    
    @SuppressWarnings({ "hiding", "unchecked" })
	protected <T> List<T> findObjectsByProperties(String namedQuery, int startPosition, int maxResult, Object... propertyValues) {
        return createNamedQuery(namedQuery, startPosition, maxResult, propertyValues).getResultList();
    }
    
    @SuppressWarnings({ "hiding", "unchecked" })
	protected <T> List<T> findObjectsByProperties(String namedQuery, Object... propertyValues) {
        return createNamedQuery(namedQuery, propertyValues).getResultList();
    }
    
	protected Object findObjectByProperties(String namedQuery, Object... propertyValues) {
        return createNamedQuery(namedQuery, propertyValues).getSingleResult();
    }
    
    protected List<T> findByProperties(String namedQuery, Property... properties) {
        return findByProperties(namedQuery, (Object[]) properties);
    }

    protected int countByProperty(String namedQuery, String propertyName, Object propertyValue) {
        return countByProperties(namedQuery, new Property(propertyName, propertyValue));
    }

    protected int countByProperty(String namedQuery, Object propertyValue) {
        return countByProperties(namedQuery, propertyValue);
    }

    protected int countByProperties(String namedQuery, Property... properties) {
        return countByProperties(namedQuery, (Object[]) properties);
    }

    protected int countByProperties(String namedQuery, Object... propertyValues) {
        Long count = (Long) createNamedQuery(namedQuery, propertyValues).getSingleResult();
        if(count!=null){
        	return count.intValue();
        }else{
        	return 0;
        }
    }

    private Query createNamedQuery(String namedQuery, Object... propertyValues) {

        return createNamedQuery(namedQuery, -1, -1, propertyValues);
    }

    private Query createNamedQuery(String namedQuery, int startPosition, int maxResult, Object... propertyValues) {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        int i = 1;
        for (Object propertyValue : propertyValues) {
            if (propertyValue instanceof Property) {
                Property property = (Property) propertyValue;
                query.setParameter(property.name, property.value);
            } else {
                query.setParameter(i, propertyValue);
            }
            i++;
        }
        if (startPosition >=0 && maxResult > 0) {
            query.setFirstResult(startPosition);
            query.setMaxResults(maxResult);            
        }
        return query;
    }

    public int executeUpdate(String namedQuery, Object... propertyValues) {
        Query query = createNamedQuery(namedQuery, -1, -1, propertyValues);
        return query.executeUpdate();
    }
    
    protected static Property p(String name, Object value) {
        return new Property(name, value);
    }
    
    public static class Property {

        public String name;

        public Object value;

        public Property(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }
    
    /**
     * 以下为使用  原生sql进行查询操作示例 
     * 
     * String queryString = "select * from traffic_log_login_near30 where mdn=?1 ";
 	 *	Map<String, Object> params = new HashMap<String,Object>();
 	 *	params.put("1", mdn);
 	 *	return getByNativeQuery(TrafficLogLoginNear30.class,queryString, params );
     * 
     */
    
   
    @SuppressWarnings({"hiding", "unchecked"})
    protected <T> List<T> findSqlByProperties(Class<T> persistentClass, String nativeQuery, Object... propertyValues){
        return createNativeQuery(nativeQuery, propertyValues).getResultList();
    }
    
    @SuppressWarnings({"hiding", "unchecked"})
    protected <T> List<T> findSqlObjectsByProperties(String nativeQuery, Object... propertyValues){
    	 return createNativeQuery(nativeQuery, propertyValues).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    protected List<T> findSqlByProperties(String nativeQuery, int startPosition, int maxResult, Object... propertyValues) {
        return createNativeQuery(nativeQuery, startPosition, maxResult, propertyValues).getResultList();
    }
    
    @SuppressWarnings({ "hiding", "unchecked" })
	protected <T> List<T> findSqlObjectsByProperties(String nativeQuery, int startPosition, int maxResult, Object... propertyValues) {
        return createNativeQuery(nativeQuery, startPosition, maxResult, propertyValues).getResultList();
    }
    
    protected int countSqlByProperties(String nativeQuery, Object... propertyValues) {
    	nativeQuery = "SELECT COUNT(1) FROM ( "+nativeQuery+" ) __temp_table";
    	Object o = createNativeQuery(nativeQuery, propertyValues).getSingleResult();
        return Integer.valueOf(o.toString());
    }
    
    private Query createNativeQuery(String nativeQuery, Object... propertyValues) {
        return createNativeQuery(nativeQuery, -1, -1, propertyValues);
    }
    
    private Query createNativeQuery(String nativeQuery, int startPosition, int maxResult, Object... propertyValues) {
        Query query = getEntityManager().createNativeQuery(nativeQuery);
        int i = 1;
        for (Object propertyValue : propertyValues) {
            if (propertyValue instanceof Property) {
                Property property = (Property) propertyValue;
                query.setParameter(property.name, property.value);
            } else {
                query.setParameter(i, propertyValue);
            }
            i++;
        }
        if (startPosition >=0 && maxResult > 0) {
            query.setFirstResult(startPosition);
            query.setMaxResults(maxResult);            
        }
        return query;
    }   
}