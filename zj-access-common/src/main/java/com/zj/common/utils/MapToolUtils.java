/**
 * 
 */
package com.zj.common.utils;

import java.net.URL;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author liukh
 * @date 2016-11-25 下午4:58:02
 */
public class MapToolUtils {

	 
	/**
	 *  获取位置
	 * @author liukh
	 * @date 2016-11-25 下午4:59:41
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public static String getPosition(Double longitude, Double latitude) {
		// lat 小 log 大
		// 参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)
		String urlString = "http://gc.ditu.aliyun.com/regeocoding?l="
				+ latitude + "," + longitude + "&type=010";
		String res = "";
		try {
			URL url = new URL(urlString);
			java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url
					.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			java.io.BufferedReader in = new java.io.BufferedReader(
					new java.io.InputStreamReader(conn.getInputStream(),
							"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				res += line + "\n";
			}
			in.close();
		} catch (Exception e) {
			System.out.println("error in wapaction,and e is " + e.getMessage());
		}
		JSONObject jsonObject = JSONObject.parseObject(res);
		JSONArray jsonArray = JSONArray.parseArray(jsonObject
				.getString("addrList"));
		JSONObject j_2 = (JSONObject) JSONObject.toJSON(jsonArray.get(0));
		String allAdd = j_2.getString("admName");
		String arr[] = allAdd.split(",");
		JSONObject json = new JSONObject();
		json.put("province", arr.length > 0 ? arr[0] : "");
		json.put("city", arr.length > 1 ? arr[1] : "");
		json.put("area", arr.length > 2 ? arr[2] : "");
		return JSON.toJSONString(json);
	}


	/**
	 * 计算两个经纬度的距离,单位:m
	 * @author liukh
	 * @date 2016-11-25 下午5:00:28
	 * @param long1
	 * @param lat1
	 * @param long2
	 * @param lat2
	 * @return
	 */
	public static double distance(double long1, double lat1, double long2,
			double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
						* Math.cos(lat2) * sb2 * sb2));
		return d;
	}
	/**
	 * 
	 * @author liukh
	 * @date 2016-12-1 上午9:51:49
	 * @param latitue 待测点的纬度
	 * @param longitude 待测点的经度
	 * @param areaLatitude1 纬度范围限制1 
	 * @param areaLatitude2 纬度范围限制2 
	 * @param areaLongitude1 经度限制范围1
	 * @param areaLongitude2 经度范围限制2
	 * @return
	 */
    public static boolean isInArea(double latitue,double longitude,double areaLatitude1,double areaLatitude2,double areaLongitude1,double areaLongitude2){  
        if(isInRange(latitue, areaLatitude1, areaLatitude2)){//如果在纬度的范围内  
            if(areaLongitude1*areaLongitude2>0){//如果都在东半球或者都在西半球  
                if(isInRange(longitude, areaLongitude1, areaLongitude2)){  
                    return true;  
                }else {  
                    return false;  
                }  
            }else {//如果一个在东半球，一个在西半球  
                if(Math.abs(areaLongitude1)+Math.abs(areaLongitude2)<180){//如果跨越0度经线在半圆的范围内  
                    if(isInRange(longitude, areaLongitude1, areaLongitude2)){  
                        return true;  
                    }else {  
                        return false;  
                    }  
                }else{//如果跨越180度经线在半圆范围内  
                    double left = Math.max(areaLongitude1, areaLongitude2);//东半球的经度范围left-180  
                    double right = Math.min(areaLongitude1, areaLongitude2);//西半球的经度范围right-（-180）  
                    if(isInRange(longitude, left, 180)||isInRange(longitude, 0, right)){  
                        return true;  
                    }else {  
                        return false;  
                    }  
                }  
            }  
        }else{  
            return false;  
        }  
    }
    /**
     * 某个点是否在范围之内
     * @author liukh
     * @date 2016-12-1 上午9:51:56
     * @param point
     * @param left
     * @param right
     * @return
     */
    public static boolean isInRange(double point, double left,double right){  
        if(point>=Math.min(left, right)&&point<=Math.max(left, right)){  
            return true;  
        }else {  
            return false;  
        }  
      
} 
}
