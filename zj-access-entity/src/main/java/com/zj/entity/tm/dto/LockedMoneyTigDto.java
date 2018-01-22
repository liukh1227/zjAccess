package com.zj.entity.tm.dto;
import java.math.BigDecimal;

/**
 * @author liukh
 * @date 2016-10-27 下午6:12:52
 */
public class LockedMoneyTigDto{

    private String tip ;
    private BigDecimal amount ;
    
	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}


	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
    
}
