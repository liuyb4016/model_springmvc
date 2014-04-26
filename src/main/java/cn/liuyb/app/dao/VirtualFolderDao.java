package cn.liuyb.app.dao;

import java.util.List;

import cn.liuyb.app.common.dao.EntityDao;
import cn.liuyb.app.domain.VirtualFolder;

public interface VirtualFolderDao extends EntityDao<VirtualFolder> {
	
	/**
	 * 
	     * 此方法描述的是：//获取用户的文件夹下的文件夹 
	     * @author: liuyb 
	     * @version: 2014-1-20 下午1:42:52
	 */
	public Integer countByUserIdAndFatherId(Long userId,Long fatherId);
	/**
	 * 
	     * 此方法描述的是：//获取用户的文件夹下的文件夹个数 
	     * @author: liuyb 
	     * @version: 2014-1-20 下午1:43:01
	 */
	public List<VirtualFolder> findByUserIdAndFatherId(Long userId,Long fatherId, Integer start, Integer step);
	/**
	 * 
	     * 此方法描述的是：//查询  用户文件夹下文件夹名称 
	     * @author: liuyb 
	     * @version: 2014-1-20 下午1:43:10
	 */
	public Integer countByFatherIdAndFolderName(Long userId, Long fatherId,String folderName);
	
	public List<VirtualFolder> findByFatherIdAndFolderName(Long userId, Long fatherId,String folderName, Integer start, Integer step);
	
	public Integer countByUFAndFolderName(Long userId, Long fatherId,String folderName) ;
	public List<VirtualFolder> findByFatherId(Long fatherId);
	public void delByIds(List<Long> ids);
	
	public List<Object> findUserFolderList(Long userId,String rootFolderName);
}
