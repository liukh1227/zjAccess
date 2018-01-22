package com.zj.access.service;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import com.zj.base.common.ConstConfig;
import com.zj.entity.unit.FTPInfo;

public class FTPUtil {

	private static Logger logger = Logger.getLogger(FTPUtil.class);
	
	private static FTPInfo ftpInfo;

	/**
	 * 获取FTPClient对象
	 * 
	 * @param ftpHost
	 *            FTP主机服务器
	 * @param ftpPassword
	 *            FTP 登录密码
	 * @param ftpUserName
	 *            FTP登录用户名
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 */
	public static FTPClient getFTPClient(String ftpHost, String ftpPassword,
			String ftpUserName, int ftpPort) {
		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
			ftpClient.setControlKeepAliveTimeout(300); // set timeout to 5 minute
			ftpClient.setConnectTimeout(10 * 1000);//10s 连接超时
			ftpClient.setDefaultTimeout(10 * 1000);
			ftpClient.setDataTimeout(10 * 1000);
			
			ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
			
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				logger.info("未连接到FTP，用户名或密码错误。");
				ftpClient.disconnect();
			} else {
				logger.info("FTP连接成功。");
			}
		} catch (SocketException e) {
			e.printStackTrace();
			logger.info("FTP的IP地址可能错误，请正确配置。");
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("FTP的端口错误,请正确配置。");
		}
		return ftpClient;
	}

	/**
	 * 本地上传文件到FTP服务器
	 * 
	 * @param ftpPath
	 *            远程文件路径FTP
	 * @throws IOException
	 */
	public static boolean upload(String ftpPath, String ftpUserName, String ftpPassword,
			String ftpHost, int ftpPort, File localFile) {
		FTPClient ftpClient = null;
		logger.info("开始上传文件到FTP.");
		InputStream in =  null;
		OutputStream output = null;
		boolean result = false;
		boolean is_timeout = false;
		try {
			ftpClient = FTPUtil.getFTPClient(ftpHost, ftpPassword, ftpUserName,
					ftpPort);
			// 设置PassiveMode传输
			ftpClient.enterLocalPassiveMode();
			ftpClient.setControlEncoding("UTF-8");
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			
			in =  new FileInputStream(localFile);
			
			//创建目录
			createDirectory(ftpPath, ftpClient);
			
			boolean dflag = ftpClient.changeWorkingDirectory(ftpPath);
			System.out.println(dflag);
			
			output = ftpClient.storeFileStream(localFile.getName());
			System.out.println("totalsize = " + in.available());
			boolean flag = true;
			try {
				byte[] buffer = new byte[1024 * 20];
				int n = 0;
				while ((n = in.read(buffer)) != -1) {
					output.write(buffer, 0, n);//上传到FTP
				}
				output.flush();
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
			}
			if(dflag && flag) {
				logger.info("上传文件" + localFile.getName() + "到FTP成功!");
				result = true;
			} else {
				logger.info("上传文件" + localFile.getName() + "到FTP失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			is_timeout = true;//超时标识
		} finally {
			if(output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(is_timeout) {//超时
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {//正常上传
				try {
					if (ftpClient.completePendingCommand()) {
						ftpClient.disconnect();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			localFile.delete();
		}
		return result;
	}
	
	/**
	 * 本地上传文件到FTP服务器，附加缩略图
	 * 
	 * @param ftpPath
	 *            远程文件路径FTP
	 * @throws IOException
	 */
	public static boolean uploadMulty(String ftpPath, String ftpUserName, String ftpPassword,
			String ftpHost, int ftpPort, File localFile, File thumbFile) {
		FTPClient ftpClient = null;
		logger.info("开始上传文件到FTP.");
		InputStream in =  null;
		BufferedInputStream bis = null;
		
		InputStream inThumb = null;
		BufferedInputStream bisThumb = null;
		OutputStream out = null;
		OutputStream thumbOut = null;
		boolean result = false;
		boolean is_timeout = false;
		try {
			ftpClient = FTPUtil.getFTPClient(ftpHost, ftpPassword, ftpUserName,
					ftpPort);
			// 设置PassiveMode传输
			ftpClient.enterLocalPassiveMode();
			ftpClient.setControlEncoding("UTF-8");
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			//缓冲区,20K
			ftpClient.setBufferSize(1024 * 20);
			
			in =  new FileInputStream(localFile);
			
			//创建目录
			createDirectory(ftpPath, ftpClient);
			//切换目录
			boolean dflag = ftpClient.changeWorkingDirectory(ftpPath);
			System.out.println(dflag);
			
			//上传缩略图
//			inThumb = new FileInputStream(thumbFile);
//			thumbOut = ftpClient.storeFileStream(thumbFile.getName());
//			System.out.println("thumb totalsize = " + inThumb.available());
//			boolean thumbFlag = true;
//			try {
//				byte[] buffer = new byte[1024 * 20];
//				int n = 0;
//				while ((n = inThumb.read(buffer)) != -1) {
//					thumbOut.write(buffer, 0, n);//上传到FTP
//				}
//				thumbOut.flush();
//			} catch (Exception e) {
//				e.printStackTrace();
//				thumbFlag = false;
//			}

			//上传大图
			out = ftpClient.storeFileStream(localFile.getName());
			System.out.println("totalsize = " + in.available());
			boolean flag = true;
			try {
				byte[] buffer = new byte[1024 * 20];
				int n = 0;
				while ((n = in.read(buffer)) != -1) {
					out.write(buffer, 0, n);//上传到FTP
				}
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
			}
			
//			if(dflag && flag && thumbFlag) {
			if(dflag && flag) {
				logger.info("上传文件" + localFile.getName() + "到FTP成功!");
				result = true;
			} else {
				logger.info("上传文件" + localFile.getName() + "到FTP失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			is_timeout = true;
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(thumbOut != null) {
				try {
					thumbOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(bisThumb != null) {
				try {
					bisThumb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inThumb != null) {
				try {
					inThumb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(is_timeout) {//超时
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {//正常上传
				try {
					if (ftpClient.completePendingCommand()) {
						ftpClient.disconnect();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			localFile.delete();
			thumbFile.delete();
		}
		return result;
	}
	
	/**
	 * 删除目录
	 */
	public static void deleteDirectory(String ftpPath,String ftpHost, String ftpPassword,
			String ftpUserName, int ftpPort) {
		try {
			FTPClient ftpClient = getFTPClient(ftpHost, ftpPassword,
					ftpUserName, ftpPort);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setControlEncoding("UTF-8");
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			boolean flag = ftpClient.removeDirectory(ftpPath);
			System.out.println(flag);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * 去 服务器的FTP路径下上读取文件
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public String readConfigFileForFTP(String ftpUserName, String ftpPassword,
			String ftpPath, String ftpHost, int ftpPort, String fileName) {
		StringBuffer resultBuffer = new StringBuffer();
		InputStream in = null;
		FTPClient ftpClient = null;
		logger.info("开始读取绝对路径" + ftpPath + "文件!");
		try {
			ftpClient = FTPUtil.getFTPClient(ftpHost, ftpPassword, ftpUserName,
					ftpPort);
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpPath);
			in = ftpClient.retrieveFileStream(fileName);
		} catch (FileNotFoundException e) {
			logger.error("没有找到" + ftpPath + "文件");
			e.printStackTrace();
			return "下载配置文件失败，请联系管理员.";
		} catch (SocketException e) {
			logger.error("连接FTP失败.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("文件读取错误。");
			e.printStackTrace();
			return "配置文件读取失败，请联系管理员.";
		}
		if (in != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String data = null;
			try {
				while ((data = br.readLine()) != null) {
					resultBuffer.append(data + "\n");
				}
			} catch (IOException e) {
				logger.error("文件读取错误。");
				e.printStackTrace();
				return "配置文件读取失败，请联系管理员.";
			} finally {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.error("in为空，不能读取。");
			return "配置文件读取失败，请联系管理员.";
		}
		return resultBuffer.toString();
	}
	
	/**
	 * 遍历服务器的某一目录
	*
	 * @param pathname
	 * FTP服务器的路径，如/dir1/dir2/
	 * @throws IOException
	*/
	public void traverseDirectory(String pathname, FTPClient client)
			throws IOException {
		client.changeWorkingDirectory(pathname);
		FTPFile[] fileList = client.listFiles(pathname);
		traverse(fileList,client);
	}
	
	/**
	 * 创建目录,(远程目录格式必须是/aaa/bbb/ccc/ddd/的形式)
	*
	 * @param remote
	 * 远程目录路径
	 * @throws IOException
	*/
	public static boolean createDirectory(String remote, FTPClient client)
			throws IOException {
		int start = 0, end = 0;
		start = remote.startsWith("/") ? 1 : 0;
		end = remote.indexOf("/", start);
		for (; start < end;) {
			String subDirectory = remote.substring(start, end);
			if (!client.changeWorkingDirectory(new String(subDirectory
					.getBytes("UTF-8"), "ISO-8859-1"))) {
				// 目录不存在则在服务器端创建目录
				if (!client.makeDirectory(new String(subDirectory
						.getBytes("UTF-8"), "ISO-8859-1"))) {
					return false;
				} else {
					client.changeWorkingDirectory(new String(subDirectory
							.getBytes("UTF-8"), "ISO-8859-1"));
				}
			}
			start = end + 1;
			end = remote.indexOf("/", start);
		}
		return true;
	}

	/**
	 * 遍历FTP服务器的目录
	 * 
	 * @param ftpClient
	 *            FTPClient
	 * @param fileList
	 *            文件列表
	 * @throws IOException
	 */
	private void traverse(FTPFile[] fileList,FTPClient client) throws IOException {
		String tempDir = null;
		for (FTPFile file : fileList) {
			if (file.getName().equals(".") || file.getName().equals("..")) {
				continue;
			}
			if (file.isDirectory()) {
				System.out.println("***************** Directory:"
						+ file.getName() + "Start **************");
				tempDir = client.printWorkingDirectory();
				tempDir += file.getName();
				client.changeWorkingDirectory(new String(tempDir
						.getBytes("UTF-8"), "ISO-8859-1"));
				traverse(client.listFiles(tempDir),client);
				// 不是目录，是文件的情况
				System.out.println("***************** Directory:"
						+ file.getName() + "End **************n");
			} else {
				System.out.println("FileName:" + file.getName() + "FileSize:"
						+ file.getSize() / (1024) + "KB" + "CreateTime:"
						+ file.getTimestamp().getTime());
			}
		}
		// 遍历完当前目录，就要将工作目录改为当前目录的父目录
		client.changeToParentDirectory();
	}
	
	/**
	 * 解析FTP信息文件
	 * @author:tjhua
	 * @date:2015-10-26 下午7:00:32
	 * <p>description:</p>
	 * @return
	 */
	public static FTPInfo readFTPInfo() {
		
		FTPInfo ftpInfo = new FTPInfo();
		
		String app_ftpUserName = "";
		String app_ftpPassword = "";
		String app_ftpHost = "";
		String app_ftpPort = "";
		String app_ftpPath = "";
		String app_writeTempFilePath = "";
		
		InputStream in = null;
		if(StringUtils.equals(ConstConfig.ENV, "development")) {
			in = FTPUtil.class.getClassLoader().getResourceAsStream("ftp_development.properties");
		} else if(StringUtils.equals(ConstConfig.ENV, "test")) {
			in = FTPUtil.class.getClassLoader().getResourceAsStream("ftp_test.properties");
		} else if(StringUtils.equals(ConstConfig.ENV, "product")) {
			in = FTPUtil.class.getClassLoader().getResourceAsStream("ftp_product.properties");
		} else {
			in = FTPUtil.class.getClassLoader().getResourceAsStream("ftp_development.properties");
		}
		if (in == null) {
			logger.info("配置文件ftp.properties读取失败");
		} else {
			Properties properties = new Properties();
			try {
				properties.load(in);
				//appmanager配置信息
				app_ftpUserName = properties.getProperty("app_ftpUserName");
				app_ftpPassword = properties.getProperty("app_ftpPassword");
				app_ftpHost = properties.getProperty("app_ftpHost");
				app_ftpPort = properties.getProperty("app_ftpPort");
				app_ftpPath = properties.getProperty("app_ftpPath");
				app_writeTempFilePath = properties.getProperty("app_writeTempFilePath");
				
				ftpInfo.setFtpUserName(app_ftpUserName);
				ftpInfo.setFtpPassword(app_ftpPassword);
				ftpInfo.setFtpHost(app_ftpHost);
				ftpInfo.setFtpPort(app_ftpPort);
				ftpInfo.setFtpPath(app_ftpPath);
				ftpInfo.setWriteTempFilePath(app_writeTempFilePath);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ftpInfo;
	}
	
	/**
	 * 获取FTP实例
	 * @author:tjhua
	 * @date:2015-10-26 下午7:00:49
	 * <p>description:</p>
	 * @return
	 */
	public static FTPInfo getFTPInstance() {
		if(ftpInfo == null) {
			ftpInfo = readFTPInfo();
		}
		return ftpInfo;
	}
}
