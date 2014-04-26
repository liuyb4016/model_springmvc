package cn.liuyb.app.dao.jpa;


import java.util.List;

import org.springframework.stereotype.Repository;

import cn.liuyb.app.common.dao.AbstractFlushModeCommitDao;
import cn.liuyb.app.dao.UserDao;
import cn.liuyb.app.domain.User;

@Repository
public class UserDaoImpl extends AbstractFlushModeCommitDao<User> implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}

	@Override
	public List<User> findByUsername(int start, int max, String name) {
		return this.findByProperties("User.findByUsername", start, max, p("name",name), p("username",name));
	}

	@Override
	public int countByUsername(String name) {
		return this.countByProperties("User.countByUsername", p("name",name), p("username",name));
	}

	@Override
	public User loginUser(String username, String password) {
		return this.findByUniqueProperties("User.loginUser", p("username",username), p("password",password));
	}

	@Override
	public List<User> findByUsernameNotSelf(String username, Long id) {
		if(id>0){
			return this.findByProperties("User.findByUsernameNotSelf",p("id",id), p("username",username));
		}else{
			return this.findByProperties("User.findByUsernameV", p("username",username));
		}
	}
}
