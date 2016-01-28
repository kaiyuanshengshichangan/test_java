package com.ck.platform.business.comm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ck.platform.base.constants.ResponseCode;
import com.ck.platform.base.constants.ServerConfig;
import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.image.ImageTool;
import com.ck.platform.base.util.image.ImageTool2;
import com.ck.platform.base.util.image.ImageUtils;
import com.ck.platform.base.util.image.Img4Util;
import com.ck.platform.base.util.object.StringTool;
import com.ck.platform.business.comm.service.UploadImgService;

@Service("uploadImgService")
@SuppressWarnings({ "rawtypes" })
@Transactional(rollbackFor = Exception.class)
public class UploadImgServiceImpl implements UploadImgService {

	@Value(value = "${path.image.store}")
	private String path_image_store = "";
	
	public RepObject uploadProductImg(Map paramMap){
		RepObject repObject = new RepObject(false,"","");
		try {
			String serverDNS="http://"+ServerConfig.SERVER_DNS_IMG01;
			String scene="p";
			String scene_dir="product";
			String extName = StringTool.getMapString(paramMap, "extName");
			String fileContent = StringTool.getMapString(paramMap,"fileContent");
			if (StringUtils.isEmpty(extName) || StringUtils.isEmpty(fileContent)) {
				repObject.setCode(ResponseCode.GLOBAL_SYSTEM_INCOMPLEMENT);
				repObject.setMsg("参数不完整");
			} else {
				String srcPath = "";
				Long timestamp = System.currentTimeMillis();
				String filePath="/"+scene_dir+"/" + timestamp+"_"+scene + "." + extName;//原图存储路径
				//生成原图
				srcPath = path_image_store + filePath; 
				ImageUtils.GenerateImage(fileContent, srcPath); 
				Map imageMap=Img4Util.getImageInfo(srcPath);
				int originalWidth = 0;
				if (imageMap.containsKey("width")) {
					originalWidth = Integer.parseInt(imageMap.get("width")
							.toString());
				}
				int originalHeight = 0;
				if (imageMap.containsKey("height")) {
					originalHeight = Integer.parseInt(imageMap.get("height")
							.toString());
				}
				
				//生成压缩图片
				Map imageCMap = ImageTool2.generateImgHandle(srcPath); 
				
				//生成规格图
				Map spec1Map=ImageTool2.handle_special(null, srcPath, originalWidth,originalHeight, 600, 600, extName);
				Map spec2Map=ImageTool2.handle_special(null, srcPath, originalWidth,originalHeight, 300, 300, extName);
				Map spec3Map=ImageTool2.handle_special(null, srcPath, originalWidth,originalHeight, 150, 150, extName);
				Map<String,Object> repData = new HashMap<String,Object>();
				repData.put("attPath", filePath);
				repData.put("url", serverDNS+filePath);
				repData.put("size", fileContent.length());
				repData.put("width", StringTool.getMapString(imageMap, "width"));
				repData.put("height", StringTool.getMapString(imageMap, "height"));
				repData.put("cWidth", StringTool.getMapString(imageCMap, "width"));
				repData.put("cHeight", StringTool.getMapString(imageCMap, "height"));
				repObject.setData(repData);
				repObject.setStatus(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return repObject;
	}
	
	
	public RepObject uploadCustomerPortraitImg(Map paramMap){
		RepObject repObject = new RepObject(false,"","");
		try {
		
			String serverDNS="http://"+ServerConfig.SERVER_DNS_IMG01;
			String scene="p";
			String scene_dir="customer";
			String extName = StringTool.getMapString(paramMap, "extName");
			String fileContent = StringTool.getMapString(paramMap,"fileContent");
			if (StringUtils.isEmpty(extName) || StringUtils.isEmpty(fileContent)) {
				repObject.setCode(ResponseCode.GLOBAL_SYSTEM_INCOMPLEMENT);
				repObject.setMsg("参数不完整");
			} else {
				String srcPath = "";
				Long timestamp = System.currentTimeMillis();
				String filePath="/"+scene_dir+"/" + timestamp+"_"+scene + "." + extName;//原图存储路径
				//生成原图
				srcPath = path_image_store + filePath; 
				ImageUtils.GenerateImage(fileContent, srcPath); 
				Map imageMap=Img4Util.getImageInfo(srcPath);
				int originalWidth = 0;
				if (imageMap.containsKey("width")) {
					originalWidth = Integer.parseInt(imageMap.get("width")
							.toString());
				}
				int originalHeight = 0;
				if (imageMap.containsKey("height")) {
					originalHeight = Integer.parseInt(imageMap.get("height")
							.toString());
				}
				
				//生成压缩图片
				Map imageCMap = ImageTool2.generateImgHandle(srcPath); 
				//生成规格图
				Map spec1Map=ImageTool2.handle_special(null, srcPath, originalWidth,originalHeight, 200, 300, extName);
				Map spec2Map=ImageTool2.handle_special(null, srcPath, originalWidth,originalHeight, 150, 150, extName);
				
				Map<String,Object> repData = new HashMap<String,Object>();
				repData.put("attPath", filePath);
				repData.put("url", serverDNS+filePath);
				repData.put("size", fileContent.length());
				repData.put("width", StringTool.getMapString(imageMap, "width"));
				repData.put("height", StringTool.getMapString(imageMap, "height"));
				repData.put("cWidth", StringTool.getMapString(imageCMap, "width"));
				repData.put("cHeight", StringTool.getMapString(imageCMap, "width"));
				repObject.setData(repData);
				repObject.setStatus(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return repObject;
	}
	
	
	
}
