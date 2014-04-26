package cn.liuyb.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.liuyb.app.dao.UserDao;
import cn.liuyb.app.domain.User;
import cn.liuyb.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public List<User> findByUsername(int start, int max, String name) {
		return userDao.findByUsername(start, max, name);
	}

	@Override
	public int countByUsername(String name) {
		return userDao.countByUsername(name);
	}

	@Override
	public User findById(Long id) {
		return userDao.find(id);
	}

	@Override
	public User loginUser(String username, String password) {
		return userDao.loginUser(username, password);
	}
	
	@Transactional
	@Override
	public void add(User user) {
		userDao.create(user);
	}
	
	@Transactional
	@Override
	public void delete(User user) {
		userDao.delete(user);
	}
	
	@Transactional
	@Override
	public void update(User user) {
		userDao.update(user);
	}
	
	@Transactional
	@Override
	public void updateUserPassWord(Long userId, String pass)
			throws IllegalAccessException {
		User user = userDao.find(userId);
		user.setPassword(pass);
		userDao.update(user);
	}
	
	@Override
	public List<User> findByUsernameNotSelf(String username, Long id) {
		return userDao.findByUsernameNotSelf(username, id);
	}
}
