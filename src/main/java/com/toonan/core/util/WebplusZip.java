package com.toonan.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;








/**
 * 
 * 类名:com.toonan.util.WebplusZip
 * 描述:文件压缩
 * 编写者:陈骑元
 * 创建时间:2019年4月25日 下午9:05:03
 * 修改说明:
 */
public class WebplusZip {
	private static Log logger = LogFactory.getLog(WebplusZip.class);// 加载日志功能
	private static final int BUFFER_SIZE = 2 * 1024;
    
	/**
     * 
     * 简要说明：递归压缩文件
     * 编写者：陈骑元
     * 创建时间：2018年11月8日 上午9:19:45
     * @param 说明 srcDir压缩文件路径 outFilePath 输出文件 是否保持原来文件结构
     * @return 说明
     */
	public static boolean compressFolader(String srcDir, String outFilePath, boolean keepDirStructure) {
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(new File(outFilePath));
			return compressFolader(srcDir,fos, keepDirStructure);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
    /**
     * 
     * 简要说明：递归压缩文件
     * 编写者：陈骑元
     * 创建时间：2018年11月8日 上午9:19:45
     * @param 说明 srcDir压缩文件路径 out 输出文件流 KeepDirStructure是否保持原来文件结构
     * @return 说明
     */
	public static boolean  compressFolader(String srcDir, OutputStream out, boolean keepDirStructure) {
        boolean result=false;
		long start = System.currentTimeMillis();

		ZipOutputStream zos = null;

		try {

			zos = new ZipOutputStream(out);

			File sourceFile = new File(srcDir);

			compress(sourceFile, zos, sourceFile.getName(), keepDirStructure);

			long end = System.currentTimeMillis();

			logger.info("压缩完成，耗时：" + (end - start) + " ms");
			result=true;

		} catch (Exception e) {

			throw new RuntimeException("zip error from WebplusZip", e);

		} finally {

			if (zos != null) {

				try {

					zos.close();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}

		}
		return result;

	}

	/**
	 * 
	 * 压缩成ZIP 方法2
	 * 
	 * @param srcFiles
	 *            需要压缩的文件列表
	 * 
	 * @param out
	 *            压缩文件输出流
	 * 
	 * @throws RuntimeException
	 *             压缩失败会抛出运行时异常
	 * 
	 */

	public static void compressZip(List<File> srcFiles, OutputStream out) throws RuntimeException {

		long start = System.currentTimeMillis();

		ZipOutputStream zos = null;

		try {

			zos = new ZipOutputStream(out);

			for (File srcFile : srcFiles) {

				byte[] buf = new byte[BUFFER_SIZE];

				zos.putNextEntry(new ZipEntry(srcFile.getName()));

				int len;

				FileInputStream in = new FileInputStream(srcFile);

				while ((len = in.read(buf)) != -1) {

					zos.write(buf, 0, len);

				}

				zos.closeEntry();

				in.close();

			}

			long end = System.currentTimeMillis();

			logger.info("压缩完成，耗时：" + (end - start) + " ms");

		} catch (Exception e) {

			throw new RuntimeException("zip error from WebplusZip", e);

		} finally {

			if (zos != null) {

				try {

					zos.close();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}

		}

	}

	/**
	 * 
	 * 递归压缩方法
	 * 
	 * @param sourceFile
	 *            源文件
	 * 
	 * @param zos
	 *            zip输出流
	 * 
	 * @param name
	 *            压缩后的名称
	 * 
	 * @param KeepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 * 
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * 
	 * @throws Exception
	 * 
	 */

	private static void compress(File sourceFile, ZipOutputStream zos, String name,

			boolean KeepDirStructure) throws Exception {

		byte[] buf = new byte[BUFFER_SIZE];

		if (sourceFile.isFile()) {

			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字

			zos.putNextEntry(new ZipEntry(name));

			// copy文件到zip输出流中

			int len;

			FileInputStream in = new FileInputStream(sourceFile);

			while ((len = in.read(buf)) != -1) {

				zos.write(buf, 0, len);

			}

			zos.closeEntry();

			in.close();

		} else {

			File[] listFiles = sourceFile.listFiles();

			if (listFiles == null || listFiles.length == 0) {

				// 需要保留原来的文件结构时,需要对空文件夹进行处理

				if (KeepDirStructure) {

					// 空文件夹的处理

					zos.putNextEntry(new ZipEntry(name + "/"));

					// 没有文件，不需要文件的copy

					zos.closeEntry();

				}

			} else {

				for (File file : listFiles) {

					// 判断是否需要保留原来的文件结构

					if (KeepDirStructure) {

						// 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,

						// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了

						compress(file, zos, name + "/" + file.getName(), KeepDirStructure);

					} else {

						compress(file, zos, file.getName(), KeepDirStructure);

					}

				}

			}

		}

	}
	/**
	 * 文件解压功能
	 * 
	 * @param zipFilePath要解压的文件路径
	 * @param filePath解压输出路径
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean decompressZip(String zipFilePath, String filePath) {
		boolean result = false;
		ZipFile zipFile = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			zipFile = new ZipFile(zipFilePath, "GBK"); // 以“GBK”编码创建zip文件，用来处理winRAR压缩的文件。
			Enumeration emu = zipFile.getEntries();

			while (emu.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) emu.nextElement();
				if (entry.isDirectory()) { // 如果是文件夹,则会创建
					new File(filePath + entry.getName()).mkdirs();
					continue;
				}
				bis = new BufferedInputStream(zipFile.getInputStream(entry)); // 使用流解决

				File file = new File(filePath , entry.getName());
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos, BUFFER_SIZE);

				byte[] buf = new byte[BUFFER_SIZE];
				int len = 0;
				while ((len = bis.read(buf, 0, BUFFER_SIZE)) != -1) {
					fos.write(buf, 0, len);
				}
				bos.flush();
			}
			result = true;
		} catch (IOException e) {
			result = false;
			logger.info("解压文件[" + zipFilePath + "]失败");
			e.printStackTrace();
		} finally {

			try {
				if (bos != null) {
					bos.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (zipFile != null) {
					zipFile.close();
				}
			} catch (IOException e) {
				result = false;
				e.printStackTrace();

			}
		}
		logger.info("解压文件[" + zipFilePath + "]成功");
		return result;

	}
	
	/**
	 * 
	 * 简要说明：解压rar文件
	 * 编写者：陈骑元
	 * 创建时间：2019年12月28日 下午4:43:17
	 * @param 说明
	 * @return 说明
	 */
	 public static boolean  decompressRar(String srcRarPath, String dstDirectoryPath)  { 
		if (!srcRarPath.toLowerCase().endsWith(".rar")) {
			logger.error("解压文件失败：不是rar格式的文件");
			return false;
		}
		File dstDiretory = new File(dstDirectoryPath);
		// 目标目录不存在时，创建该文件夹
		if (!dstDiretory.exists()) {
			dstDiretory.mkdirs();
		}
		FileOutputStream os = null;
		Archive archive =null;
		try {
			archive=new Archive(new FileInputStream(new File(srcRarPath)));
			if (WebplusUtil.isNotEmpty(archive)) {
				// 打印文件信息
				archive.getMainHeader().print();
				FileHeader fileHeader = archive.nextFileHeader();
				while (fileHeader != null) {
					// 解决中文乱码问题【压缩文件中文乱码】
					String fileName = fileHeader.getFileNameW().isEmpty() ? fileHeader.getFileNameString()
							: fileHeader.getFileNameW();
					// 文件夹
					if (fileHeader.isDirectory()) {
						File fol = new File(dstDirectoryPath + File.separator + fileName.trim());
						fol.mkdirs();
					} else { // 文件
						// 解决linux系统中\分隔符无法识别问题
						String[] fileParts = fileName.split("\\\\");
						StringBuilder filePath = new StringBuilder();
						for (String filePart : fileParts) {
							filePath.append(filePart).append(File.separator);
						}
						fileName = filePath.substring(0, filePath.length() - 1);
						File out = new File(dstDirectoryPath + File.separator + fileName.trim());
						if (!out.exists()) {
							// 相对路径可能多级，可能需要创建父目录.
							if (!out.getParentFile().exists()) {
								out.getParentFile().mkdirs();
							}
							out.createNewFile();
						}
						os = new FileOutputStream(out);
						archive.extractFile(fileHeader, os);
					}
					fileHeader = archive.nextFileHeader();
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("解压文件失败：" + e);

		} finally {
			  if(os!=null){
				  try {
					os.close();
					os=null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
			  }
			  if(archive!=null){
				  try {
					archive.close();
					archive=null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
			  }

		}
        return false;
	 } 
	 
	/***
     * 采用命令行方式解压文件
     * @param zipFile 压缩文件
     * @param destDir 解压结果路径
     * @param cmdPath WinRAR.exe的路径，也可以在代码中写死
     * @return
     */
    public static boolean extractRar(String  rarFilePath, String destDir,String cmdPath) {
    	   File rarFile=new File(rarFilePath);
    	  if (!rarFile.exists()) {
    		  logger.error("解压rar文件失败："+rarFilePath+"文件路径不存在");
              return false;
          }
        // 解决路径中存在/..格式的路径问题
        destDir = new File(destDir).getAbsoluteFile().getAbsolutePath();
        while(destDir.contains("..")) {
            String[] sepList = destDir.split("\\\\");
            destDir = "";
            for (int i = 0; i < sepList.length; i++) {
                if(!"..".equals(sepList[i]) && i < sepList.length -1 && "..".equals(sepList[i+1])) {
                    i++;
                } else {
                    destDir += sepList[i] + File.separator;
                }
            }
        }

        boolean bool = false;
      
        // 开始调用命令行解压，参数-o+是表示覆盖的意思
        String cmd = cmdPath + " X -o+ " + rarFile + " " + destDir;
        logger.info("调用WinRAR解压软件地址："+cmd);
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            if (proc.waitFor() != 0) {
                if (proc.exitValue() == 0) {
                    bool = false;
                }
            } else {
                bool = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       logger.info("解压" + (bool ? "成功" : "失败"));
        return bool;
    }

	public static void main(String[] args) throws Exception {
		File file=new File("E:\\webpub\\temp\\C191230195052137.rar");
		if(file.exists()){
			System.out.println(">>>>>>>>>>>>");
		}
		extractRar("E:\\webpub\\temp\\C191230195052137.rar","E:\\webpub\\R191230195052712","");
	    
	}

}
