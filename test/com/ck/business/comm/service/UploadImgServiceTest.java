package com.ck.business.comm.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ck.business.BaseInterfaceTest;
import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.image.ImageTool2;
import com.ck.platform.base.util.image.ImageUtils;
import com.ck.platform.business.comm.service.UploadImgService;

public class UploadImgServiceTest extends BaseInterfaceTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(UploadImgServiceTest.class);

	@Resource
	UploadImgService service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void testUpload() {
		String img_path = "/Users/lyq/space/img/qq.png";
		// FastDFSUtil.compressImage("/root/img_01.jpg", "/root/img_01_111.jpg",
		// "jpg");
		// AllinImageTool.portraitHandle(img_path);
		ImageTool2.generateImgHandle(img_path);
	}
	
	@Test
	public void testUploadProductImg() {
		String img_path = "/Users/lyq/space/img/lxb3.jpeg";
		Map paramMap=new HashMap();
		paramMap.put("extName", "jpeg");
		File file=new File(img_path);
		String fileContent=ImageUtils.getImageStr(file);
		paramMap.put("fileContent", fileContent);
		RepObject result=service.uploadProductImg(paramMap);
		System.out.println("----"+result.getStatus());
		System.out.println("----"+result.getData());
		
	}

}