package cn.liuyb.app.sync.handler;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import cn.liuyb.app.common.utils.FileUtil;
import cn.liuyb.app.common.utils.MD5;
import cn.liuyb.app.common.utils.Slf4jLogUtils;
import cn.liuyb.app.common.utils.SystemPathUtil;
import cn.liuyb.app.domain.MobileFile;
import cn.liuyb.app.domain.User;
import cn.liuyb.app.domain.VirtualFolder;
import cn.liuyb.app.service.MobileFileService;
import cn.liuyb.app.service.UserService;
import cn.liuyb.app.service.VirtualFolderService;
import cn.liuyb.app.service.impl.CurrentUser;
import cn.liuyb.app.sync.json.CmdConstants;
import cn.liuyb.app.sync.json.Request;
import cn.liuyb.app.sync.json.Response;
import cn.liuyb.app.sync.json.ResponseHelper;
import cn.liuyb.app.sync.json.data.FileInfo;

@Component
public class FileUploadHandler implements MultipartCmdHandler<Request> {

	private static Logger logger = Slf4jLogUtils.getLogger(FileUploadHandler.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private MobileFileService mobileFileService;
	@Autowired
	private VirtualFolderService virtualFolderService;
	
	
	@Override
	public String getCmd() {
		return CmdConstants.Cmds.FILE_UPLOAD;
	}
	
	
	@Override
	public Response handle(Request request, Map<String, MultipartFile> files) {
		logger.debug("upload file: into  handle");
		Long userId = CurrentUser.getUserId();
		if(userId==null){
	        return ResponseHelper.createInvalidTokenResponse(request);
		}
		User user = userService.findById(userId);
		if(user==null){
	        return ResponseHelper.createInvalidTokenResponse(request);
		}
		
		FileInfo fileInfo=(FileInfo) request.getData();
		if(fileInfo==null){
			return ResponseHelper.createBusinessErrorResponse(request, "请求参数非法!");
		}
		Long folderId=fileInfo.getFolderId();
		String md5=fileInfo.getMd5();
		String fileName=fileInfo.getFileName();
		if(folderId==null||folderId.intValue()<0||StringUtils.isEmpty(md5)||StringUtils.isEmpty(fileName)){
			return ResponseHelper.createBusinessErrorResponse(request, "请求参数非法,请确认所有参数是否填写完整!");
		}
		
		String fileSuffix=FileUtil.getFileSuffix(fileName);//文件的后缀
		if(!FileUtil.checkFileSuffixIsLegal(fileSuffix)){
			return ResponseHelper.createBusinessErrorResponse(request, fileName+"文件非法!");
		}
		
		//判断文件名是否存在
		String nickName=FileUtil.getFileName(fileName);
		if(mobileFileService.checkNameIsExist(userId, folderId, nickName,fileSuffix)){
			return ResponseHelper.createBusinessErrorResponse(request, fileName+"文件名重复,请修改后再上传!");
		}
		
		try {
			
			String folderName="根目录";
			if(folderId.intValue()>0){
				VirtualFolder virtualFolder=virtualFolderService.findById(folderId);
				if(virtualFolder!=null){
					folderName=virtualFolder.getFolderName();
				}else{
					return ResponseHelper.createBusinessErrorResponse(request,"文件夹["+folderId+"]不存在!");
				}
			}
			
			MobileFile newMobileFile=new MobileFile();
			newMobileFile.setUserId(userId);
			newMobileFile.setFolderId(folderId);
			newMobileFile.setNickName( nickName);
			
			
			//假如md5不存在，判断用户是否上传了文件
			MobileFile mobileFile=mobileFileService.getMobileFileByMd5(md5);
			if(mobileFile==null){
				MultipartFile multipart =null;
				for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
					multipart =entry.getValue();
				}
				if(multipart==null||multipart.isEmpty()){
					return ResponseHelper.createBusinessErrorResponse(request, "文件不存在,请上传相应的文件!");
				}else{//上传文件到服务器,新文件的md5和判断是数据库是否已经存在该文件
					md5=MD5.getMD5(multipart.getInputStream());
					mobileFile=mobileFileService.getMobileFileByMd5(md5);
					
					if(mobileFile==null){
						String fileSyPath=SystemPathUtil.getFileSystemPath(FileUtil.getFolderName(fileSuffix));//根据文件的后缀名，确定文件保存的目录
						File folder=new File(fileSyPath);
						if (!folder.exists()) {
							folder.mkdirs();
						}
						multipart.transferTo(new File(fileSyPath+md5+"."+fileSuffix));
						newMobileFile.setFileSize(multipart.getSize());
						newMobileFile.setMd5(md5);
						newMobileFile.setMimeType(new MimetypesFileTypeMap().getContentType(fileName));//文件类型
						newMobileFile.setSuffix(fileSuffix);
						newMobileFile.setUploadTime(new Date());
						mobileFileService.addOrUpdate(newMobileFile);
					} 
				}
			} 
			
			if(mobileFile!=null){
//				folderId,md5,userid
				if(mobileFileService.checkUFMIsExist(userId, folderId, md5)){//使用服务器的文件
					logger.debug("用户{}已经将{}文件上传到{}目录下",new Object[]{userId,md5,folderName});
					return ResponseHelper.createBusinessErrorResponse(request, "文件已经存在该目录下!");
				}else{
					newMobileFile.setFileSize(mobileFile.getFileSize());
					newMobileFile.setMd5(mobileFile.getMd5());
					newMobileFile.setMimeType(mobileFile.getMimeType());
					newMobileFile.setSuffix(mobileFile.getSuffix());
					newMobileFile.setUploadTime(new Date());
					mobileFileService.addOrUpdate(newMobileFile);
				}
			}
			
			FileInfo reFileInfo=new FileInfo();
			reFileInfo.setFolderName(folderName);
			reFileInfo.setDownloadUrl(SystemPathUtil.getFileDownUrl(userId, newMobileFile.getId()));
			Response response=ResponseHelper.createSuccessResponse(request);
			response.setData(reFileInfo);
			return response;
			
		} catch ( Exception e) {
			logger.error("在{}用户上传文件时，出现异常："+e.toString(),new Object[]{userId});
			return ResponseHelper.createBusinessErrorResponse(request, "系统异常!");
		}
		
		
	}
	
	
	@Override
	public Response handle(Request request) {
		return null;
	}

	
}
