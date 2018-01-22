package com.zj.base.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import com.zj.base.common.Constant;

/**
 * http post请求帮助类
 * 
 * @author tanjianhua
 * @date 2016-9-13 下午2:24:14
 */
public class HttpClientPost {

    private final static Logger logger = Logger.getLogger(HttpClientPost.class);

    /**
     * 以post的方式请求url,根据params参数替换url中的参数{xxx}
     * 
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String post(String url, String jsonString) throws Exception {
	CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	url = url.replaceAll(" ", "%20"); // 替换url中的空格，否则无法执行
	HttpPost httppost = new HttpPost(url);
	// 配置请求的5s超时设置
	RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Constant.HTTP_REQUEST_TIMEOUT)
		.setConnectTimeout(Constant.HTTP_CONNECT_TIMEOUT).setSocketTimeout(Constant.HTTP_SOCKET_TIMEOUT).build();
	httppost.setConfig(requestConfig);
	StringEntity jsonentity = new StringEntity(jsonString, Constant.HTTP_ENCODING);// 解决中文乱码问题
	jsonentity.setContentEncoding(Constant.HTTP_ENCODING);
	jsonentity.setContentType(Constant.HTTP_CONTENT_TYPE);
	httppost.setEntity(jsonentity);
	CloseableHttpResponse response = httpclient.execute(httppost);
	HttpEntity entity = response.getEntity();
	String jsonStr = EntityUtils.toString(entity, Constant.HTTP_ENCODING);
	logger.info("response.toString()============" + jsonStr);
	response.close();
	httpclient.close();
	httppost.releaseConnection();

	return jsonStr;
    }

    /**
     * 以post的方式请求url,根据params参数替换url中的参数{xxx}
     * 
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String postReplaceParam(String url, String jsonString, Map<String, String> params) throws Exception {
	CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	Set<String> keySet = params.keySet();
	for (String key : keySet) {
	    String str = "{" + key + "}";
	    url = url.replace(str, params.get(key) == null ? "null" : params.get(key));
	}
	url = url.replaceAll(" ", "%20"); // 替换url中的空格，否则无法执行
	HttpPost httppost = new HttpPost(url);
	// 配置请求的5s超时设置
	RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Constant.HTTP_REQUEST_TIMEOUT)
		.setConnectTimeout(Constant.HTTP_CONNECT_TIMEOUT).setSocketTimeout(Constant.HTTP_SOCKET_TIMEOUT).build();
	httppost.setConfig(requestConfig);
	StringEntity jsonentity = new StringEntity(jsonString, Constant.HTTP_ENCODING);// 解决中文乱码问题
	jsonentity.setContentEncoding(Constant.HTTP_ENCODING);
	jsonentity.setContentType(Constant.HTTP_CONTENT_TYPE);
	httppost.setEntity(jsonentity);
	CloseableHttpResponse response = httpclient.execute(httppost);
	HttpEntity entity = response.getEntity();
	String jsonStr = EntityUtils.toString(entity, Constant.HTTP_ENCODING);
	logger.info("response.toString()============" + jsonStr);
	response.close();
	httpclient.close();
	httppost.releaseConnection();

	return jsonStr;
    }

    /**
     * 以post的方式请求url,根据params参数替换url中的参数{xxx}
     * 
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String post(String url, Map<String, String> addParams, Map<String, String> params) throws Exception {
	CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	Set<String> keySet = params.keySet();
	Set<String> addKeySet = addParams.keySet();
	for (String key : keySet) {
	    String str = "{" + key + "}";
	    url = url.replace(str, params.get(key) == null ? "null" : params.get(key));
	}
	String paramstr = "";
	for (String key : addKeySet) {
	    paramstr = paramstr == "" ? "?" + key + "=" + addParams.get(key) : paramstr + "&" + key + "=" + addParams.get(key);
	}
	url = url + paramstr;

	url = url.replaceAll(" ", "%20"); // 替换url中的空格，否则无法执行
	HttpPost httppost = new HttpPost(url);
	// 配置请求的5s超时设置
	RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Constant.HTTP_REQUEST_TIMEOUT)
		.setConnectTimeout(Constant.HTTP_CONNECT_TIMEOUT).setSocketTimeout(Constant.HTTP_SOCKET_TIMEOUT).build();
	httppost.setConfig(requestConfig);
	StringEntity jsonentity = new StringEntity(Constant.HTTP_ENCODING);// 解决中文乱码问题
	jsonentity.setContentEncoding(Constant.HTTP_ENCODING);
	jsonentity.setContentType(Constant.HTTP_CONTENT_TYPE);
	httppost.setEntity(jsonentity);
	CloseableHttpResponse response = httpclient.execute(httppost);
	HttpEntity entity = response.getEntity();
	String jsonStr = EntityUtils.toString(entity, Constant.HTTP_ENCODING);
	logger.info("response.toString()============" + jsonStr);
	response.close();
	httpclient.close();
	httppost.releaseConnection();

	return jsonStr;
    }

    /**
     * 以post的方式请求url,根据params参数add url中的参数{xxx}
     * 
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String post(String url, Map<String, String> params) throws Exception {
	CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	Set<String> keySet = params.keySet();
	String paramstr = "";
	for (String key : keySet) {
	    paramstr = paramstr == "" ? "?" + key + "=" + params.get(key) : paramstr + "&" + key + "=" + params.get(key);
	}
	url = url + paramstr;

	url = url.replaceAll(" ", "%20"); // 替换url中的空格，否则无法执行
	HttpPost httppost = new HttpPost(url);
	// 配置请求的5s超时设置
	RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Constant.HTTP_REQUEST_TIMEOUT)
		.setConnectTimeout(Constant.HTTP_CONNECT_TIMEOUT).setSocketTimeout(Constant.HTTP_SOCKET_TIMEOUT).build();
	httppost.setConfig(requestConfig);
	StringEntity jsonentity = new StringEntity(Constant.HTTP_ENCODING);// 解决中文乱码问题
	jsonentity.setContentEncoding(Constant.HTTP_ENCODING);
	jsonentity.setContentType(Constant.HTTP_CONTENT_TYPE);
	httppost.setEntity(jsonentity);
	CloseableHttpResponse response = httpclient.execute(httppost);
	HttpEntity entity = response.getEntity();
	String jsonStr = EntityUtils.toString(entity, Constant.HTTP_ENCODING);
	logger.info("response.toString()============" + jsonStr);
	response.close();
	httpclient.close();
	httppost.releaseConnection();

	return jsonStr;
    }

    /**
     * 发送实体类
     * 
     * @author:tjhua
     * @date:2016-3-24 下午2:22:31
     *                 <p>
     *                 description:
     *                 </p>
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String postStringEntity(String url, Map<String, String> params) throws Exception {
	CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	Set<String> keySet = params.keySet();
	HttpPost httppost = new HttpPost(url);

	List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	for (String key : keySet) {
	    nvps.add(new BasicNameValuePair(key, params.get(key)));
	}
	httppost.setEntity(new UrlEncodedFormEntity(nvps, Constant.HTTP_ENCODING));

	// 配置请求的5s超时设置
	RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Constant.HTTP_REQUEST_TIMEOUT)
		.setConnectTimeout(Constant.HTTP_CONNECT_TIMEOUT).setSocketTimeout(Constant.HTTP_SOCKET_TIMEOUT).build();
	httppost.setConfig(requestConfig);
	CloseableHttpResponse response = httpclient.execute(httppost);
	HttpEntity entity = response.getEntity();
	String jsonStr = EntityUtils.toString(entity, Constant.HTTP_ENCODING);
	logger.info("response.toString()============" + jsonStr);
	response.close();
	httpclient.close();
	httppost.releaseConnection();

	return jsonStr;
    }

    public static String post(String url) throws Exception {
	CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	url = url.replaceAll(" ", "%20"); // 替换url中的空格，否则无法执行
	HttpPost httppost = new HttpPost(url);
	// 配置请求的5s超时设置
	RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Constant.HTTP_REQUEST_TIMEOUT)
		.setConnectTimeout(Constant.HTTP_CONNECT_TIMEOUT).setSocketTimeout(Constant.HTTP_SOCKET_TIMEOUT).build();
	httppost.setConfig(requestConfig);
	StringEntity jsonentity = new StringEntity("", Constant.HTTP_ENCODING);// 解决中文乱码问题
	jsonentity.setContentEncoding(Constant.HTTP_ENCODING);
	jsonentity.setContentType(Constant.HTTP_CONTENT_TYPE);
	httppost.setEntity(jsonentity);
	CloseableHttpResponse response = httpclient.execute(httppost);
	HttpEntity entity = response.getEntity();
	String jsonStr = EntityUtils.toString(entity, Constant.HTTP_ENCODING);
	logger.info("response.toString()============" + jsonStr);
	response.close();
	httpclient.close();
	httppost.releaseConnection();

	return jsonStr;
    }

    /**
     * 以post的方式请求url
     * 
     * @param url
     * @param params
     * @return
     * @throws Exception
     */

    public static String postByNameValuePair(String url, List<NameValuePair> nvps) throws Exception {
	CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	url = url.replaceAll(" ", "%20"); // 替换url中的空格，否则无法执行
	HttpPost httppost = new HttpPost(url);
	// 配置请求的5s超时设置
	RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Constant.HTTP_REQUEST_TIMEOUT)
		.setConnectTimeout(Constant.HTTP_CONNECT_TIMEOUT).setSocketTimeout(Constant.HTTP_SOCKET_TIMEOUT).build();
	httppost.setConfig(requestConfig);
	httppost.setEntity(new UrlEncodedFormEntity(nvps, Constant.HTTP_ENCODING));
	CloseableHttpResponse response = httpclient.execute(httppost);
	HttpEntity entity = response.getEntity();
	String jsonStr = EntityUtils.toString(entity, Constant.HTTP_ENCODING);
	logger.info("response.toString()============" + jsonStr);
	response.close();
	httpclient.close();
	httppost.releaseConnection();

	return jsonStr;
    }
}
