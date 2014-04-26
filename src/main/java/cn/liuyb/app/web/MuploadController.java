package cn.liuyb.app.web;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import cn.liuyb.app.common.utils.FileUtil;
import cn.liuyb.app.common.utils.MD5;
import cn.liuyb.app.common.utils.MyFileUtils;
import cn.liuyb.app.common.utils.Slf4jLogUtils;
import cn.liuyb.app.common.utils.SystemPathUtil;
import cn.liuyb.app.domain.MobileFile;
import cn.liuyb.app.service.MobileFileService;
@Controller
@RequestMapping(value = "/mupload")
public class MuploadController implements NoNeedLoginController {
	private static final Logger logger = Slf4jLogUtils.getLogger(MuploadController.class);
	
	@Autowired
	private MobileFileService mobileFileService;
		
		
	@RequestMapping( method = RequestMethod.POST)
	public void   mupload(Long folderId,Long userId,MultipartFile Filedata,DefaultMultipartHttpServletRequest  request, HttpServletResponse response) {
		/**
		 * 1.判断文件是否为空
		 * 2.求md5值，判断md5是否存在
		 * 3.不存在，则上传文件
		 */
		if(Filedata!=null&&Filedata.getSize()>0){
			try {
				String fileName=Filedata.getOriginalFilename();//文件名
				String nickName= FileUtil.getFileName(fileName);
				//1.当前目录下的文件存在同名文件,上传失败
				if(mobileFileService.checkNameIsExist(userId, folderId, nickName)){
					writeMsg(response,1,fileName+"文件的文件名已经存在,请修改名称再上传!");
					return  ;
				}
				
				
				
				String md5=MD5.getMD5(Filedata.getInputStream());
				if(StringUtils.isNotEmpty(md5)){
					//2.根据md5判断文件是否已经存在
					String suffix=FileUtil.getFileSuffix(fileName);
					
					MobileFile mobileFile=mobileFileService.getMobileFileByMd5(md5);
					if(mobileFile==null){//文件不存在,则将文件上传到相应的目录
						String fileSyPath=SystemPathUtil.getFileSystemPath(FileUtil.getFolderName(suffix));
						SystemPathUtil.doTransmitResources(Filedata,md5,fileSyPath);//文件名为【md5值.后缀名】
					}
					
					//文件类型
					MimetypesFileTypeMap mime = new MimetypesFileTypeMap();
					String mimeType = mime.getContentType(fileName);
					MobileFile newMobileFile=new MobileFile();
					newMobileFile.setFileSize(Filedata.getSize());
					newMobileFile.setFolderId(folderId);
					newMobileFile.setMd5(md5);
					newMobileFile.setMimeType(mimeType);
					newMobileFile.setNickName( nickName);
					newMobileFile.setSuffix(suffix);
					newMobileFile.setUserId(userId);
					mobileFileService.addOrUpdate(newMobileFile);
				}
			} catch ( Exception e) {
				logger.info("在上传文件时，出现异常："+e.toString());
				writeMsg(response,-1,"上传文件出现异常!");
			}
		}
		writeMsg(response,0,"文件上传成功!");
	}
	
	public void writeMsg( HttpServletResponse response,int code,String msg){
		try {
			String jsonObj="{'succode':"+code+",'msg':'"+msg+"'}";
			response.getWriter().write(jsonObj);
		} catch (IOException e) {
			logger.error("在上传文件时，将结果输出时，出现异常："+e.toString());
		}
	}
	
	
	
	
	
	@RequestMapping(value="/download/{id}", method = RequestMethod.GET)
	public void downloadFile(@PathVariable("id") Long id, HttpServletRequest request,  HttpServletResponse response, OutputStream out) throws IOException{
		MobileFile mobileFile=mobileFileService.findById(id);
		if(mobileFile==null) return ;
		String fileName=mobileFile.getMd5()+"."+mobileFile.getSuffix();
		String filePath=SystemPathUtil.getFileSystemPath(FileUtil.getFolderName(mobileFile.getSuffix()))+fileName;
		logger.debug(filePath);
		File downloadFile=new File(filePath);
		String name=mobileFile.getNickName()+"."+mobileFile.getSuffix();
		MyFileUtils.breakPointDownload(downloadFile, request, response, name);
//		response.setContentType( mobileFile.getMimeType());    
//		response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(mobileFile.getNickName()+"."+mobileFile.getSuffix(), "UTF-8"));
//		response.setHeader("Accept-Ranges", "bytes");
//		response.setHeader("Content-Type", "application/octet-stream");	
//		response.setHeader("Content-Length", ""+mobileFile.getFileSize());	
//		BeanUtils.copyInputFileToOutput(filePath, out);
	}
	
	
	
	
	
	
	@RequestMapping(value="/dl/{userId}/{id}", method = RequestMethod.GET)
	public void downloadFile(@PathVariable("userId") Long userId,@PathVariable("id") Long id, HttpServletRequest request,  HttpServletResponse response, OutputStream out) throws IOException{
		MobileFile mobileFile=mobileFileService.findById(id);
		if(mobileFile==null) {
			writeMsg(response,-1,"文件不存在!");
			return ;
		}
		if(mobileFile.getUserId().intValue()!=userId.intValue()){
			writeMsg(response,-1,"文件非用户文件!");
			return ;
		}
		String fileName=mobileFile.getMd5()+"."+mobileFile.getSuffix();
		String filePath=SystemPathUtil.getFileSystemPath(FileUtil.getFolderName(mobileFile.getSuffix()))+fileName;
		logger.debug(filePath);
		File downloadFile=new File(filePath);
		String name=mobileFile.getNickName()+"."+mobileFile.getSuffix();
		MyFileUtils.breakPointDownload(downloadFile, request, response, name);
	}
	
	
	
	
	
}
