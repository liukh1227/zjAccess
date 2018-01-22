package com.zj.common.utils;

import java.math.BigDecimal;

import com.zj.base.common.Constant;


/**
 * @author liukh
 * @date 2016-10-29 下午5:12:53
 */
public class NumberUtils {
	

	/**
	 * 根据工期类型，返回相应的报价
	 * 
	 * @author liukh
	 * @date 2016-10-29 下午5:11:13
	 * @param beginDate
	 * @param endDate
	 * @param modelQuoteStr
	 * @return
	 */
	public static BigDecimal getMonthOrDayModelQuote(Integer billingType, String modelQuoteStr) {
		BigDecimal modelQuote = BigDecimal.ZERO;
		
		if (modelQuoteStr.indexOf("|") > -1
				&& modelQuoteStr.split("\\|") != null
				&& modelQuoteStr.split("\\|").length > 0) {
			String[] modelQutoArray = modelQuoteStr.split("\\|");
			if(billingType == Constant.BILLINGTYPE_MONTH){
				return new BigDecimal(modelQutoArray[0]);
			}else if(billingType == Constant.BILLINGTYPE_DAY){
				return new BigDecimal(modelQutoArray[1]);
			}
		}

		return modelQuote;
	}

	/**
	 * 计算总价 单价*时间*数量
	 * 
	 * @author liukh
	 * @date 2016-10-29 下午4:43:26
	 * @param beginDate
	 * @param endDate
	 * @param modelQuoteStr
	 * @param amount
	 * @return
	 */
	public static BigDecimal getModelQuotetotalPrice(Integer billingType,String leaseTerm ,String modelQuoteStr, int amount) {
		BigDecimal modelQuoteTotal = BigDecimal.ZERO;
		BigDecimal modelQuote = getMonthOrDayModelQuote(billingType,
				modelQuoteStr);
		if (modelQuote.compareTo(BigDecimal.ZERO) > 0) {
			return getDeviceTotalPrice(leaseTerm,modelQuote,amount);
		}

		return modelQuoteTotal;
	}
	/**
	 * 算总价 单价*时间*数量
	 * @author liukh
	 * @date 2017-1-11 下午4:22:00
	 * @param leaseTerm
	 * @param unitPrice
	 * @param amount
	 * @return
	 */
	public static BigDecimal getDeviceTotalPrice(String leaseTerm ,BigDecimal unitPrice, int amount) {
	
			return unitPrice.multiply(new BigDecimal(leaseTerm)).multiply(new BigDecimal(amount));
		
	}
	
	/**
	 * 判断可支配金额是否大于需要锁定的金额
	 * 
	 * @author liukh
	 * @date 2016-10-29 下午7:18:58
	 * @param disposableAmount
	 * @param needLocaledAmount
	 * @return
	 */
	public static Boolean checkCompanyMoneyIsEnough(
			BigDecimal disposableAmount, BigDecimal needLocaledAmount) {
		return disposableAmount.compareTo(needLocaledAmount) >= 0;
	}
	
	
	
	public static BigDecimal calculateTotalPrice(BigDecimal timeAmount,BigDecimal unitPrice,int amount,BigDecimal otherPrice){
        BigDecimal totalPrice = BigDecimal.ZERO;
        if(timeAmount!= null && unitPrice != null && otherPrice != null){
        	totalPrice = unitPrice;
        	totalPrice = totalPrice.multiply(timeAmount).multiply(new BigDecimal(amount));
        	totalPrice =totalPrice.add(otherPrice);
        }
		return totalPrice;
		
	}
	
	public static BigDecimal calculateTotalPriceUseDate(String leaseTerm ,BigDecimal unitPrice,int amount,BigDecimal otherPrice){
        BigDecimal totalPrice = BigDecimal.ZERO;
        
        if(leaseTerm != null &&unitPrice!= null  && otherPrice!= null){
        	return calculateTotalPrice(new BigDecimal(leaseTerm),unitPrice,amount,otherPrice);
        }
		
		return totalPrice;
		
	}
	
	public static Boolean checkCalculateTotalPriceEqualsTotalPrice(String leaseTerm,BigDecimal unitPrice,int amount,BigDecimal otherPrice, BigDecimal totalPrice){
		if(totalPrice != null){
			BigDecimal  calculateTotalPrice = calculateTotalPriceUseDate(leaseTerm,unitPrice, amount,otherPrice);
			return calculateTotalPrice.compareTo(totalPrice) == 0;
		}
		return false;
		
	}

	/**
	 * 如果总天数大约30，则总金额除以总天数乘以3天作为扣除（即扣除一个月的费用），否则扣除全部
	 * @author liukh
	 * @date 2016-10-31 下午2:16:53
	 * @param dayAmount
	 * @param totalParce
	 * @return
	 */	
	public static BigDecimal getLockedPrice(Integer billingType,String leaseTerm, BigDecimal totalParce) {
		BigDecimal lockedAmount = BigDecimal.ZERO;
		if(billingType == Constant.BILLINGTYPE_MONTH){
			lockedAmount = totalParce.divide(new BigDecimal(leaseTerm), 2,BigDecimal.ROUND_HALF_UP);
		}else if(billingType == Constant.BILLINGTYPE_DAY){
			if(new BigDecimal(leaseTerm).compareTo(new BigDecimal(Constant.MONTH_DAYS))>0){
				lockedAmount = totalParce.divide(new BigDecimal(leaseTerm), 2,BigDecimal.ROUND_HALF_UP);
				lockedAmount = lockedAmount.multiply(new BigDecimal(Constant.MONTH_DAYS));
			}else{
				lockedAmount = totalParce;
			}			

		}
		return lockedAmount;

		
	}
	




}
