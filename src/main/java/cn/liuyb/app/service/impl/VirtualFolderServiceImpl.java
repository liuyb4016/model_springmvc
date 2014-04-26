package cn.liuyb.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.liuyb.app.dao.VirtualFolderDao;
import cn.liuyb.app.domain.VirtualFolder;
import cn.liuyb.app.service.VirtualFolderService;

@Service
public class VirtualFolderServiceImpl implements VirtualFolderService {

	@Autowired
	private VirtualFolderDao virtualFolderDao;

	@Override
	public Integer countByUserIdAndFatherId(Long userId, Long fatherId) {
		return virtualFolderDao.countByUserIdAndFatherId(userId, fatherId);
	}

	@Override
	public List<VirtualFolder> findByUserIdAndFatherId(Long userId,
			Long fatherId, Integer start, Integer step) {
		return virtualFolderDao.findByUserIdAndFatherId(userId, fatherId, start, step);
	}

	@Override
	public Integer countByFatherIdAndFolderName(Long userId, Long fatherId, String folderName) {
		if(StringUtils.isNotEmpty(folderName)){
			return virtualFolderDao.countByFatherIdAndFolderName(userId, fatherId, folderName);
		}else{
			return virtualFolderDao.countByUserIdAndFatherId(userId, fatherId);
		}
	}

	@Transactional
	@Override
	public Boolean addOrUpdate(VirtualFolder virtualFolder) {
		if(virtualFolder==null){
			return false;
		}
		if(virtualFolder.getId()!=null){
			virtualFolder.setUpdateTime(new Date());
			virtualFolderDao.update(virtualFolder);
			return true;
		}else{
			virtualFolder.setCreateTime(new Date());
			virtualFolder.setUpdateTime(new Date());
			virtualFolder.setLastUploadTime(new Date());
			virtualFolderDao.create(virtualFolder);
			return true;
		}
	}

	@Transactional
	@Override
	public Boolean delete(VirtualFolder virtualFolder) {
		if(virtualFolder==null){
			return false;
		}
		virtualFolderDao.delete(virtualFolder);
		return true;
	}
	
	@Override
	public VirtualFolder findById(Long id) {
		return virtualFolderDao.find(id);
	}
	
	@Override
	public List<VirtualFolder> findByFatherIdAndFolderName(Long userId, Long fatherId,String folderName, Integer start, Integer step){
		if(StringUtils.isNotEmpty(folderName)){
			return virtualFolderDao.findByFatherIdAndFolderName(userId, fatherId, folderName, start, step);
		}else{
			return virtualFolderDao.findByUserIdAndFatherId(userId, fatherId, start, step);
		}
	}
	
	
	@Override
	public boolean checkFolderNameIsExist(Long userId, Long fatherId,String folderName) {
		Integer count=virtualFolderDao.countByUFAndFolderName(userId, fatherId, folderName);
		if(count!=null&&count>0){
			return true;
		}
		return false;
	}
	
	@Override
	public List<Long> findTreeFolderId(Long folderId){
		List<Long> folderIdList=new ArrayList<Long>();
		this.getChildrenFolderId(folderIdList, folderId);
		return folderIdList;
	}
	
	
	private void  getChildrenFolderId(List<Long> folderIdList,Long folderId){
		folderIdList.add(folderId);
		List<VirtualFolder> childrenFolderList=virtualFolderDao.findByFatherId(folderId);//获取子节点树
		if(childrenFolderList!=null&&childrenFolderList.size()>0){
			for(VirtualFolder virtualFolder:childrenFolderList){
				getChildrenFolderId(folderIdList,virtualFolder.getId());
			}
		}
	}
	
	@Transactional
	@Override
	public void delByIds(List<Long> ids){
		virtualFolderDao.delByIds(ids);
	}
	
	@Override
	public List<Object> findUserFolderList(Long userId,String rootFolderName){
		return virtualFolderDao.findUserFolderList(userId, rootFolderName);
	}
}
