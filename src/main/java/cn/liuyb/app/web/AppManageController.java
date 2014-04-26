package cn.liuyb.app.web;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.liuyb.app.common.utils.MethodDirections;
import cn.liuyb.app.common.utils.PaginationUtils;
import cn.liuyb.app.common.utils.Slf4jLogUtils;
import cn.liuyb.app.domain.App;
import cn.liuyb.app.service.AppService;
import cn.liuyb.app.service.impl.CurrentUser;

@Controller
@RequestMapping("/appmanage")
public class AppManageController implements NeedLoginController {
	
	private static final Logger logger = Slf4jLogUtils.getLogger(AppManageController.class);
	@Autowired
	private AppService appService;
	
	@MethodDirections(value = "查询：进入应用管理列表")
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "withy/app_list";
	}

	@MethodDirections(value = "查询：获得应用管理列表")
	@RequestMapping(value = "/page/{step}/{page}", method = RequestMethod.GET)
	public @ResponseBody
	Object[] queryPageAndCount2(@PathVariable("page") int page, @PathVariable("step") int step) {
		List<App> applists = appService.findAll();
		int size = applists.size();
		int pageCount = PaginationUtils.computeTotalPage(size, step);
		return new Object[] { pageCount, applists};
	}	
	
	@MethodDirections(value = "修改：删除应用")
	@RequestMapping(value = "/deleteApp/{id}")
	public @ResponseBody
	int deleteApp(@PathVariable("id") long appId) throws IllegalAccessException {
		if (appId < 1) {
			return -1;
		}
		if (CurrentUser.getUserId() == null) {
			return -4;
		}
		App app = appService.findById(appId);
		if (app == null) {
			return -5;
		}
		try {
			appService.deleteApp(appId);
		} catch (Exception e) {
			logger.error("app {} delte fail", appId);
			return -3;
		}
		return 1;
	}
}
