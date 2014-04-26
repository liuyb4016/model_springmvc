package cn.liuyb.app.sync.handler;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.liuyb.app.common.utils.FileUtil;
import cn.liuyb.app.common.utils.Slf4jLogUtils;
import cn.liuyb.app.domain.MobileFile;
import cn.liuyb.app.domain.User;
import cn.liuyb.app.service.MobileFileService;
import cn.liuyb.app.service.UserService;
import cn.liuyb.app.service.impl.CurrentUser;
import cn.liuyb.app.sync.json.CmdConstants;
import cn.liuyb.app.sync.json.Request;
import cn.liuyb.app.sync.json.Response;
import cn.liuyb.app.sync.json.ResponseHelper;
import cn.liuyb.app.sync.json.data.FileInfo;
@Component
public class CheckFileHandler extends AbstractHttpServletRequestAwareHandler implements CmdHandler {

	private static Logger logger = Slf4jLogUtils.getLogger(CheckFileHandler.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private MobileFileService mobileFileService;
	
	
	@Override
	public String getCmd() {
		return CmdConstants.Cmds.CHECK_FILE;
	}

	@Override
	public Response handle(Request request) {
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
		
		Response response;
		try {
			boolean md5IsExist=false;
			boolean fileNameIsExist=false;
			MobileFile mobileFile=mobileFileService.getMobileFileByMd5(md5);
			if(mobileFile!=null){
				md5IsExist=true;
			}
			String nickName=FileUtil.getFileName(fileName);
			if(mobileFileService.checkNameIsExist(userId, folderId, nickName,fileSuffix)){
				fileNameIsExist=true;
			}
			FileInfo reFileInfo=new FileInfo();
			reFileInfo.setFileNameIsExist(fileNameIsExist);
			reFileInfo.setMd5IsExist(md5IsExist);
			response = ResponseHelper.createSuccessResponse(request);
			response.setData(reFileInfo);
		} catch (Exception e) {
			logger.error("在检查{}用户上传的{}文件的md5:{}是否存在时，出现异常："+e.toString(),new Object[]{userId,fileName,md5});
			return ResponseHelper.createBusinessErrorResponse(request, "系统异常!");
		}
		
		
		return response;
	}

}
