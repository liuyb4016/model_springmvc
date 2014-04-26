package cn.liuyb.app.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.liuyb.app.dao.MobileFileDao;
import cn.liuyb.app.domain.MobileFile;
import cn.liuyb.app.service.MobileFileService;

@Service
public class MobileFileServiceImpl implements MobileFileService {

	@Autowired
	private MobileFileDao mobileFileDao;

	@Override
	public List<MobileFile> findMobileFileByUserIdAndFolderIdDESC( Integer start, Integer step, Long userId, Long folderId ) {
		return mobileFileDao.findMobileFileByUserIdAndFolderIdDESC(start, step, userId, folderId);
	}

	@Override
	public Integer countMobileFileByUserIdAndFolderId(Long userId, Long folderId) {
		return mobileFileDao.countMobileFileByUserIdAndFolderId(userId, folderId);
	}

	@Override
	public List<MobileFile> findMobileFileByUserIdAndVirtualFolderFileName(
			Long userId, Long folderId, String fileName) {
		return mobileFileDao.findMobileFileByUserIdAndVirtualFolderFileName(userId, folderId, fileName);
	}

	@Override
	public Long getAllFileSizeByUserId(Long userId) {
		return mobileFileDao.getAllFileSizeByUserId(userId);
	}

	@Override
	public List<MobileFile> findMobileFileByUserIdAndQueryString(Integer start,
			Integer step, Long userId, String fileName) {
		return mobileFileDao.findMobileFileByUserIdAndQueryString(start, step, userId, fileName);
	}

	@Override
	public Integer countMobileFileByUserIdAndQueryString(Long userId,
			String fileName) {
		return mobileFileDao.countMobileFileByUserIdAndQueryString(userId, fileName);
	}

	@Transactional
	@Override
	public Boolean addOrUpdate(MobileFile mobileFile) {
		if(mobileFile==null){
			return false;
		}
		if(mobileFile.getId()!=null){
			mobileFile.setUpdateTime(new Date());
			mobileFileDao.update(mobileFile);
			return true;
		}else{
			mobileFile.setCreateTime(new Date());
			mobileFile.setUpdateTime(new Date());
			mobileFile.setUpdateTime(new Date());
			mobileFileDao.create(mobileFile);
			return true;
		}
	}
	
	@Transactional
	@Override
	public Boolean delete(MobileFile mobileFile) {
		if(mobileFile==null){
			return false;
		}
		mobileFileDao.delete(mobileFile);
		return true;
	}
	
	@Override
	public List<MobileFile>  findMobileFileByUserIdAndVirtualFolderFileName(Integer start, Integer step,Long userId,Long folderId, String nickName){
		if(StringUtils.isNotEmpty(nickName)){
			return mobileFileDao.findMobileFileByUserIdAndVirtualFolderFileName(start, step, userId, folderId, nickName);
		}else{
			return mobileFileDao.findMobileFileByUserIdAndFolderIdDESC(start, step, userId, folderId);
		}
	}
	
	@Override
	public Integer  countByUserIdAndVirtualFolderFileName( Long userId,Long folderId, String nickName){
		if(StringUtils.isNotEmpty(nickName)){
			return mobileFileDao.countByUserIdAndVirtualFolderFileName( userId, folderId, nickName);
		}else{
			return mobileFileDao.countMobileFileByUserIdAndFolderId(userId, folderId);
		}
	}
	
	
	@Override
	public MobileFile findById(Long id){
		return mobileFileDao.find(id);
	}
	
	@Override
	public boolean   checkNameIsExist( Long userId,Long folderId, String nickName){
		Integer count=mobileFileDao.countUVirtualName(userId, folderId, nickName);
		if(count!=null&&count>0){
			return true;
		}
		return false;
	}
	
	@Override
	public MobileFile getMobileFileByMd5(String md5){
		return mobileFileDao.getMobileFileByMd5(md5);
	}
	
	@Transactional
	@Override
	public void deleteByFolderId(List<Long> folderIds){
		mobileFileDao.deleteByFolderId(folderIds);
	}
	
	@Override
	public int countSizeByFolderId(List<Long> folderIds){
		return mobileFileDao.countSizeByFolderId(folderIds);
	}
	
	@Transactional
	@Override
	public void delByIds(List<Long> ids){
		mobileFileDao.delByIds(ids);
	}
	
	@Override
	public boolean   checkNameIsExist( Long userId,Long folderId, String nickName,String suffix){
		Integer count=mobileFileDao.countUVirtualName(userId, folderId, nickName,suffix);
		if(count!=null&&count>0){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean   checkUFMIsExist( Long userId,Long folderId, String md5){
		Integer count=mobileFileDao.countByUFMd5(userId, folderId, md5);
		if(count!=null&&count>0){
			return true;
		}
		return false;
	}
}
