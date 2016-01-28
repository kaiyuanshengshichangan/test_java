package com.ck.platform.base.util.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.process.ArrayListOutputConsumer;

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
public class FastDFSUtil {

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

	private static TrackerClient trackerClient;
	private static TrackerServer trackerServer;
	private static StorageServer storageServer;
	private static StorageClient storageClient;

	static { // Initialize Fast DFS Client configurations
		try {

			String classPath = new File(FastDFSUtil.class.getResource("/").getFile()).getCanonicalPath();
			System.out.println("static classPath=" + classPath);
			String fdfsClientConfigFilePath = classPath + File.separator+ CLIENT_CONFIG_FILE;

			// logger.info("Fast DFS configuration file path:" +
			// fdfsClientConfigFilePath);
			ClientGlobal.init(fdfsClientConfigFilePath);

			trackerClient = new TrackerClient();
			trackerServer = trackerClient.getConnection();

			storageClient = new StorageClient(trackerServer, storageServer);

		} catch (Exception e) {

		}
	}

	
	public static String[] upload(FastDFSFile file, String fileName) {

		System.out.println("upload  FastDFSFile.GETEXT=" + file.getExt());
		System.out.println("upload  fileName=" + fileName);
		String[] return_uploadResults = null;

		try {

			NameValuePair[] meta_list = null;

			if (fileName != null && fileName.length() > 0) {
				meta_list = new NameValuePair[1];
				meta_list[0] = new NameValuePair("name", fileName);
				meta_list[0] = new NameValuePair("fileName", fileName
						+ file.getExt());
			}

			System.out.println("meta_list=" + meta_list[0].getValue());
			return_uploadResults = storageClient.upload_file(file.getContent(),
					file.getExt(), meta_list);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return return_uploadResults;

	}

	public static String[] uploadRef(String[] uploadResults, String destPath,
			String prefix_name, String extName) {
		try {

			File newFile = new File(destPath);

			System.out.println("uploadRef  destPath=" + destPath);
			System.out.println("uploadRef  prefix_name=" + prefix_name);

			FileInputStream fis = new FileInputStream(newFile);

			byte[] file_buff = null;

			if (fis != null) {
				int len = fis.available();
				file_buff = new byte[len];
				fis.read(file_buff);
			}

			FastDFSFile file_ref = new FastDFSFile("", file_buff, extName);

			String groupName = uploadResults[0];
			String remoteFileName = uploadResults[1];

			String master_filename = remoteFileName; // 获取原始文件的remoteID

			uploadResults = storageClient.upload_file(groupName,
					master_filename, prefix_name, file_ref.getContent(),
					file_ref.getExt(), null);

			System.out.println("uploadRef  uploadResults[0]="
					+ uploadResults[0]);
			System.out.println("uploadRef  uploadResults[1]="
					+ uploadResults[1]);

			
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return uploadResults;
	}

	/**
	 * NameValuePair[] meta_list = new NameValuePair[3]; meta_list[0] = new
	 * NameValuePair("width", "120"); meta_list[1] = new NameValuePair("heigth",
	 * "120"); meta_list[2] = new NameValuePair("author", "Diandi");
	 * 
	 * @param groupName
	 * @param remoteFileName
	 * @return
	 */

	public static FileInfo getFile(String groupName, String remoteFileName) {
		try {
			return storageClient.get_file_info(groupName, remoteFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void deleteFile(String groupName, String remoteFileName)
			throws Exception {
		storageClient.delete_file(groupName, remoteFileName);
	}

	public static StorageServer[] getStoreStorages(String groupName)
			throws IOException {
		return trackerClient.getStoreStorages(trackerServer, groupName);
	}

	public static ServerInfo[] getFetchStorages(String groupName,
			String remoteFileName) throws IOException {
		return trackerClient.getFetchStorages(trackerServer, groupName,
				remoteFileName);
	}


}