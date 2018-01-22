package com.zj.access.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.Callback.CalbackBodyType;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.zj.base.common.ConstConfig;
import com.zj.entity.unit.FTPAlyOSSInfo;
import com.zj.entity.unit.FTPAlyOSSPutRet;
import com.zj.entity.unit.FTPQiNiuPutRet;

public class FTPAlyOSSUtil {

	private static Logger logger = Logger.getLogger(FTPAlyOSSUtil.class);
	private static FTPAlyOSSInfo fTPAlyOSSInfo;
	/**
	 * 解析阿里云OSS上传配置文件
	 * @author liukh
	 * @date 2017-4-16 下午2:22:44
	 * @return
	 */
		public static FTPAlyOSSInfo readFTPAlyOSSInfo() {
			
			FTPAlyOSSInfo ftpInfo = new FTPAlyOSSInfo();
			InputStream in = null;
			if(StringUtils.equals(ConstConfig.ENV, "development")) {
				in = FTPAlyOSSUtil.class.getClassLoader().getResourceAsStream("ftp_qiniu_development.properties");
			} else if(StringUtils.equals(ConstConfig.ENV, "test")) {
				in = FTPAlyOSSUtil.class.getClassLoader().getResourceAsStream("ftp_qiniu_test.properties");
			} else if(StringUtils.equals(ConstConfig.ENV, "product")) {
				in = FTPAlyOSSUtil.class.getClassLoader().getResourceAsStream("ftp_qiniu_product.properties");
			} else {
				in = FTPAlyOSSUtil.class.getClassLoader().getResourceAsStream("ftp_qiniu_development.properties");
			}
			if (in == null) {
				logger.info("配置文件ftpqiniu.properties读取失败");
			} else {
				Properties properties = new Properties();
				try {
					properties.load(in);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return ftpInfo;
		}
		
		/**
		 * 获取阿里云OSS配置实例
		 * @author liukh
		 * @date 2017-7-30 上午11:16:56
		 * @return
		 */
		public static FTPAlyOSSInfo getFTPInstance() {
			if(fTPAlyOSSInfo == null) {
				fTPAlyOSSInfo = readFTPAlyOSSInfo();
			}
			return fTPAlyOSSInfo;
		}
		
		/**
		 * 上传
		 * @author liukh
		 * @date 2017-4-16 下午2:57:08
		 * @param in 上传的文件
		 * @param accessKey 密钥 AK
		 * @param secretKey 密钥 SK
		 * @param bucket 存储域
		 * @param key 默认不指定key的情况下，以文件内容的hash值作为文件名
		 * @return
		 */
		public static FTPAlyOSSPutRet upload(InputStream in,String endpoint,String accessKeyId,String  accessKeySecret,String bucketName,String key,String callbackUrl){
			FTPAlyOSSPutRet putRet = null;
			OSSClient ossClient = null;
		try{
	
			// 创建OSSClient实例
			 ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			ossClient.putObject(bucketName, key, in);
			//call back 
			  String content = "Hello OSS";
	           PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key,new ByteArrayInputStream(content.getBytes())); 
	            
	            Callback callback = new Callback();
	            callback.setCallbackUrl(callbackUrl);
	            callback.setCallbackHost("oss-cn-hangzhou.aliyuncs.com");
	            callback.setCallbackBody("{\\\"bucket\\\":${bucket},\\\"object\\\":${object},"
	                    + "\\\"mimeType\\\":${mimeType},\\\"size\\\":${size},"
	                    + "\\\"my_var1\\\":${x:code},\\\"my_var2\\\":${x:path}}");
	            callback.setCalbackBodyType(CalbackBodyType.JSON);
	            callback.addCallbackVar("x:var1", "value1");
	            callback.addCallbackVar("x:var2", "value2");
	            putObjectRequest.setCallback(callback);
	            
	            PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
	            byte[] buffer = new byte[1024];
	            int b;
	         //   putObjectResult.getCallbackResponseBody().read(buffer);
	          
	            while((b= putObjectResult.getCallbackResponseBody().read(buffer))!=-1){
	             System.out.println(b);
	            String jsonStr=new String(buffer,"utf-8");
	            System.out.println(jsonStr);
	            }
	          
			putObjectResult.getCallbackResponseBody().close();
			
			
		}catch(OSSException oe){
			System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
		}catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        }catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
        	if(ossClient != null){
        		ossClient.shutdown();
        	}
        	if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
            
        }
			
			return putRet;
			
		}
		
		
		
	
}
