package cn.liuyb.app.dao.jpa;

import org.springframework.stereotype.Repository;

import cn.liuyb.app.common.dao.AbstractJpaDao;
import cn.liuyb.app.dao.AppDao;
import cn.liuyb.app.domain.App;

@Repository
public class AppDaoImpl extends AbstractJpaDao<App> implements AppDao {
	
	public AppDaoImpl() {
		super(App.class);
	}

	@Override
	public int countAll() {
		return this.count();
	}

	@Override
	public App findByPackageName(String packageName) {
		return this.findByUniqueProperty("App.findByPackageName", "packageName",packageName);
	}
	
	
}
