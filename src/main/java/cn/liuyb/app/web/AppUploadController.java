package cn.liuyb.app.web;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.liuyb.app.common.utils.MethodDirections;
import cn.liuyb.app.common.utils.SystemPathUtil;
import cn.liuyb.app.domain.App;
import cn.liuyb.app.domain.AppUploadInfo;
import cn.liuyb.app.service.AppService;

@Controller
@RequestMapping("/appupload")
public class AppUploadController implements NeedLoginController {

	@Autowired
	private AppService appService;
	@Autowired
	private ServletContext context;
	
	@MethodDirections(value = "修改：进入应用上传")
	@RequestMapping(method=RequestMethod.GET)
	public String show(@ModelAttribute(value="uploadinfo") AppUploadInfo uploadinfo) {
		return "withy/appupload";
	}
	
	@MethodDirections(value = "修改：应用上传")
	@RequestMapping(method=RequestMethod.POST)
	public String upload(
			@Valid @ModelAttribute(value = "uploadinfo") AppUploadInfo uploadinfo,
			BindingResult result, Model model) {
		if (uploadinfo.getApk().isEmpty()){
			result.reject("100", "请选择一个android应用文件");
			model.addAttribute("resultB", true);
			return "withy/appupload";
		}
		App app = appService.uploadApp(uploadinfo);
		if (app==null) {
			result.reject("100", "应用上传失败");
			model.addAttribute("resultB", true);
			return "withy/appupload";
		}
		
		SystemPathUtil.reProduceAppIcon(app, context);
		appService.update(app);
		model.addAttribute("uploaded", true);
		return "withy/appupload";
	}
}
