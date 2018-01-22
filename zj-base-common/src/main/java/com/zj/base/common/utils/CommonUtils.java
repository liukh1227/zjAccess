package com.zj.base.common.utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 帮助类
 * @author tanjh
 * @date 2016-8-18 下午1:50:18
 */
public class CommonUtils {

	/**
	 * 获得指定长度的随机数
	 * @author:tjhua
	 * @date:2015-8-27 下午3:00:17
	 * <p>description:</p>
	 * @param length	生成字符串的长度
	 * @return
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * 获得指定长度的数字型随机数
	 * @author:tjhua
	 * @date:2015-8-27 下午3:00:17
	 * <p>description:</p>
	 * @param length	生成字符串的长度
	 * @return
	 */
	public static String getRandomNumberString(int length) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	public static synchronized String getId() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + getRandomString(8);
	}

	public static synchronized String getOrderId() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + getRandomNumberString(6);
	}
	
	public static File checkFileExist(File file,String rPath,String suffix) {
		if(file.exists()) {
			file = new File(rPath + getId() + suffix);
		} else {
			return file;
		}
		return checkFileExist(file,rPath,suffix);
	}
	
	public static ValueFilter getValueFilter() {
		ValueFilter filter = new ValueFilter() {
			@Override
			public Object process(Object obj, String s, Object v) {
				if (v == null)
					return "";
				return v;
			}
		};
		return filter;
	}
	
	 /**  
     * 对double数据进行取精度.  
     * @param value  double数据.  
     * @param scale  精度位数(保留的小数位数).  
     * @param roundingMode  精度取值方式.  
     * @return 精度计算后的数据.  
     */  
    public static double round(double value, int scale) {   
        BigDecimal bd = new BigDecimal(Double.toString(value));   
        bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);   
        double d = bd.doubleValue();   
        bd = null;   
        return d;   
    }

    public static int roundToInt(double value, int scale, int scaleType) {   
    	BigDecimal bd = new BigDecimal(Double.toString(value));   
    	bd = bd.setScale(scale, scaleType);   
    	int d = bd.intValue();
    	bd = null;   
    	return d;   
    }
    
     /** 
     * double 相加 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double sum(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.add(bd2).doubleValue(); 
    }

    /**
     * @desc 相加
     * @author tanjianhua
     * @date 2016-12-8 下午2:05:43
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal bigSum(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.add(bd2);
    } 


    /** 
     * double 相减 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double sub(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.subtract(bd2).doubleValue(); 
    }

    /** 
     * double 相减 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static BigDecimal bigSub(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.subtract(bd2); 
    }

    /** 
     * double 乘法 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double mul(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return Double.valueOf(bd1.multiply(bd2).toPlainString()); 
    }

    /**
     * double 乘法 
     * @author tanjianhua
     * @date 2016-10-28 上午10:27:58
     * @param d1
     * @param d2
     * @return
     */
    public static String mulStr(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).toPlainString(); 
    } 

    /** 
     * double 除法 
     * @param d1 
     * @param d2 
     * @param scale 四舍五入 小数点位数 
     * @return 
     */ 
    public static double div(double d1,double d2,int scale){ 
        //  当然在此之前，你要判断分母是否为0，   
        //  为0你可以根据实际需求做相应的处理 

        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.divide 
               (bd2,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
    }
    
    /** 
     * double 除法 
     * @param d1 
     * @param d2 
     * @param scale 四舍五入 小数点位数 
     * @return 
     */ 
    public static double div(double d1,double d2){ 
        //  当然在此之前，你要判断分母是否为0，   
        //  为0你可以根据实际需求做相应的处理 

        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.divide 
               (bd2).doubleValue(); 
    }
    
    /**
	 * 获取当前日期，格式：4 位年＋2位月＋2位日
	 * 
	 * @return yyyymmdd
	 */
	public static String getDate() {
		String strDate = null;

		Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR);
		String strYear = String.valueOf(year);
		strYear = fillChar(strYear, '0', 4, true);
		int month = rightNow.get(Calendar.MONTH) + 1;
		String strMonth = String.valueOf(month);
		strMonth = fillChar(strMonth, '0', 2, true);
		int day = rightNow.get(Calendar.DAY_OF_MONTH);
		String strDay = String.valueOf(day);
		strDay = fillChar(strDay, '0', 2, true);

		strDate = strYear + strMonth + strDay;

		return strDate;
	}

	/**
	 * 获取当前时间，格式：2位时＋2位分
	 * 
	 * @return HHMM
	 */
	public static String getTime() {
		String strTime = null;

		Calendar rightNow = Calendar.getInstance();

		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		String strHour = String.valueOf(hour);
		strHour = fillChar(strHour, '0', 2, true);

		int minute = rightNow.get(Calendar.MINUTE);
		String strMinute = String.valueOf(minute);
		strMinute = fillChar(strMinute, '0', 2, true);
		strTime = strHour + strMinute;
		return strTime;

	}

	/**
	 * 获取当前时间，格式：2位时＋2位分＋2位秒
	 * 
	 * @return HHMMSS
	 */
	public static String getTimeSec() {
		String strTime = null;

		Calendar rightNow = Calendar.getInstance();

		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		String strHour = String.valueOf(hour);
		strHour = fillChar(strHour, '0', 2, true);

		int minute = rightNow.get(Calendar.MINUTE);
		String strMinute = String.valueOf(minute);
		strMinute = fillChar(strMinute, '0', 2, true);

		int second = rightNow.get(Calendar.SECOND);
		String strSecond = String.valueOf(second);
		strSecond = fillChar(strSecond, '0', 2, true);

		strTime = strHour + strMinute + strSecond;
		return strTime;

	}

	/**
	 * 获取当前时间，格式：2位时＋2位分＋2位秒+3毫秒+000
	 * 
	 * @return HHMMSSNNN000
	 */
	public static String getTimeMillisec() {
		String strTime = null;

		Calendar rightNow = Calendar.getInstance();

		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		String strHour = String.valueOf(hour);
		strHour = fillChar(strHour, '0', 2, true);

		int minute = rightNow.get(Calendar.MINUTE);
		String strMinute = String.valueOf(minute);
		strMinute = fillChar(strMinute, '0', 2, true);

		int second = rightNow.get(Calendar.SECOND);
		String strSecond = String.valueOf(second);
		strSecond = fillChar(strSecond, '0', 2, true);

		int msecond = rightNow.get(Calendar.MILLISECOND);
		String mstrSecond = String.valueOf(msecond);
		mstrSecond = fillChar(mstrSecond, '0', 3, true);

		strTime = strHour + strMinute + strSecond + mstrSecond;
		return strTime;

	}
	
	
	/**
	 * 获取当前时间，格式：2位时＋2位分＋2位秒+3毫秒+000
	 * 
	 * @return HHMMSSNNN
	 */
	public static String getTimeMillisec2() {
		String strTime = null;

		Calendar rightNow = Calendar.getInstance();

		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		String strHour = String.valueOf(hour);
		strHour = fillChar(strHour, '0', 2, true);

		int minute = rightNow.get(Calendar.MINUTE);
		String strMinute = String.valueOf(minute);
		strMinute = fillChar(strMinute, '0', 2, true);

		int second = rightNow.get(Calendar.SECOND);
		String strSecond = String.valueOf(second);
		strSecond = fillChar(strSecond, '0', 2, true);

		int msecond = rightNow.get(Calendar.MILLISECOND);
		String mstrSecond = String.valueOf(msecond);
		mstrSecond = fillChar(mstrSecond, '0', 3, true);

		strTime = strHour + strMinute + strSecond + mstrSecond;
		return strTime;

	}

	/**
	 * 获取当前日期时间，格式：4位年＋2位月＋2位日+2位时＋2位分
	 * 
	 * @return YYYYMMDDHHMM
	 */
	public static String getDateTime() {
		return getDate() + getTime();
	}
	
	/**
	 * date转string
	 * @author:tjhua
	 * @date:2016-4-29
	 * <p>description:</p>
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDateString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String str = "";
		try {
			str = sdf.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 获取当前日期时间，格式：4位年＋2位月＋2位日+2位时＋2位分＋2位秒
	 * 
	 * @return YYYYMMDDHHMMSS
	 */
	public static String getDateTime2() {
		return getDate() + getTimeSec();
	}
	
	/**
	 * 获取当前日期时间，格式：4位年＋2位月＋2位日+2位时＋2位分＋2位秒 + 3位毫秒
	 * 
	 * @return yyyyMMddHHmmssSSS
	 */
	public static String getDateTime3() {
		return getDate() + getTimeSec();
	}
	
	/**
	 * 获取当前日期时间，格式：yyyy-MM-dd HH:mm:ss
	 * @author:tjhua
	 * @date:2016-2-26 上午10:35:46
	 * <p>description:</p>
	 * @return
	 */
	public static String getDateTime4() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	/**
	 * 获取当前日期时间，格式：MM
	 * @author:tjhua
	 * @date:2016-3-21 下午3:01:02
	 * <p>description:</p>
	 * @return
	 */
	public static String getDateTime5() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return sdf.format(new Date());
	}

	/**
	 * 填充函数，可左/右补字符
	 * 
	 * @param sSource
	 *            需填充的源String
	 * @param ch
	 *            填充的字符
	 * @param nLen
	 *            填充后长度
	 * @param bLeft
	 *            左填充为true，右填充为false
	 * @return 填充后String
	 */
	public static String fillChar(String sSource, char ch, int nLen,
			boolean bLeft) {
		int nSrcLen = sSource.getBytes().length;

		if (nSrcLen <= nLen) { // 左填充
			if (bLeft)
				for (int i = 0; i < (nLen - nSrcLen); i++)
					sSource = ch + sSource;
			else
				// 右填充
				for (int i = 0; i < (nLen - nSrcLen); i++)
					sSource += ch;
		}

		return (sSource);
	}

	/**
	 * 文件复制
	 * 
	 * @param source
	 *            源文件
	 * @param dest
	 *            目标文件
	 */
	public static void fileCopy(String source, String dest) // 文件拷贝
	{
		try {
			File destFile = new File(dest);
			if (destFile.exists()) {
				for (int i = 0; i < 3; i++) {
					if (!destFile.delete()) {
						System.out.println("[" + CommonUtils.getDateTime2()
								+ "]删除原文件失败！");
						i += 1;
					} else {
						System.out.println("[" + CommonUtils.getDateTime2()
								+ "]删除原文件成功！");
						break;
					}
				}
			}
			File srcFile = new File(source);
			long fileLen = srcFile.length();
			destFile = null;
			InputStream is = new FileInputStream(srcFile);
			PrintWriter writer = new PrintWriter(new BufferedWriter(
					new FileWriter(dest)));

			byte[] buf = new byte[(int) fileLen];

			is.read(buf, 0, (int) fileLen);
			String str = new String(buf);

			writer.println(str.trim());
			is.close();
			writer.close();

		} catch (IOException e) {
			// mypage.FcfeMain.wR(mypage.FcfeMain.SIM_APP_ERP, e);
			// mypage.FcfeMain.wR(mypage.FcfeMain.SIM_APP_ERP,
			// "复制发送文件失败！！");
			e.printStackTrace();
		}

	}

	/**
	 * 读文件到String
	 * 
	 * @param source
	 *            源文件
	 * @return 文件内容String
	 */
	public static String fileToStr(String source) {
		String dest = "";
		try {
			File sourceFile = new File(source);
			InputStream is = new FileInputStream(sourceFile);
			long fileLen = sourceFile.length();
			byte[] rbytes = new byte[(int) fileLen];
			is.read(rbytes, 0, (int) fileLen);
			dest = new String(rbytes, 0, (int) fileLen);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			// mypage.FcfeMain.wR(mypage.FcfeMain.SIM_APP_ERP, "文件转换为字符串时失败！！");
		}
		return dest;

	}

	/**
	 * 写入String到指定文件中
	 * 
	 * @param source
	 * @param destFile
	 */
	public static void str2File(String source, String destFile) {
		try {
			File dest = new File(destFile);
			if (dest.exists()) {
				dest.delete();
			}
			OutputStream os = new FileOutputStream(dest);
			os.write(source.getBytes());
			os.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * 检查输入的字符是否有xml的转义字符，如果有，则改成原字符
	 * 
	 * @param s
	 * @return
	 */
	public static String xmlFormat(String s) {
		int idx = 0;
		int sLength = s.length();
		int offset = 0;
		String subS = null;
		String newS = "";
		do {
			idx = s.indexOf("&lt;", offset);
			if (idx == -1) {
				newS = s;
				break;
			}
			subS = s.substring(offset, idx);
			newS = newS + subS + "<";
			offset = idx + 1;
			System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝执行了一次左中括号转义＝＝＝＝＝＝＝＝＝＝＝");
		} while (idx != -1);
		s = newS;
		idx = 0;
		sLength = s.length();
		offset = 0;
		subS = null;
		newS = "";
		do {
			idx = s.indexOf("&gt;", offset);
			if (idx == -1) {
				newS = s;
				break;
			}
			subS = s.substring(offset, idx);
			newS = newS + subS + ">";
			offset = idx + 1;
			System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝执行了一次右中括号转义＝＝＝＝＝＝＝＝＝＝＝");
		} while (idx != -1);
		s = newS;
		idx = 0;
		sLength = s.length();
		offset = 0;
		subS = null;
		newS = "";
		do {
			idx = s.indexOf("&amp;", offset);
			if (idx == -1) {
				newS = s;
				break;
			}
			subS = s.substring(offset, idx);
			newS = newS + subS + "&";
			offset = idx + 1;
			System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝执行了一次\"&\"号转义＝＝＝＝＝＝＝＝＝＝＝");
		} while (idx != -1);
		return newS;
	}

	/**
	 * 检查输入的字符是否有xml不可识别的字符，如果有，则改成转义字符
	 * 
	 * @param s
	 *            xml数据源
	 * @return 转义后xml
	 */
	public static String xmlFormat2(String s) {
		int idx = 0;
		int sLength = s.length();
		int offset = 0;
		String subS = null;
		String newS = "";
		do {
			idx = s.indexOf("<", offset);
			if (idx == -1) {
				newS = s;
				break;
			}
			subS = s.substring(offset, idx);
			newS = newS + subS + "&lt;";
			offset = idx + 1;
			System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝执行了一次左中括号转义＝＝＝＝＝＝＝＝＝＝＝");
		} while (idx != -1);
		s = newS;
		idx = 0;
		sLength = s.length();
		offset = 0;
		subS = null;
		newS = "";
		do {
			idx = s.indexOf(">", offset);
			if (idx == -1) {
				newS = s;
				break;
			}
			subS = s.substring(offset, idx);
			newS = newS + subS + "&gt;";
			offset = idx + 1;
			System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝执行了一次右中括号转义＝＝＝＝＝＝＝＝＝＝＝");
		} while (idx != -1);
		s = newS;
		idx = 0;
		sLength = s.length();
		offset = 0;
		subS = null;
		newS = "";
		do {
			idx = s.indexOf("&", offset);
			if (idx == -1) {
				newS = s;
				break;
			}
			subS = s.substring(offset, idx);
			newS = newS + subS + "&amp;";
			offset = idx + 1;
			System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝执行了一次\"&\"号转义＝＝＝＝＝＝＝＝＝＝＝");
		} while (idx != -1);
		return newS;
	}

	/**
	 * 取得Visa报文所需格式的日期
	 * 
	 * @return
	 */
	public static String getVisaTime() {
		Calendar rightNow = Calendar.getInstance(); // 日历的基类
		int year = rightNow.get(Calendar.YEAR);
		int month = rightNow.get(Calendar.MONTH) + 1;
		int day = rightNow.get(Calendar.DAY_OF_MONTH);
		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		int minute = rightNow.get(Calendar.MINUTE);
		int second = rightNow.get(Calendar.SECOND);
		String visaTime = Integer.toString(year) + Integer.toString(month)
				+ Integer.toString(day) + " " + Integer.toString(hour) + ":"
				+ Integer.toString(minute) + ":" + Integer.toString(second);
		return visaTime;
	}

	public static Boolean isMobileNo(String str) {
 		Boolean isMobileNo = false;
 		try {
			Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(14[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
			Matcher m = p.matcher(str);
			isMobileNo = m.matches();
		} catch (Exception e) {
			e.printStackTrace();
		}
 		return isMobileNo;
 	}
	
	/**
	 * str转date
	 * @author:tjhua
	 * @date:2016-3-28 下午1:01:35
	 * <p>description:</p>
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String str, String pattern) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 两个日期相差多少天
	 * @author:tjhua
	 * @date:2016-4-29
	 * <p>description:</p>
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(String startDate, String endDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(startDate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(endDate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * @desc 两个日期相差多少分钟
	 * @author tanjianhua
	 * @date 2016-12-29 下午3:37:04
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetweenSecond(Date startDate, Date endDate) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(endDate);
		long time2 = cal.getTimeInMillis();
		long between_seconds = (time2 - time1) / 1000;

		return Integer.parseInt(String.valueOf(between_seconds));
	}

	/**
     * 按指定高度 等比例缩放图片
     * 
     * @param imageFile
     * @param newPath
     * @param newWidth 新图的宽度
     * @throws IOException
     */
    public static void zoomImageScale(File imageFile, String newPath, int newWidth) throws IOException {
         if(!imageFile.canRead())
             return;
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        if (null == bufferedImage) 
            return;
        
        int originalWidth = bufferedImage.getWidth();
        int originalHeight = bufferedImage.getHeight();
        double scale = (double)originalWidth / (double)newWidth;    // 缩放的比例
        
        int newHeight =  (int)(originalHeight / scale);

        zoomImageUtils(imageFile, newPath, bufferedImage, newWidth, newHeight);
    }

    private static void zoomImageUtils(File imageFile, String newPath, BufferedImage bufferedImage, int width, int height)
            throws IOException{
        // 处理 png 背景变黑的问题
    	String fileType = getFileType(imageFile);
    	if(StringUtils.isNotBlank(fileType) && (fileType.toLowerCase().endsWith("png") || fileType.toLowerCase().endsWith("gif"))){
            BufferedImage to= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
            Graphics2D g2d = to.createGraphics(); 
            to = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
            g2d.dispose(); 
            
            g2d = to.createGraphics(); 
            Image from = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING); 
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose(); 
            
            ImageIO.write(to, fileType, new File(newPath));
        }else{
            // 高质量压缩，其实对清晰度而言没有太多的帮助
//            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            tag.getGraphics().drawImage(bufferedImage, 0, 0, width, height, null);
//
//            FileOutputStream out = new FileOutputStream(newPath);    // 将图片写入 newPath
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
//            jep.setQuality(1f, true);    //压缩质量, 1 是最高值
//            encoder.encode(tag, jep);
//            out.close();
            
            BufferedImage newImage = new BufferedImage(width, height, bufferedImage.getType());
            Graphics g = newImage.getGraphics();
            g.drawImage(bufferedImage, 0, 0, width, height, null);
            g.dispose();
            ImageIO.write(newImage, fileType, new File(newPath));
        }
    }
   
    /**
     * 等比例改变图片尺寸
     * @param nw 新图片的宽度
     * @param oldImage 原图片
     * @throws IOException
     */
    public static void constrainProportios(int nw, String oldImage) throws IOException {
        AffineTransform transform = new AffineTransform();
        BufferedImage bis = ImageIO.read(new File(oldImage));
        int w = bis.getWidth();
        int h = bis.getHeight();
        int nh = (nw * h) / w;
        double sx = (double) nw / w;
        double sy = (double) nh / h;
        transform.setToScale(sx, sy);
        AffineTransformOp ato = new AffineTransformOp(transform, null);
        BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
        ato.filter(bis, bid);
        
        String newPath = StringUtils.substringBeforeLast(oldImage,".")+"_3."+StringUtils.substringAfterLast(oldImage,".");
        ImageIO.write(bid, "jpeg", new File(newPath));
//        ImageIO.write(bid, "jpeg", response.getOutputStream());
    }
   
    /**
     * 按尺寸缩放图片
     * 
     * @param imageFile
     * @param newPath
     * @param times
     * @throws IOException
     */
    public static void zoomImage(File imageFile, String newPath, int width, int height) throws IOException {
        if (imageFile != null && !imageFile.canRead())
            return;
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        if (null == bufferedImage) 
            return;

        zoomImageUtils(imageFile, newPath, bufferedImage, width, height);
    }

    /**
	 * 十六进制字符串
	 * @author:tjhua
	 * @date:2015-9-22 上午11:33:42
	 * <p>description:</p>
	 * @param src
	 * @return
	 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 获取文件类型
	 * @author:tjhua
	 * @date:2016-6-27
	 * <p>description:</p>
	 * @param file
	 * @return
	 */
	private static String getFileType(File file) {
		String fileType = "";
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] b = new byte[4];
			fis.read(b, 0, b.length);
			String type = bytesToHexString(b).toUpperCase();
			if(type.contains("89504E")){
				fileType = "png";
			} else if(type.contains("474946")){
				fileType = "gif";
			} else if(type.contains("FFD8FF")){
				fileType = "jpg";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileType;
	}

	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String string) {

		StringBuffer unicode = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {

			// 取出每一个字符
			char c = string.charAt(i);

			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}

		return unicode.toString();
	}

	/**
	 * unicode 转字符串
	 */
	public static String unicode2String(String unicode) {

		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split("\\\\u");

		for (int i = 1; i < hex.length; i++) {

			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);

			// 追加成string
			string.append((char) data);
		}

		return string.toString();
	}

	/**
	 * 计算两个经纬度的距离,单位:m
	 * @author tanjianhua
	 * @date 2016-11-10 下午2:42:42
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
	 * 获取位置
	 * @author tanjianhua
	 * @date 2016-11-10 下午3:17:32
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
	 * 获取16位的UUID
	 * @author liukh
	 * @date 2017-2-7 下午2:40:01
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
	/**
	 * 去除"-"的UUID
	 * @author liukh
	 * @date 2017-2-7 下午2:40:33
	 * @return
	 */
	public static String getUUIDRepalceStr(){
		String uUIDStr = getUUID();
		return uUIDStr.replaceAll("-","");
	}
	/**
	 * 某个时间上加月份或者天数
	 * @author liukh
	 * @date 2017-1-12 上午9:45:32
	 * @param startDate
	 * @param dayOrMonth
	 * @param amount
	 * @return
	 * @throws ParseException
	 */
	public static String getEndDate(String startDate,Integer dayOrMonth,Integer amount) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(startDate));
		//月
		if(dayOrMonth == 0){
			cal.add(Calendar.MONTH, amount);
		}else if(dayOrMonth == 1){
			cal.add(Calendar.DAY_OF_YEAR, amount);
		}
		return sdf.format(cal.getTime());
	}
}
