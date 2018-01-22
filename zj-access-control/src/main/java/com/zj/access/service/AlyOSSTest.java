package com.zj.access.service;

import java.io.*;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.Callback.CalbackBodyType;
import com.zj.entity.unit.FTPAlyOSSPutRet;

public class AlyOSSTest {
	
	public static void upload(InputStream in,String endpoint,String accessKeyId,String  accessKeySecret,String bucketName,String key,String callbackUrl){
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
	}
	
	public static void upload2(File file,String endpoint,String accessKeyId,String  accessKeySecret,String bucketName,String key,String callbackUrl){
	
		OSSClient ossClient = null;
		InputStream in = null;
		try {
			in = new  FileInputStream(file);
			
			// 创建OSSClient实例
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			ossClient.putObject(bucketName, key, in);
			//文件名  
            String fileName = file.getName(); 
            //文件大小  
            Long fileSize = file.length();  
            ObjectMetadata metadata = new ObjectMetadata();  
            //上传的文件的长度  
            metadata.setContentLength(in.available());    
            //指定该Object被下载时的网页的缓存行为  
            metadata.setCacheControl("no-cache");   
            //指定该Object下设置Header  
            metadata.setHeader("Pragma", "no-cache");    
            //指定该Object被下载时的内容编码格式  
            metadata.setContentEncoding("utf-8");    
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，  
            //如果没有扩展名则填默认值application/octet-stream  
            metadata.setContentType("image/jpeg");    
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）  
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");    
            //上传文件   (上传文件流的形式)  
            PutObjectResult putResult = ossClient.putObject(bucketName,  fileName, in, metadata);  
          //解析结果  
          String  resultStr = putResult.getETag(); 
            System.out.println(resultStr);
		
		
			
			
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
			if(in!= null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(ossClient != null){
				ossClient.shutdown();
			}
			
			}
			
		}
		


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String endpoint = "oss-cn-shenzhen.aliyuncs.com";
		String accessKeyId = "LTAIXrq9lBbxeM80";
		String  accessKeySecret = "Gnd382E5JbqilJvpUCFpWFV4yIPXOz";
		String bucketName = "jy-picture";
		String key = "test";
		String callbackUrl = "http://119.23.237.219:9000";
		File  file = new File ("D://aa.jpg");
		InputStream in;
		try {
			in = new  FileInputStream(file);
		//	AlyOSSTest.upload( in, endpoint, accessKeyId,  accessKeySecret, bucketName, key, callbackUrl);
			AlyOSSTest.upload2( file, endpoint, accessKeyId,  accessKeySecret, bucketName, key, callbackUrl);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			

		
		

	}

}
