package cn.liuyb.app.sync.handler;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.liuyb.app.common.utils.Slf4jLogUtils;
import cn.liuyb.app.domain.User;
import cn.liuyb.app.service.UserService;
import cn.liuyb.app.service.VirtualFolderService;
import cn.liuyb.app.service.impl.CurrentUser;
import cn.liuyb.app.sync.json.CmdConstants;
import cn.liuyb.app.sync.json.Request;
import cn.liuyb.app.sync.json.Response;
import cn.liuyb.app.sync.json.ResponseHelper;
import cn.liuyb.app.sync.json.data.Array;
import cn.liuyb.app.sync.json.data.FolderInfo;
@Component
public class QueryFolderHandler extends AbstractHttpServletRequestAwareHandler implements CmdHandler {

	private static Logger logger = Slf4jLogUtils.getLogger(QueryFolderHandler.class);
	@Autowired
	private UserService userService;
	@Autowired
	private VirtualFolderService virtualFolderService;
	
	
	@Override
	public String getCmd() {
		return CmdConstants.Cmds.QUERY_FOLDER;
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
		
		Response response=null;
		try {
			response = ResponseHelper.createSuccessResponse(request);
			List<Object> datas=virtualFolderService.findUserFolderList(userId, "根目录");
			if(datas!=null&&datas.size()>0){
				Array array=new Array();
				List<FolderInfo> folderList=new ArrayList<FolderInfo>();
				FolderInfo folder;
				for(Object obj:datas){
					Object[] objs=(Object[]) obj;
					folder=new FolderInfo();
					folder.setFolderId( Long.valueOf(objs[0].toString()));
					folder.setFolderName(objs[1].toString());
					folder.setpFolderId(Long.valueOf(objs[2].toString()));
					folder.setpFolderName(objs[3].toString());
					folderList.add(folder);
				}
				array.setArray(folderList);
				response.setData(array);
			}
		} catch (Exception e) {
			 logger.error("在查询{}用户的文件夹列表时，出现异常："+e.toString(),userId);
			 response = ResponseHelper.createBusinessErrorResponse(request,"系统异常!");
		}
		
		return response;
	}

}
