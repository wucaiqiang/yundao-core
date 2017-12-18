package com.yundao.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;

/**
 * zip工具类
 * 
 * @author gjl
 *
 */
public abstract class ZipUtil {

	private static Log log = LogFactory.getLog(ZipUtil.class);

	/**
	 * 解压zip包
	 * 
	 * @param sZipPathFile
	 *            zip文件路径
	 * @param sDestPath
	 *            解压路径
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Map<String, File> extract(String sZipPathFile, String sDestPath) {
		try {
			return extract(new FileInputStream(sZipPathFile), sDestPath);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, File> extract(InputStream is, String sDestPath) {
		Map<String, File> result = new HashMap<String, File>();
		ZipInputStream zins = null;
		try {
			// 先指定压缩档的位置和档名，建立FileInputStream对象
			zins = new ZipInputStream(is);
			ZipEntry ze = null;
			byte[] ch = new byte[256];
			while ((ze = zins.getNextEntry()) != null) {
				File zfile = new File(sDestPath + File.separator + ze.getName());
				File fpath = new File(zfile.getParentFile().getPath());
				if (ze.isDirectory()) {
					if (!zfile.exists())
						zfile.mkdirs();
					zins.closeEntry();
				}
				else {
					if (!fpath.exists())
						fpath.mkdirs();
					FileOutputStream fouts = new FileOutputStream(zfile);
					int i;
					result.put(zfile.getCanonicalPath().replace(sDestPath, ""), zfile);
					while ((i = zins.read(ch)) != -1)
						fouts.write(ch, 0, i);
					zins.closeEntry();
					fouts.close();
				}
			}
		}
		catch (Exception e) {
			log.error("解压时异常", e);
		}
		finally {
			CloseableUtils.close(zins);
		}
		return result;
	}

}