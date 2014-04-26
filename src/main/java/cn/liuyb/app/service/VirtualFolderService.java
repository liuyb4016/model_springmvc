package cn.liuyb.app.service;

import java.util.List;

import cn.liuyb.app.domain.VirtualFolder;

public interface VirtualFolderService {
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
	
	/**
	 * 
	     * 此方法描述的是：新增或者修改 
	     * @author: liuyb 
	     * @version: 2014-1-20 下午2:56:51
	 */
	public Boolean addOrUpdate(VirtualFolder virtualFolder);
	
	/**
	 * 
	     * 此方法描述的是： 删除
	     * @author: liuyb 
	     * @version: 2014-1-20 下午2:57:09
	 */
	public Boolean delete(VirtualFolder virtualFolder);
	public VirtualFolder findById(Long id);
	
	
	public List<VirtualFolder> findByFatherIdAndFolderName(Long userId, Long fatherId,String folderName, Integer start, Integer step);
	
	public boolean checkFolderNameIsExist(Long userId, Long fatherId,String folderName) ;
	
	/**
	 * 获取folder及其以下的书节点id集
	 * @param folderId
	 * @return
	 */
	public List<Long> findTreeFolderId(Long folderId);
	public void delByIds(List<Long> ids);
	
	
	public List<Object> findUserFolderList(Long userId,String rootFolderName);
}
