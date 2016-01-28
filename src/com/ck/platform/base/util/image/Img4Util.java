package com.ck.platform.base.util.image;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.process.ArrayListOutputConsumer;
import org.im4java.process.ProcessStarter;

/**
 * convert：转换图像格式和大小，模糊，裁剪，驱除污点，抖动，临近，图片上画图片，加入新图片，生成缩略图等。
 identify：描述一个或较多图像文件的格式和特性。
 mogrify：按规定尺寸***一个图像，模糊，裁剪，抖动等。Mogrify改写最初的图像文件然后写到一个不同的图像文件。
 composite：根据一个图片或多个图片组合生成图片。
 montage：创建一些分开的要素图像。在含有要素图像任意的装饰图片，如边框、结构、图片名称等。
 compare：在算术上和视觉上评估不同的图片***其它的改造图片。
 display：如果你拥有一个X server的系统，它可以按次序的显示图片
 animate：利用X server显示动画图片
 import：在X server或任何可见的窗口上输出图片文件。 你可以捕获单一窗口，整个的荧屏或任何荧屏的矩形部分。
 conjure：解释执行 MSL (Magick Scripting Language) 写的脚本。
 */

/**
 * @ClassName: FastDFSUtil
 * @Description: ( FastDfs 操作工具类)
 */
public class Img4Util {

	public static final String PROTOCOL = "http://";
	public static final String MAO_SEPARATOR = "/";
	public static final String DIAN_SEPARATOR = ".";
	public static final String UNDERLINE_SEPARATOR = "_";
	public static final String SEPARATOR = "/";

	public static final String COMPRESS = "c";// compress
	public static final String CROP = "x";// crop
	public static final String THUMB = "t"; // thumb

	public static final String SCENE_LIST = "s_l";// 列表页面
	public static final String SCENE_TERMINAL = "s_t";// 终端页面

	public static final String NUMBER_1 = "1";// compress
	public static final String NUMBER_9 = "9";// crop

	public static final String TERMINAL_PC = "p";
	public static final String TERMINAL_H = "h";
	public static final String TERMINAL_I5S = "i5s";
	public static final String TERMINAL_I6 = "i6";

	public static final String TRACKER_NGNIX_PORT = "80";

	public static final String CLIENT_CONFIG_FILE = "fdfs_client.conf";

	static{
		ProcessStarter.setGlobalSearchPath("/usr/local/bin");
	}

	/**
	 * Executes the Image Magick Identify command with format of Width x Height
	 * 
	 * @param imagePath
	 *            String
	 * @return String format of Image's Width x Height
	 */
	public static String getImageSize(String imagePath) {
		imagePath = imagePath.replaceAll("//", "/");
		String response = null;

		try {
			IdentifyCmd cmd = new IdentifyCmd();
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			cmd.setOutputConsumer(output);

			IMOperation op = new IMOperation();
			op.format("%[fx:w]x%[fx:h]");
			op.addImage(imagePath);
			/*
			 * ProcessTask p = cmd.getProcessTask(imOperation);
			 * p.getProcessStarter().getSearchPath();
			 */
			/*
			 * String globalSearchPath = ImageToolUtil.getGlobalSearchPath(
			 * companyId, siteId);
			 * ProcessStarter.setGlobalSearchPath(globalSearchPath);
			 */

			cmd.run(op);

			ArrayList<String> cmdOutput = output.getOutput();
			response = cmdOutput.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	public static int delete(FastDFSFile file) {

		int returnInt = 0;

		try {
			// returnInt = storageClient.delete_file(arg0, arg1);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return returnInt;

	}


	/**
	 * 
	 * 根据坐标裁剪图片
	 * 
	 * 
	 * 
	 * @param srcPath
	 *            要裁剪图片的路径
	 * 
	 * @param newPath
	 *            裁剪图片后的路径
	 * 
	 * @param x
	 *            起始横坐标
	 * 
	 * @param y
	 *            起始挫坐标
	 * 
	 * @param x1
	 *            结束横坐标
	 * 
	 * @param y1
	 *            结束挫坐标
	 * 
	 * 
	 * 
	 *            width：裁剪的宽度
	 * 
	 *            height：裁剪的高度
	 * 
	 *            x：裁剪的横坐标
	 * 
	 *            y：裁剪的挫坐标
	 */

	public static void cropImage(String srcPath, String destPath, int x_start,
			int y_start, int x_end, int y_end, String nextFixName) {

		try {

			int width = x_end - x_start;
			int height = y_end - y_start;

			IMOperation op = new IMOperation();

			op.addImage(srcPath);

			op.crop(width, height, x_start, y_start);

			op.addImage(destPath);

			ConvertCmd convert = new ConvertCmd(true);

			// linux下不要设置此值，不然会报错

			 //convert.setSearchPath(imageMagickPath);

			convert.run(op);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 
	 * 根据尺寸缩放图片
	 * 
	 * @param width
	 *            缩放后的图片宽度
	 * 
	 * @param height
	 *            缩放后的图片高度
	 * 
	 * @param srcPath
	 *            源图片路径
	 * 
	 * @param prefixName
	 *            缩放后图片的路径
	 */

	public static void zoomImage(int width, int height, String srcPath,
			String destPath, String nextFixName) {

		try {

			IMOperation op = new IMOperation();
			op.addImage(srcPath);

			if (width == 0) {// 根据高度缩放图片
				op.resize(null, height);
			} else if (height == 0) {// 根据宽度缩放图片
				// op.addRawArgs("-resize", "200");
				op.resize(width, null);
			} else {
				op.resize(width, height);
			}
			op.addImage(destPath);
			ConvertCmd convert = new ConvertCmd(Boolean.TRUE);
			//
			// linux下不要设置此值，不然会报错
			// convert.setSearchPath(imageMagickPath);
			convert.run(op);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 
	 * 给图片加水印
	 * 
	 * @param srcPath
	 *            源图片路径
	 */

	public static void addImgText(String srcPath) {

		try {

			IMOperation op = new IMOperation();

			op.font("宋体").gravity("southeast").pointsize(18).fill("#BCBFC8")
					.draw("text 55 ibokee.com");

			op.addImage();

			op.addImage();

			ConvertCmd convert = new ConvertCmd();

			// linux下不要设置此值，不然会报错

			// convert.setSearchPath(imageMagickPath);

			convert.run(op);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 图片处理（缩略图、水印等）
	 * 
	 * @param savedFile
	 */

	public static void imgHandle(File parent, FileItem item) {
		try {

			File savedFile = new File(parent, item.getName());

			GMOperation op = new GMOperation();
			// 待处理图片的绝对路径
			op.addImage(savedFile.getAbsolutePath());
			// 图片压缩比，有效值范围是0.0-100.0，数值越大，缩略图越清晰 s
			op.quality(20.0);
			// width 和height可以是原图的尺寸，也可以是按比例处理后的尺寸
			op.addRawArgs("-resize", "100");
			// 宽高都为100
			// op.addRawArgs("-resize", "100x100");
			op.addRawArgs("-gravity", "center");
			// op.resize(100, null);
			// 处理后图片的绝对路径
			File smallFile = new File(savedFile.getAbsolutePath() + "/small");

			if (!smallFile.exists()) {
				smallFile.mkdir();
			}

			op.addImage(smallFile.getAbsolutePath() + "/" + item.getName());

			// 如果使用ImageMagick，设为false,使用GraphicsMagick，就设为true，默认为false

			ConvertCmd convert = new ConvertCmd(true);

			String osName = System.getProperty("os.name").toLowerCase();

			if (osName.contains("win")) {
				// linux下不要设置此值，不然会报错
				convert.setSearchPath("D://Program Files//GraphicsMagick-1.3.19-Q8");
			}

			convert.run(op);

			// System.out.println((System.currentTimeMillis() - time));
			// 压缩图片保存

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static String[] compressImage(String srcPath, String destPath,
			String extName) {

		String[] return_uploadResults = null;
		try {

			System.out.println("compressImage srcPath="+srcPath);
			System.out.println("compressImage destPath="+destPath);
			
			
			IMOperation op = new IMOperation();

			op.addImage(srcPath);

			// 图片质量
			op.addRawArgs("-quality", "75");

			op.addImage(destPath);

			ConvertCmd convert = new ConvertCmd(Boolean.TRUE);
			//
			// linux下不要设置此值，不然会报错

			// convert.setSearchPath(imageMagickPath);

			convert.run(op);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return return_uploadResults;
	}

	/**
	 * 图片旋转
	 * 
	 * @param imagePath
	 *            源图片路径
	 * @param newPath
	 *            处理后图片路径
	 * @param degree
	 *            旋转角度
	 */
	public boolean rotate(String imagePath, String newPath, double degree) {
		boolean flag = false;
		try {
			// 1.将角度转换到0-360度之间
			degree = degree % 360;
			if (degree <= 0) {
				degree = 360 + degree;
			}
			IMOperation op = new IMOperation();
			op.addImage(imagePath);
			op.rotate(degree);
			op.addImage(newPath);
			ConvertCmd cmd = new ConvertCmd(true);
			cmd.run(op);
			flag = true;
		} catch (Exception e) {
			flag = false;
			System.out.println("图片旋转失败!");
		}
		return flag;
	}

	/**
	 * 092 * 图片信息 093 * 094 * @param imagePath 095 * @return 096
	 */
	public static Map getImageInfo(String imagePath) {
		Map returnMap = null;
		String line = null;
		try {
			IMOperation op = new IMOperation();
			op.format("width:%w,height:%h,path:%d%f,size:%b,originalTime:%[EXIF:DateTimeOriginal]");
			op.addImage(1);
			IdentifyCmd identifyCmd = new IdentifyCmd(true);
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			identifyCmd.setOutputConsumer(output);
			identifyCmd.run(op, imagePath);
			ArrayList<String> cmdOutput = output.getOutput();

			// assert cmdOutput.size() == 1;
			line = cmdOutput.get(0);

			System.out.println("line=" + line);

			String[] a = line.split(",");

			if (a.length > 0) {
				returnMap = new HashMap();
				for (String temp : a) {

					returnMap.put(temp.substring(0, temp.indexOf(":")),
							temp.substring(temp.indexOf(":") + 1));
				}

				System.out.println("returnMap=" + returnMap);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

}