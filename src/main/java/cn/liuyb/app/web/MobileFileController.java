package cn.liuyb.app.web;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.liuyb.app.common.utils.MethodDirections;
import cn.liuyb.app.common.utils.PaginationUtils;
import cn.liuyb.app.common.utils.Slf4jLogUtils;
import cn.liuyb.app.domain.MobileFile;
import cn.liuyb.app.domain.VirtualFolder;
import cn.liuyb.app.service.MobileFileService;
import cn.liuyb.app.service.VirtualFolderService;
import cn.liuyb.app.service.impl.CurrentUser;

@Controller
@RequestMapping(value = "/mobilefile")
public class MobileFileController implements NeedLoginController {

	private static final Logger logger = Slf4jLogUtils.getLogger(MobileFileController.class);
	@Autowired
	private MobileFileService mobileFileService;
	@Autowired
	private VirtualFolderService virtualFolderService;
	
	private static String TYPE_FOLDER="folder";
	private static String TYPE_FILE="file";
	
	
	@MethodDirections(value = "查询：进入文件管理列表")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model){
		Long userId = CurrentUser.getUserId();
		model.addAttribute("userId", userId);
		return "withy/mobilefile_list";
	}

	@RequestMapping(value="/query/{step}/{page}/{folder}", method = RequestMethod.GET)
    public @ResponseBody Object[] getPageAndCount(@PathVariable("page") Integer page,
    		@PathVariable("step") Integer step, @PathVariable("folder") Long folderId,String q) {
		Long userId = CurrentUser.getUserId();
        int start = PaginationUtils.computeStartPosition(page, step);
        
        List<VirtualFolder> virtualFolders=null;
        List<MobileFile> files = null;
        Integer folderCount =0;
        Integer fileCount =0;
        
        int startNew = start/2;
		int stepNew = step/2;
        
		folderCount = virtualFolderService.countByFatherIdAndFolderName(userId,folderId,q);
    	fileCount=mobileFileService.countByUserIdAndVirtualFolderFileName(userId, folderId, q);

    	//页数
    	int folderpage=folderCount/stepNew+((folderCount%stepNew==0)?0:1);//2页
    	int filepage=fileCount/stepNew+((fileCount%stepNew==0)?0:1);//3页
    	
    	if(page<=folderpage||page<=filepage){
    		if(page<=folderpage&&page<=filepage){//当前页，两者都有
    			virtualFolders = virtualFolderService.findByFatherIdAndFolderName(userId,folderId,q, startNew, stepNew);
            	files = mobileFileService.findMobileFileByUserIdAndVirtualFolderFileName(startNew, stepNew, userId, folderId, q);
            	
            	if(virtualFolders!=null&&virtualFolders.size()<stepNew){
            		 List<MobileFile> partFiles =mobileFileService.findMobileFileByUserIdAndVirtualFolderFileName(startNew+stepNew, stepNew-virtualFolders.size(), userId, folderId, q);
            		 files.addAll(partFiles);
            	}
            	
            	if(files!=null&&files.size()<stepNew){
            		 List<VirtualFolder> partVirtualFolders=virtualFolderService.findByFatherIdAndFolderName(userId,folderId,q, startNew+stepNew, stepNew-files.size());
            		 virtualFolders.addAll(partVirtualFolders);
            	}
            	
            	
            	
    		}else if(page>folderpage &&page<=filepage){//文件夹已经不存在
    			files = mobileFileService.findMobileFileByUserIdAndVirtualFolderFileName(start-folderCount%stepNew, step , userId, folderId, q);
    		}else if(page<=folderpage &&page>filepage){//文件已经不存在
    			virtualFolders = virtualFolderService.findByFatherIdAndFolderName(userId,folderId,q, start-fileCount%stepNew, step);
    		}
    	}
        
    	
    	if(virtualFolders!=null&&virtualFolders.size()>0){
    		List<Long> folderIds;
    		for(VirtualFolder virtualFolder:virtualFolders){
    			folderIds=virtualFolderService.findTreeFolderId(virtualFolder.getId());
    			if(folderIds!=null&&folderIds.size()>0){
    				int fsize=mobileFileService.countSizeByFolderId(folderIds);
    				virtualFolder.setFolderSize(fsize);
    			}
    			
    			
    		}
    	}
    	
    	
        int pageCount = PaginationUtils.computeTotalPage(fileCount+folderCount, step);
        return new Object[]{pageCount, virtualFolders, files };
	}


	
	
	@RequestMapping(value="/addfolder", method=RequestMethod.GET)
	public @ResponseBody int addfolder(String fname, Long folderId) throws IllegalAccessException{
		int resultCode=1;
		if(StringUtils.isEmpty(fname)){
			resultCode= -2;
		}else if(folderId==null){
			resultCode= -1;
		}else{
			Long userId = CurrentUser.getUserId();
			if(virtualFolderService.checkFolderNameIsExist(userId, folderId, fname)){
				resultCode= 0;
			}else{
				try {
					VirtualFolder virtualFolder = new VirtualFolder();
					virtualFolder.setFatherId(folderId);
					virtualFolder.setUserId(userId);
					virtualFolder.setFolderName(fname);
					virtualFolderService.addOrUpdate(virtualFolder);
					resultCode= 1;
				} catch (Exception e) {
					resultCode= -1;
					logger.info("在添加文件夹时，出现异常："+e.toString());
				}
			}
		}
		return resultCode;
	}
	
	
	
	@RequestMapping(value="/find/{type}/{id}", method = RequestMethod.GET)
    public @ResponseBody Object  find(@PathVariable("type") String type, @PathVariable("id") Long id) {
		if(TYPE_FOLDER.equals(type)){
			return virtualFolderService.findById(  id);
		}else if(TYPE_FILE.equals(type)){
			return mobileFileService.findById(id);
		}
		return null;
	}
	
	@RequestMapping(value="/update/{type}/{id}", method = RequestMethod.GET)
	public @ResponseBody int  update(@PathVariable("type") String type, @PathVariable("id") Long id,String fname) {
		Long userId = CurrentUser.getUserId();
		int resultCode=1;
		try {
			if(TYPE_FOLDER.equals(type)){
				VirtualFolder virtualFolder=virtualFolderService.findById(  id);
				if(virtualFolder!=null&&StringUtils.isNotEmpty(fname)
						&&!virtualFolder.getFolderName().equals(fname) ){
					if(!virtualFolderService.checkFolderNameIsExist(userId, virtualFolder.getFatherId(), fname)){
						virtualFolder.setFolderName(fname);
						virtualFolderService.addOrUpdate(virtualFolder);
					}else{
						resultCode=0;
					}
				}
			}else if(TYPE_FILE.equals(type)){
				MobileFile mobileFile=mobileFileService.findById(id);
				if(mobileFile!=null&&StringUtils.isNotEmpty(fname)
						&&!mobileFile.getNickName().equals(fname) ){
					if(!mobileFileService.checkNameIsExist(userId, mobileFile.getFolderId(), fname)){
						mobileFile.setNickName(fname);
						mobileFileService.addOrUpdate(mobileFile);
					}else{
						resultCode=0;
					}
				}
			}
		} catch (Exception e) {
			logger.info("在更新文件夹或文件时，出现异常："+e.toString());
			resultCode=-1;
		}
		return resultCode;
	}
	
	
	
	
	@RequestMapping(value="/del/{type}/{id}", method = RequestMethod.GET)
    public @ResponseBody boolean  del(@PathVariable("type") String type, @PathVariable("id") Long id) {
		boolean isSuccess=true;
		
		try {
			if(TYPE_FOLDER.equals(type)){
				List<Long> folderIds=virtualFolderService.findTreeFolderId(id);
				if(folderIds!=null&&folderIds.size()>0){
					virtualFolderService.delByIds(folderIds);
					mobileFileService.deleteByFolderId(folderIds);
				}
			}else if(TYPE_FILE.equals(type)){
				MobileFile mobileFile=mobileFileService.findById(id);
				if(mobileFile!=null){
					mobileFileService.delete(mobileFile);
				}
			}
		} catch (Exception e) {
			logger.info("在删除文件夹或文件时，出现异常："+e.toString());
			isSuccess=false;
		}
		return isSuccess;
	}
	
	
	
	@RequestMapping(value="/delGroup", method = RequestMethod.GET)
	public @ResponseBody boolean  delGroup(@RequestParam("ids[]") Long[] ids) {
		boolean isSuccess=true;
		try {
			if(ids!=null&&ids.length>0){
				mobileFileService.delByIds(Arrays.asList(ids));
			}
		} catch (Exception e) {
			logger.info("在删除文件夹或文件时，出现异常："+e.toString());
			isSuccess=false;
		}
		return isSuccess;
	}
	
}
