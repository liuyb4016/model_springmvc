package cn.liuyb.app.sync.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import cn.liuyb.app.common.utils.MD5;
import cn.liuyb.app.common.utils.Slf4jLogUtils;
import cn.liuyb.app.common.utils.SystemPathUtil;
import cn.liuyb.app.domain.App;
import cn.liuyb.app.domain.User;
import cn.liuyb.app.service.AppService;
import cn.liuyb.app.service.UserService;
import cn.liuyb.app.service.impl.CurrentUser;
import cn.liuyb.app.sync.json.CmdConstants;
import cn.liuyb.app.sync.json.Request;
import cn.liuyb.app.sync.json.Response;
import cn.liuyb.app.sync.json.ResponseHelper;
import cn.liuyb.app.sync.json.data.AppUploadReq;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
@Component
public class AppUploadHandler implements MultipartCmdHandler<Request> {
	private static Logger logger = Slf4jLogUtils.getLogger(AppUploadHandler.class);
	@Autowired
	UserService userService;
	@Autowired
	private ServletContext context;
	@Autowired
	private AppService appService;
	
	private XStream xStream = new XStream(new DomDriver());
	@Override
	public Response handle(Request request) {
		return null;
	}

	@Override
	public String getCmd() {
		return CmdConstants.Cmds.APP_UPLOAD;
	}

	@Override
	public Response handle(Request request, Map<String, MultipartFile> files) {
		logger.debug("app upload file: into  handle");
		Long userId = CurrentUser.getUserId();
		if(userId==null){
	        return ResponseHelper.createInvalidTokenResponse(request);
		}
		User user = userService.findById(userId);
		if(user==null){
	        return ResponseHelper.createInvalidTokenResponse(request);
		}
		
		AppUploadReq req = (AppUploadReq) request.getData();
		if(req==null){
	        return ResponseHelper.createInvalidCmdDataResponse(request);
		}
		if(req.getFileMD5()==null||"".equals(req.getFileMD5().trim())
				||req.getFileName()==null||"".equals(req.getFileName().trim())
				|req.getFileSize()<=0
				||req.getPackageName()==null||"".equals(req.getPackageName().trim())){
			 return ResponseHelper.createInvalidCmdDataResponse(request);
		}
		if (user != null) {
			//存放临时文件
			String folderPath = SystemPathUtil.getTempPath(context);
			File folder = new File(folderPath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			File file = new File(folder, java.util.UUID.randomUUID().toString());
			try{
				//将文件存放到指定目录
				saveMultipart(files, file);
			}catch(Exception e){
				try{
					file.delete();
				}catch(Exception e1){
				}
				logger.error("文件存放到指定目录错误:" + file.getPath() + ";req=" + xStream.toXML(req));
				return ResponseHelper.createBusinessErrorResponse(request, "文件存放到指定目录错误");
			}
			
			try {
				InputStream in = new FileInputStream(file);
				String md5 = MD5.getMD5(in);
				if(!req.getFileMD5().equals(md5)){
					return ResponseHelper.createBusinessErrorResponse(request, "用户上传App文件的Md5和请求数据中的md5不一致。");
				}
			} catch (FileNotFoundException e) {
			}
			
			App app = appService.uploadApp(file);
			if(app==null){
				try{
					file.delete();
				}catch(Exception e1){
				}
				return ResponseHelper.createBusinessErrorResponse(request, "文件上传失败。请检测后再传");
			}
			logger.debug("2.upload app success message:packageName:"+app.getPackageName()+"--versionCode:"+app.getVersionCode()+"---fileMd5"+app.getFileMd5()+"---fileName"+app.getFileName());
			SystemPathUtil.reProduceAppIcon(app, context);
			appService.update(app);
			//删除上传的临时文件
			file.delete();
		}
		return ResponseHelper.createSuccessResponse(request);
	}
	
	private void saveMultipart(Map<String, MultipartFile> files, File file) throws IllegalStateException, IOException {
		MultipartFile multipart = files.get("appFile");
		multipart.transferTo(file);
	}
}
