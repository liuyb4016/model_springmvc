package cn.liuyb.app.service;

import java.util.List;

import cn.liuyb.app.domain.User;

public interface UserService {

	public List<User> findByUsername(int start,int max,String name);
	public int countByUsername(String name);
	public User findById(Long id);
	public User loginUser(String userName,String password);
	public void add(User user);
	public void delete(User user);
	public void update(User user);
	public void updateUserPassWord(Long userId, String pass)
			throws IllegalAccessException;
	public List<User> findByUsernameNotSelf(String username, Long id);
}
