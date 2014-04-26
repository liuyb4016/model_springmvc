package cn.liuyb.app.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.liuyb.app.common.cache.MemCached;
import cn.liuyb.app.common.utils.Slf4jLogUtils;
import cn.liuyb.app.common.utils.SystemPathUtil;
import cn.liuyb.app.dao.AppDao;
import cn.liuyb.app.domain.App;
import cn.liuyb.app.domain.AppUploadInfo;
import cn.liuyb.app.service.AppService;

@Service
public class AppServiceImpl implements AppService {
	
	private static Logger logger = Slf4jLogUtils.getLogger(AppServiceImpl.class);
	
	@Autowired
	private AppDao appDao;
	
	@Autowired
	private ServletContext context;
	
	@Override
	public List<App> findAll() {
		return appDao.findAll();
	}

	@Override
	public int countAll() {
		return appDao.countAll();
	}

	@Override
	public App findByPackageName(String packageName) {
		return appDao.findByPackageName(packageName);
	}

	@Override
	public App findById(Long appId) {
		return appDao.find(appId);
	}
	
	@Override
	public App findFmById(Long appId) {
		Object o = MemCached.INSTANCE.get(AppDao.APP + appId);
		if(o!=null){
			return (App)o;
		}else{
			App a = appDao.find(appId);
			if(a!=null){
				MemCached.INSTANCE.set(AppDao.APP + appId,a);
				return appDao.find(appId);
			}
			return null;
		}
		
	}
	
	@Transactional
	@Override
	public App uploadApp(AppUploadInfo uploadinfo) {
		String folderPath = SystemPathUtil.getTempPath(context);
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String fileName = "upload";
		File apk = SystemPathUtil.getNoRepeatUploadApkFile(folder, fileName);
		MultipartFile apkFile = uploadinfo.getApk();
		try {
			apkFile.transferTo(apk);
		} catch (IllegalStateException e2) {
			logger.error("move apk file error", e2);
			return null;
		} catch (IOException e2) {
			logger.error("move apk file error", e2);
			return null;
		}
		App tempapp = new App();
		if (SystemPathUtil.getUploadApkInfo(tempapp, apk, context)) {
			App app = findByPackageName(tempapp.getPackageName());
			tempapp.setUpdateTime(new Date(System.currentTimeMillis()));
			String newName = tempapp.getPackageName() + ".apk";
			String newFolder = SystemPathUtil.getAppResPath(tempapp.getPackageName(), tempapp.getVersionCode());
			String newPath = newFolder + newName;
			File newPlace = new File(newPath);
			try {
				if(newPlace.exists()){
					newPlace.delete();
				}
				FileUtils.moveFile(apk, newPlace);
			} catch (IOException e1) {
				logger.error("apk move error", e1);
			}
			tempapp.setFileName(newName);
			if (app != null) {
				tempapp.setId(app.getId());
				tempapp.setDownloadCount(app.getDownloadCount()==null?0:app.getDownloadCount());
				appDao.update(tempapp);
			} else {
				tempapp.setDownloadCount(0);
				tempapp.setCtrateTime(new Date(System.currentTimeMillis()));
				appDao.create(tempapp);
			}
			// 更新缓存
			MemCached.INSTANCE.set(AppDao.APP + tempapp.getId(),tempapp);
			return tempapp;
		}
		return null;
	}
	
	@Transactional
	@Override
	public void update(App app) {
		app.setUpdateTime(new Date());
		appDao.update(app);
		MemCached.INSTANCE.set(AppDao.APP + app.getId(),app);
	}
	
	@Transactional
	@Override
	public void deleteApp(Long id) {
		App app = appDao.find(id);
		appDao.delete(app);
		// 更新缓存
		MemCached.INSTANCE.delete(AppDao.APP + id);
	}

	@Transactional
	@Override
	public App uploadApp(File apk) {
		App tempapp = new App();
		if (SystemPathUtil.getUploadApkInfo(tempapp, apk, context)) {
			App app = findByPackageName(tempapp.getPackageName());
			tempapp.setUpdateTime(new Date(System.currentTimeMillis()));
			String newName = tempapp.getPackageName() + ".apk";
			String newFolder = SystemPathUtil.getAppResPath(tempapp.getPackageName(), tempapp.getVersionCode());
			String newPath = newFolder + newName;
			File newPlace = new File(newPath);
			try {
				if(newPlace.exists()){
					newPlace.delete();
				}
				FileUtils.moveFile(apk, newPlace);
			} catch (IOException e1) {
				logger.error("apk move error", e1);
			}
			tempapp.setFileName(newName);
			if (app != null) {
				tempapp.setId(app.getId());
				tempapp.setDownloadCount(app.getDownloadCount()==null?0:app.getDownloadCount());
				appDao.update(tempapp);
			} else {
				tempapp.setDownloadCount(0);
				tempapp.setCtrateTime(new Date(System.currentTimeMillis()));
				appDao.create(tempapp);
			}
			// 更新缓存
			MemCached.INSTANCE.set(AppDao.APP + tempapp.getId(),tempapp);
			return tempapp;
		}
		return null;
	}
}