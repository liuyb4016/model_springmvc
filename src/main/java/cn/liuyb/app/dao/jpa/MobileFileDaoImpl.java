package cn.liuyb.app.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import cn.liuyb.app.common.dao.AbstractJpaDao;
import cn.liuyb.app.dao.MobileFileDao;
import cn.liuyb.app.domain.MobileFile;

@Repository
public class MobileFileDaoImpl extends AbstractJpaDao<MobileFile> implements MobileFileDao {

	public MobileFileDaoImpl() {
		super(MobileFile.class);
	}

	@Override
	public List<MobileFile> findMobileFileByUserIdAndFolderIdDESC(Integer start, Integer step,Long userId, Long folderId) {
		return this.findByProperties("MobileFile.findMobileFileByUserIdAndFolderIdDESC", start, step, p("userId", userId),p("folderId",folderId));
	}

	@Override
	public Integer countMobileFileByUserIdAndFolderId(Long userId, Long folderId) {
		return this.countByProperties("MobileFile.countMobileFileByUserIdAndFolderId", p("userId", userId),p("folderId",folderId));
	}

	@Override
	public List<MobileFile> findMobileFileByUserIdAndVirtualFolderFileName(Long userId, Long folderId, String nickName) {
		return this.findByProperties("MobileFile.findMobileFileByUserIdAndVirtualFolderFileName", p("userId", userId),p("folderId",folderId),p("nickName","%"+nickName+"%"));
	}
	
	@Override
	public List<MobileFile> findMobileFileByUserIdAndQueryString(Integer start, Integer step,Long userId, String nickName) {
		return this.findByProperties("MobileFile.findMobileFileByUserIdAndQueryString", start,  step, p("userId", userId),p("nickName","%"+nickName+"%"));
	}

	@Override
	public Integer countMobileFileByUserIdAndQueryString(Long userId, String nickName) {
		return this.countByProperties("MobileFile.countMobileFileByUserIdAndQueryString", p("userId", userId),p("nickName","%"+nickName+"%"));
	}
	
	@Override
	public Long getAllFileSizeByUserId(Long userId) {
		Query query = getEntityManager().createNamedQuery("MobileFile.getAllFileSizeByUserId");
		query.setParameter("userId", userId);
		Long result = (Long)query.getSingleResult();
		return result!=null?result:0L;
	}

	
	@Override
	public List<MobileFile>  findMobileFileByUserIdAndVirtualFolderFileName(Integer start, Integer step,Long userId,Long folderId, String nickName){
		return this.findByProperties("MobileFile.findMobileFileByUserIdAndVirtualFolderFileName",start,step, p("userId", userId),p("folderId",folderId),p("nickName","%"+nickName+"%"));
	}
	@Override
	public Integer  countByUserIdAndVirtualFolderFileName( Long userId,Long folderId, String nickName){
		return this.countByProperties("MobileFile.countByUserIdAndVirtualFolderFileName", p("userId", userId),p("folderId",folderId),p("nickName","%"+nickName+"%"));
	}
	@Override
	public Integer  countUVirtualName( Long userId,Long folderId, String nickName){
		return this.countByProperties("MobileFile.countUVirtualName", p("userId", userId),p("folderId",folderId),p("nickName",nickName));
	}
	
	@Override
	public MobileFile getMobileFileByMd5(String md5){
		List<MobileFile> datas=findByProperties("MobileFile.getMobileFileByMd5",  p("md5", md5) );
		if(datas!=null&&datas.size()>0){
			return datas.get(0);
		}
		return null;
	}
	
	@Override
	public void deleteByFolderId(List<Long> folderIds){
		this.executeUpdate("MobileFile.delByFolderIds", p("folderIds", folderIds));
	}
	@Override
	public int countSizeByFolderId(List<Long> folderIds){
		return this.countByProperties("MobileFile.countSizeByFolderId", p("folderIds", folderIds));
	}
	@Override
	public void delByIds(List<Long> ids){
		this.executeUpdate("MobileFile.delByIds", p("ids", ids));
	}
	
	
	@Override
	public Integer  countUVirtualName( Long userId,Long folderId, String nickName,String suffix){
		return this.countByProperties("MobileFile.countUVirtualNameSuff", p("userId", userId),p("folderId",folderId),p("nickName",nickName),p("suffix",suffix));
	}
	
	@Override
	public Integer   countByUFMd5( Long userId,Long folderId, String md5){
		return this.countByProperties("MobileFile.countByUFMd5", p("userId", userId),p("folderId",folderId),p("md5",md5) );
	}
}
