package com.ck.platform.base.util.image;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import com.ck.platform.base.util.core.file.FileUtil;
import com.ck.platform.base.util.object.StringTool;


public class ImageTool2 {
	
	public static Map generateImgHandle(String srcPath) {
		Map returnMap = new HashMap();
		try {
			File srcFile = new File(srcPath);
			String extName = srcFile.getName().substring(srcFile.getName().lastIndexOf(".") + 1);
//			FileInputStream fis = new FileInputStream(srcFile);
//			byte[] file_buff = null;
//			if (fis != null) {
//				int len = fis.available();
//				file_buff = new byte[len];
//				fis.read(file_buff);
//			}
			//FastDFSFile src_fastDfsfile = new FastDFSFile(null, file_buff,extName);
			// 原始文件上传
			//String[] uploadResults = FastDFSUtil.upload(src_fastDfsfile, "520");
			//String groupName = uploadResults[0];
			//String remoteFileName = uploadResults[1];
			// 文件路径
			//returnMap.put("filePath", groupName + "/" + remoteFileName);
			// 需要存储组名和文件名
			//FileInfo fileInfo = FastDFSUtil.getFile(groupName, remoteFileName);
			//returnMap.put("fileSize", fileInfo.getFileSize());
			
			// 原始文件压缩并上传
			String dest_compress_path = StringTool.replace(srcPath, FastDFSUtil.DIAN_SEPARATOR,FastDFSUtil.UNDERLINE_SEPARATOR
									+ FastDFSUtil.COMPRESS
									+ FastDFSUtil.DIAN_SEPARATOR);
			 Img4Util.compressImage(srcPath, dest_compress_path, extName);
			//FastDFSUtil.uploadRef(uploadResults, dest_compress_path, "_c",extName);
			 returnMap = Img4Util.getImageInfo(dest_compress_path);
			// 变更为压缩后的图片，准备对此图片进行剪裁、缩略图、水印等
			/*srcPath = StringTool.replace(srcPath, FastDFSUtil.DIAN_SEPARATOR,
					FastDFSUtil.UNDERLINE_SEPARATOR + FastDFSUtil.COMPRESS
							+ FastDFSUtil.DIAN_SEPARATOR);*/
			// 处理在终端、列表场景显示的图片
//			String[] dyna = handle_scene_normal(dest_compress_path,
//					uploadResults, originalWidth, originalHeight, SCENE_SPEC.get(scene+"_max"),
//					SCENE_SPEC.get(scene+"_min"), scene, extName);

			// Handle return
			//returnMap.put("dynaWidth", dyna[1]);
			//returnMap.put("dynaHeight", dyna[1]);
			System.out.println("returnMap=" + returnMap);
			//FileUtil.deleteFile(srcPath);
			//FileUtil.deleteFile(dest_compress_path);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnMap;

	}
	
	/**
	 * 生成不同规格的图片－场景1 
	 * 300*300 150*150 100*100
	 * @param srcPath
	 * @param uploadResults
	 * @param originalWidth
	 * @param originalHeight
	 * @param extName
	 * @param sceneFlag
	 * @return
	 */
	public static Map handle_scene1_normal(String srcPath,
			String[] uploadResults, int originalWidth, int originalHeight, String extName,String sceneFlag) {

		// 第一张图片的处理（最大规格的300*300），其他规格从此文件生成
		Map dyna = null;
		try {
			dyna = handle_special(uploadResults, srcPath, originalWidth,originalHeight, 300, 300, extName);
			handle_special(uploadResults, srcPath, originalWidth,originalHeight, 200, 200, extName);
			handle_special(uploadResults, srcPath, originalWidth,originalHeight, 100, 100, extName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dyna;
	}
	
	/**
	 * 生成不同规格的图片－场景2 
	 * @param srcPath
	 * @param uploadResults
	 * @param originalWidth
	 * @param originalHeight
	 * @param extName
	 * @param sceneFlag
	 * @return
	 */
	public static Map handle_scene2_normal(String srcPath,
			String[] uploadResults, int originalWidth, int originalHeight, String extName,String sceneFlag) {

		// 第一张图片的处理（最大规格的300*300），其他规格从此文件生成
		Map dyna = null;
		try {
			dyna = handle_special(uploadResults, srcPath, originalWidth,originalHeight, 600, 800, extName);
			handle_special(uploadResults, srcPath, originalWidth,originalHeight, 300, 600, extName);
			handle_special(uploadResults, srcPath, originalWidth,originalHeight, 200, 300, extName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dyna;
	}
	
	/**
	 * 生成不同规格的图片－场景3
	 * 300*300 150*150 100*100
	 * @param srcPath
	 * @param uploadResults
	 * @param originalWidth
	 * @param originalHeight
	 * @param extName
	 * @param sceneFlag
	 * @return
	 */
	public static Map handle_scene3_normal(String srcPath,
			String[] uploadResults, int originalWidth, int originalHeight, String extName,String sceneFlag) {

		// 第一张图片的处理（最大规格的300*300），其他规格从此文件生成
		Map dyna = null;
		try {
			dyna = handle_special(uploadResults, srcPath, originalWidth,originalHeight, 800, 600, extName);
			handle_special(uploadResults, srcPath, originalWidth,originalHeight, 600, 400, extName);
			handle_special(uploadResults, srcPath, originalWidth,originalHeight, 300, 200, extName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dyna;
	}

	/**
	 * 处理图片－压缩、剪裁
	 * @param uploadResults
	 * @param srcPath
	 * @param originalWidth
	 * @param originalHeight
	 * @param limitLow
	 * @param limitHigh
	 * @param extName
	 * @param sceneFlag
	 * @return
	 */
	public static Map handle_special(String[] uploadResults,
			String srcPath, int originalWidth, int originalHeight,
			int limitLow, int limitHigh, String extName) {

		Map imageMap=null;
		try {
			Map a = getParamSpec(originalWidth, originalHeight, limitLow,limitHigh);
			Boolean isZoom = (Boolean) a.get("isZoom");
			Boolean isCrop = (Boolean) a.get("isCrop");
			int zoomWidth = (Integer) a.get("zoomWidth");
			int zoomHeight = (Integer) a.get("zoomHeight");
			int crop_x_start = (Integer) a.get("crop_x_start");
			int crop_x_end = (Integer) a.get("crop_x_end");
			int crop_y_start =  (Integer) a.get("crop_y_start");
			int crop_y_end =  (Integer) a.get("crop_y_end");
			System.out.println("originalWidth=" + originalWidth
			+"originalHeight="+ originalHeight
			+"cropWidth=" + crop_x_end
			+"cropHeight="+ crop_y_end
			+"isZoom=" + isZoom
			+"isCrop=" + isCrop);
			String destPath = srcPath; //处理后的图片路径
			String destPath_temp="";
			if (isZoom && isCrop) {
				 destPath_temp = StringTool.replace(srcPath,
						FastDFSUtil.DIAN_SEPARATOR, "_temp"
								+ FastDFSUtil.DIAN_SEPARATOR);
				Img4Util.zoomImage(zoomWidth, zoomHeight, srcPath,destPath_temp, extName);
				destPath = destPath_temp.replace("_temp", "");
				destPath = returnS(destPath, limitLow , limitHigh);
				Img4Util.cropImage(destPath_temp, destPath, crop_x_start,crop_y_start, crop_x_end, crop_y_end, extName);
			} else if (isZoom && !isCrop) {
				 destPath_temp = StringTool.replace(srcPath,
						FastDFSUtil.DIAN_SEPARATOR, "_temp"
								+ FastDFSUtil.DIAN_SEPARATOR);
				destPath = destPath_temp.replace("_temp", "");
				destPath = returnS(destPath, limitLow , limitHigh);
				Img4Util.zoomImage(zoomWidth, zoomHeight, srcPath, destPath,extName);

			} else if (!isZoom && isCrop) {
				 destPath_temp = StringTool.replace(srcPath,
						FastDFSUtil.DIAN_SEPARATOR, "_temp"
								+ FastDFSUtil.DIAN_SEPARATOR);
				destPath = destPath_temp.replace("_temp", "");
				destPath = returnS(destPath, limitLow , limitHigh);
				Img4Util.cropImage(srcPath, destPath, crop_x_start, crop_y_start,crop_x_end, crop_y_end, extName);

			}
//
//			if (sceneFlag.equalsIgnoreCase(DYNAMIC_SCENE)) {
//				FastDFSUtil.uploadRef(uploadResults, destPath, "_c_"
//						+ sceneFlag + "_sp", extName);
//			} else if (sceneFlag.equalsIgnoreCase(TERMINAL_SCENE)) {
//				FastDFSUtil.uploadRef(uploadResults, destPath, "_c_" + sceneFlag
//								+ "_" + limitHigh + "_" + limitLow, extName);
//			} else if (sceneFlag.equalsIgnoreCase(PORTRAIT_SCENE)) {
//				FastDFSUtil.uploadRef(uploadResults, destPath, "_c_"+ sceneFlag + "_" + limitLow + "_" + limitLow, extName);
//
//			}
			imageMap = Img4Util.getImageInfo(destPath);
			//FileUtil.deleteFile(destPath);
			FileUtil.deleteFile(destPath_temp);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return imageMap;

	}
	
	private static Map getParamSpec(int originalWidth, int originalHeight,int limitWidth,int limitHeight) {
		Map returnMap = new HashMap();
		boolean isZoom = Boolean.FALSE; //是否缩放
		boolean isCrop = Boolean.FALSE; //是否剪裁
		int zoomWidth = 0;// 处理后的宽度
		int zoomHeight = 0;// 处理后的高度
		int crop_x_start=0;
		int crop_x_end=0;
		int crop_y_start=0;
		int crop_y_end=0;
		int widthC=originalWidth-limitWidth; //宽度差
		int heightC=originalHeight-limitHeight;//高度差
		float zoomRadis=0; //缩放比例
		if (originalWidth >= limitWidth && originalHeight>=limitHeight) {
			isZoom=true;
			if(widthC>=heightC){
				zoomRadis=(float)limitHeight/originalHeight;
				zoomHeight=limitHeight;
				zoomWidth=Math.round(originalWidth*zoomRadis);
				crop_x_start=Math.round(((float)(zoomWidth-limitWidth)/2));
				crop_x_end=crop_x_start+limitWidth;
				crop_y_start=0;
				crop_y_end=limitHeight;
			}else{
				zoomRadis=(float)limitWidth/originalWidth;
				zoomWidth=limitWidth;
				zoomHeight=Math.round(originalHeight*zoomRadis);
				crop_x_start=0;
				crop_x_end=limitWidth;
				crop_y_start=Math.round(((float)(zoomHeight-limitHeight)/2));
				crop_y_end=crop_y_start+limitHeight;
			}
			if(crop_x_start>0 || crop_y_start>0){
				isCrop=true;	
			}
		} else if (originalWidth >= limitWidth && originalHeight<limitHeight) {
			isCrop=true;
			crop_x_start=Math.round(((float)(originalWidth-limitWidth)/2));
		}else if (originalWidth < limitWidth && originalHeight>=limitHeight) {
			isCrop=true;
			crop_y_start=Math.round(((float)(originalHeight-limitHeight)/2));
		}
		returnMap.put("isZoom", isZoom);
		returnMap.put("isCrop", isCrop);
		returnMap.put("zoomWidth", zoomWidth);
		returnMap.put("zoomHeight", zoomHeight);
		returnMap.put("crop_x_start", crop_x_start);
		returnMap.put("crop_x_end", crop_x_end);
		returnMap.put("crop_y_start", crop_y_start);
		returnMap.put("crop_y_end", crop_y_end);
		return returnMap;

	}
	
	private static String returnS(String destPath,int width, int height) {
		String returnString = destPath.replace(".", "_"+width + "_"+ height + ".");
		return returnString;
	}
	
	
	public static void main(String[] args) {

		String img_path = "/Users/lyq/space/img/allinlogo.png";
		//convert.createScript("d:\\myscript.sh",op);  
		try {
			//ProcessStarter.setGlobalSearchPath("/usr/local/bin");
//			ConvertCmd cmd = new ConvertCmd(true);
//			// create the operation, add images and operators/options
//			IMOperation op = new IMOperation();
//			cmd.setSearchPath("/usr/local/bin");
//			op.addImage("/Users/lyq/space/img/allin_logo.png");
//			op.resize(800,600);
//			op.addImage("/Users/lyq/space/img/allin_logo332.png");
//			// execute the operation
//			cmd.run(op);
			//generateImgHandle(img_path,"d");
			handle_scene1_normal(img_path,null,120,120,"png","d");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}


}
