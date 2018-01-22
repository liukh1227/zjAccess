package com.zj.access.utils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.zj.base.common.Constant;
import com.zj.base.entity.base.dto.BaseObjDto;
import com.zj.common.utils.JAXBUtil;
import com.zj.entity.version.LeasingSideVersion;
import com.zj.entity.version.LesseeSideVersion;
import com.zj.entity.version.Update;

/**
 * @author liukh
 * @date 2016-12-6 上午11:30:58
 */
public class APPVersionUtil {
	
	private static final Logger log = Logger.getLogger(APPVersionUtil.class);
	/**
	 * 获取所有的版本更新信息
	 * @author liukh
	 * @date 2016-12-6 下午3:10:42
	 * @return
	 */
	public static HashMap<String,String> getUpdateVersion(String  clientType){
		HashMap<String,String> versionMap = null;
		InputStream in = null;
		ByteArrayOutputStream baos = null;
		BaseObjDto<Object> baseObjoDto = null;
		String jsonStr = null;
		
		try {
			versionMap = new HashMap<String,String>();
			if(clientType.equals(Constant.COMPANY_BUSSINESS_LEASINGSID)){
				in = APPVersionUtil.class.getClassLoader().getResourceAsStream("aPPVersionLeasingSide.xml");
				if (in == null) {
					log.info("get aPPVersionLeasingSide.xml to InputStream is null !");
			     	return null;
				}
				baos = new ByteArrayOutputStream();
				int index = -1;
				while ((index = in.read()) != -1) {
					baos.write(index);
				}
				byte[] bytes = baos.toByteArray();
				String content = new String(bytes, "UTF-8");
			
				LeasingSideVersion leasingSideVersion = JAXBUtil.toObject(LeasingSideVersion.class, content);
				if (leasingSideVersion == null) {
					log.info("set aPPVersionLeasingSide.xml to APPVersion is null !");
					return null;
				}
				if(leasingSideVersion.getLeasingSidelist()!= null && leasingSideVersion.getLeasingSidelist().size()>0){
					StringBuffer sbf = null;
					for(int indexx = 0;indexx < leasingSideVersion.getLeasingSidelist().size();indexx++){
						Update updateVersion = leasingSideVersion.getLeasingSidelist().get(indexx);
						if(updateVersion.getChannel()!=null){
							sbf = new StringBuffer();
							baseObjoDto = new BaseObjDto<Object>();
							baseObjoDto.setRcode(0);
							baseObjoDto.setRinfo("请求成功!");
							baseObjoDto.setData(updateVersion);
							jsonStr = JSON.toJSONString(baseObjoDto);
							sbf.append(Constant.COMPANY_BUSSINESS_LEASINGSID);
							sbf.append("_");
							sbf.append(updateVersion.getChannel());
							versionMap.put(sbf.toString(), jsonStr);
						}
						
						
					}
				}
			}else{
				in = APPVersionUtil.class.getClassLoader().getResourceAsStream("aPPVersionLesseeSide.xml");
				if (in == null) {
					log.info("get aPPVersionLesseeSide.xml to InputStream is null !");
			     	return null;
				}
				baos = new ByteArrayOutputStream();
				int index = -1;
				while ((index = in.read()) != -1) {
					baos.write(index);
				}
				byte[] bytes = baos.toByteArray();
				String content = new String(bytes, "UTF-8");
			
				LesseeSideVersion lesseeSideVersion = JAXBUtil.toObject(LesseeSideVersion.class, content);
				if (lesseeSideVersion == null) {
					log.info("set aPPVersionLesseeSide.xml to APPVersion is null !");
					return null;
				}
				if(lesseeSideVersion.getLesseeSideList()!= null && lesseeSideVersion.getLesseeSideList().size()>0){
					StringBuffer sbf = null;
					for(int indexx = 0;indexx < lesseeSideVersion.getLesseeSideList().size();indexx++){
						Update updateVersion = lesseeSideVersion.getLesseeSideList().get(indexx);
						if(updateVersion.getChannel()!=null){
							sbf = new StringBuffer();
							baseObjoDto = new BaseObjDto<Object>();
							baseObjoDto.setRcode(0);
							baseObjoDto.setRinfo("请求成功!");
							baseObjoDto.setData(updateVersion);
							jsonStr = JSON.toJSONString(baseObjoDto);
							sbf.append(Constant.COMPANY_BUSSINESS_LESSEESIDE);
							sbf.append("_");
							sbf.append(updateVersion.getChannel());
							versionMap.put(sbf.toString(), jsonStr);
						}
						
						
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return  versionMap;
	}
	
	public static String getVersionMessage(String channel,String clientType ){
		BaseObjDto<Object> baseObjDto = new BaseObjDto<Object>();
		StringBuffer sbf = new StringBuffer();
		sbf.append(clientType);
		sbf.append("_");
		sbf.append(channel);
		
		Map<String,String> allVersionMap = getUpdateVersion(clientType);
		if(allVersionMap == null ){
			baseObjDto.setRcode(21);
			baseObjDto.setRinfo("Prase aPPVersion Exption !");
			return baseObjDto.toString();
		}else if(!allVersionMap.containsKey(sbf.toString())){
			baseObjDto.setRcode(21);
			baseObjDto.setRinfo("No Have "+sbf.toString()+" update version information !");
			return baseObjDto.toString();
		}
		return allVersionMap.get(sbf.toString());
		
	}
}
