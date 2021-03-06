package cn.liuyb.app.dao;

import java.util.List;

import cn.liuyb.app.common.dao.EntityDao;
import cn.liuyb.app.domain.MobileFile;

public interface MobileFileDao extends EntityDao<MobileFile> {
	
	/**
	 * 
	     * 此方法描述的是：//用户文件夹下的文件   
	     * @author: liuyb 
	     * @version: 2014-1-20 下午1:32:20
	 */
	public List<MobileFile> findMobileFileByUserIdAndFolderIdDESC(Integer start, Integer step,Long userId, Long folderId );
	/**
	 * 
	     * 此方法描述的是：//用户文件夹下的文件数据 个数
	     * @author: liuyb 
	     * @version: 2014-1-20 下午1:32:38
	 */
	public Integer countMobileFileByUserIdAndFolderId(Long userId, Long folderId);
	/**
	 * 
	     * 此方法描述的是： //用户文件 按名字查询    同一文件夹下不能重复名称 
	     * @author: liuyb 
	     * @version: 2014-1-20 下午1:34:44
	 */
	public List<MobileFile>  findMobileFileByUserIdAndVirtualFolderFileName(Long userId,Long folderId, String fileName);
	/**
	 * 
	     * 此方法描述的是： //用户总文件大小
	     * @author: liuyb 
	     * @version: 2014-1-20 下午1:32:38
	 */
	public Long getAllFileSizeByUserId(Long userId);
	/**
	 * 
	     * 此方法描述的是： //按用户 文件名搜索
	     * @author: liuyb 
	     * @version: 2014-1-20 下午1:32:38
	 */
	public List<MobileFile> findMobileFileByUserIdAndQueryString(Integer start,Integer step, Long userId, String fileName);
	/**
	 * 
	     * 此方法描述的是： //按用户 文件名搜索  数量
	     * @author: liuyb 
	     * @version: 2014-1-20 下午1:32:38
	 */
	public Integer countMobileFileByUserIdAndQueryString(Long userId, String fileName);
	
	
	
	public List<MobileFile>  findMobileFileByUserIdAndVirtualFolderFileName(Integer start, Integer step,Long userId,Long folderId, String nickName);
	public Integer  countByUserIdAndVirtualFolderFileName( Long userId,Long folderId, String nickName);
	public Integer  countUVirtualName( Long userId,Long folderId, String nickName);
	public MobileFile getMobileFileByMd5(String md5);
	public void deleteByFolderId(List<Long> folderIds);
	public int countSizeByFolderId(List<Long> folderIds);
	public void delByIds(List<Long> ids);
	public Integer  countUVirtualName( Long userId,Long folderId, String nickName,String suffix);
	public Integer   countByUFMd5( Long userId,Long folderId, String md5);
}
