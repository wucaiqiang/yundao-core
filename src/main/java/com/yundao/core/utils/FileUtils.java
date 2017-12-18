package com.yundao.core.utils;

import java.io.File;
import java.io.IOException;

import com.yundao.core.constant.CommonConstant;

/**
 * 文件工具类
 *
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class FileUtils {

	/**
	 * 创建目录
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void mkdirs(File file) throws IOException {
		mkdirs(file, false);
	}

	/**
	 * 创建目录
	 * 
	 * @param file
	 * @param isGetParentFile
	 *            若为true则只创建父目录
	 * @throws IOException
	 */
	public static void mkdirs(File file, boolean isGetParentFile) throws IOException {
		if (file != null) {
			File canonicalFile = file.getCanonicalFile();
			if (isGetParentFile) {
				canonicalFile = canonicalFile.getParentFile();
			}
			if (canonicalFile != null && !canonicalFile.exists()) {
				canonicalFile.mkdirs();
			}
		}
	}

	/**
	 * 生成新的文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String generateFileName(String fileName) {
		if (BooleanUtils.isBlank(fileName)) {
			return null;
		}

		String ext = "";
		int index = fileName.lastIndexOf(".");
		if (index != -1) {
			ext = fileName.substring(index);
		}
		return UUIDUtils.getUUID() + ext;
	}

	/**
	 * 连接两个路径
	 * 
	 * @param rootPath
	 * @param relativePath
	 * @return
	 */
	public static String getRealPath(String rootPath, String relativePath) {
		if (BooleanUtils.isBlank(rootPath)) {
			return relativePath;
		}

		String result = rootPath;
		if (!BooleanUtils.isBlank(relativePath)) {
			boolean isEnd = false;
			int rootPathLength = rootPath.length() - 1;
			if (rootPath.charAt(rootPathLength) == CommonConstant.PATH_SEPARATOR) {
				isEnd = true;
			}

			boolean isBegin = false;
			if (relativePath.charAt(0) == CommonConstant.PATH_SEPARATOR) {
				isBegin = true;
			}

			if (isEnd && isBegin) {
				rootPath = rootPath.substring(0, rootPathLength);
			}
			else if (!isEnd && !isBegin) {
				rootPath += CommonConstant.PATH_SEPARATOR;
			}
			result = rootPath + relativePath;
		}
		return result;
	}

	/**
	 * 通过File删除文件
	 * 
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(File file) {
		if (file == null || !file.exists()) {
			return false;
		}
		return deleteDir(file);
	}

	/**
	 * 通过文件路径删除文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath) {
		if (BooleanUtils.isEmpty(filePath)) {
			return false;
		}
		return deleteFile(new File(filePath));
	}

	/**
	 * 递归删除文件
	 * @param file
	 * @return
	 */
	private static boolean deleteDir(File file) {
		if (file.isDirectory()) {
			String[] children = file.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(file, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return file.delete();
	}
}
