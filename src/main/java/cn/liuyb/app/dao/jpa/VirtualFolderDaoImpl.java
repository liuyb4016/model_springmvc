package cn.liuyb.app.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.liuyb.app.common.dao.AbstractJpaDao;
import cn.liuyb.app.dao.VirtualFolderDao;
import cn.liuyb.app.domain.VirtualFolder;

@Repository
public class VirtualFolderDaoImpl extends AbstractJpaDao<VirtualFolder> implements
		VirtualFolderDao {

	public VirtualFolderDaoImpl() {
		super(VirtualFolder.class);
	}
	
	@Override
	public List<VirtualFolder> findByUserIdAndFatherId(Long userId,Long fatherId, Integer start, Integer step) {
		return this.findByProperties("VirtualFolder.findByUserIdAndFatherId", start, step, p("userId", userId),p("fatherId",fatherId));
	}
	
	@Override
	public Integer countByUserIdAndFatherId(Long userId, Long fatherId) {
		return this.countByProperties("VirtualFolder.countByUserIdAndFatherId", p("userId", userId),p("fatherId",fatherId));
	}

	@Override
	public Integer countByFatherIdAndFolderName(Long userId, Long fatherId,String folderName) {
		return this.countByProperties("VirtualFolder.countByFatherIdAndFolderName", p("userId", userId),p("fatherId",fatherId),p("folderName","%"+folderName+"%"));
	}
	
	@Override
	public List<VirtualFolder> findByFatherIdAndFolderName(Long userId, Long fatherId,String folderName, Integer start, Integer step){
		return this.findByProperties("VirtualFolder.findByFatherIdAndFolderName", start, step, p("userId", userId),p("fatherId",fatherId), p("folderName","%"+folderName+"%"));
	}
	
	
	/**
	 * 检查文件夹是否存在
	 */
	@Override
	public Integer countByUFAndFolderName(Long userId, Long fatherId,String folderName) {
		return this.countByProperties("VirtualFolder.countByUFAndFolderName", p("userId", userId),p("fatherId",fatherId),p("folderName",folderName ));
	}
	
	@Override
	public List<VirtualFolder> findByFatherId(Long fatherId){
		return this.findByProperties("VirtualFolder.findByFatherId", p("fatherId",fatherId));
	}
	
	@Override
	public void delByIds(List<Long> ids){
		executeUpdate("VirtualFolder.delByIds", p("ids",ids));
	}
	
	
	/**
	 * @param userId
	 * @param rootFolderName
	 * @return
	 */
	@Override
	public List<Object> findUserFolderList(Long userId,String rootFolderName){
		String sqlStr="select f.id,f.folder_name ,f.father_id,( case when f.father_id>0 then (select   v.folder_name   from virtual_folder v where v.id=f.father_id)" +
				" else ? end ) pfoldername from virtual_folder f where user_id=? ";
		return this.findSqlObjectsByProperties(sqlStr, new Object[]{rootFolderName,userId});
	}
	
	
}
